package com.example.activitydemo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.*;
import com.example.activitydemo.mapper.*;
import com.example.activitydemo.service.IActivityUserTaskRecordService;
import com.example.activitydemo.validate.ActivityUserTaskRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordUpdateValidate;
import com.example.activitydemo.vo.ActivityUserPriceVo;
import com.example.activitydemo.vo.ActivityUserTaskRecordListedVo;
import com.example.activitydemo.vo.SignMsgVo;
import com.example.activitydemo.vo.TaskDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 用户任务记录实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityUserTaskRecordServiceImpl extends ServiceImpl<ActivityUserTaskRecordMapper, ActivityUserTaskRecord> implements IActivityUserTaskRecordService {
    public static SimpleDateFormat format1 = new SimpleDateFormat(
            "yyyyMMdd HH:mm:ss");
    @Resource
    private ActivityUserTaskRecordMapper activityUserTaskRecordMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivityTaskItemMapper activityTaskItemMapper;
    @Resource
    private ActivityAwardMapper activityAwardMapper;
    @Resource
    private ActivityAwardRuleMapper activityAwardRuleMapper;
    @Resource
    private ActivityUserAwardRecordMapper activityUserAwardRecordMapper;
    @Resource
    private ActivityUserAwardMapper activityUserAwardMapper;
    @Resource
    private ActivityUserJoinMapper activityUserJoinMapper;
    @Resource
    private ActivityTaskRelationMapper activityTaskRelationMapper;


    @Override
    public SignMsgVo getSignMsg(Long activityId, long userId) {
        SignMsgVo signMsgVo = new SignMsgVo();
        getUserActivityMsg(signMsgVo, userId, activityId);
        //查询用户是否已完成
        ActivityUserJoin byUserIdAndActivityId = activityUserJoinMapper.getByUserIdAndActivityId(activityId, userId);
        if (byUserIdAndActivityId.getStatus() == 1) {
            //活动已完成，填充中奖信息
            List<Long> allAwardByUserId = activityUserAwardRecordMapper.getAllAwardByUserId(userId, activityId);
            List<ActivityUserPriceVo> priceList = new ArrayList<>();
            for (Long activityAwardId : allAwardByUserId) {
                ActivityUserPriceVo tmp = new ActivityUserPriceVo();
                ActivityAward activityAward = activityAwardMapper.selectById(activityAwardId);
                if (activityAward.getType().equals(1)) {
                    //勋章
                    signMsgVo.setRankNum(activityAward.getUseNum());
                }
                tmp.setPrizeIcon(activityAward.getIcon());
                tmp.setPrizeName(activityAward.getName());
                priceList.add(tmp);
            }
            //设置为已完成
            signMsgVo.setIfComplete(true);
            signMsgVo.setPriceList(priceList);
        } else {
            signMsgVo.setIfComplete(false);
        }
        return signMsgVo;
    }
    /**
     * 获取活动基本信息
     *
     * @param signMsgVo
     * @param userId
     * @param activityId
     */
    public void getUserActivityMsg(SignMsgVo signMsgVo, long userId, long activityId) {
        String key = RedisKey.ACTIVITY_USER_SIGN_NUM.getKey(userId + ":" + activityId);
        Integer continuousNums = redisTemplate.opsForValue().get(key);
        //查询活动信息
        Activity activity = activityMapper.selectById(activityId);
        Date startTime = activity.getStartTime();
        Date endTime = activity.getEndTime();
        List<ActivityUserTaskRecord> byActivityIdAndTimeAndUserId = activityUserTaskRecordMapper.getByActivityIdAndTimeAndUserId(startTime, endTime, activityId, userId);
        signMsgVo.setContinuousNums(continuousNums == null ? 0 : continuousNums);
        signMsgVo.setSignNums(byActivityIdAndTimeAndUserId == null ? 0 : byActivityIdAndTimeAndUserId.size());
        List<TaskDataVo> taskList = new ArrayList<>();
        if (byActivityIdAndTimeAndUserId != null && byActivityIdAndTimeAndUserId.size() > 0 && byActivityIdAndTimeAndUserId.get(0) != null) {
            for (ActivityUserTaskRecord activityUserTaskRecord : byActivityIdAndTimeAndUserId) {
                TaskDataVo tmp = new TaskDataVo();
                tmp.setTaskId(activityUserTaskRecord.getTaskItemId());
                tmp.setSignTime(activityUserTaskRecord.getCreateTime());
                tmp.setTaskStatus(0);
                ActivityTaskItem activityTaskItem = activityTaskItemMapper.selectById(activityUserTaskRecord.getTaskItemId());
                tmp.setTaskName(activityTaskItem.getName());
                tmp.setIcon(activityTaskItem.getIcon());
                tmp.setMoodStatus(activityUserTaskRecord.getMoodStatus());
                tmp.setResources(activityUserTaskRecord.getResources());
                tmp.setContent(activityUserTaskRecord.getContent());
                tmp.setAiComment(activityUserTaskRecord.getAiComment());
                taskList.add(tmp);
            }
        }
        signMsgVo.setPlanBanner(activity.getPlanBanner());
        signMsgVo.setTaskList(taskList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean signIn(Long activityId, Long taskId, Long userId, Integer moodStatus, String resource, String content, String aiComment, Integer isOpen) {
        Activity activity = activityMapper.selectById(activityId);
        //判断活动是否结束
        //判断活动是否结束
        if (System.currentTimeMillis() > activity.getEndTime().getTime()) {
            return false;
        }
        log.info("当前用户签到：用户id：{}，活动id：{}", userId, activityId);
        // 检查今天是否打卡
        ActivityUserTaskRecord lastActivityTask = activityUserTaskRecordMapper.getLatelyByActivityAndUserId(activityId, userId);
        Long taskItemId = lastActivityTask == null ? 0L : lastActivityTask.getTaskItemId();
        if (taskItemId.equals(taskId)) {
            return true;
        }
        //保存数据
        ActivityUserTaskRecord taskRecod = getTaskRecod(userId, activityId, taskId, moodStatus, resource, content, aiComment, isOpen);
        activityUserTaskRecordMapper.insert(taskRecod);
        String key = RedisKey.ACTIVITY_USER_SIGN_NUM.getKey(userId + ":" + activityId);
        redisTemplate.opsForValue().increment(key);
        Date now = new Date();
        long between = DateUtil.between(now, DateUtil.endOfDay(now).offset(DateField.DAY_OF_YEAR, 1), DateUnit.SECOND);
        redisTemplate.expire(key, between, TimeUnit.SECONDS);

        Date startTime = activity.getStartTime();
        Date endTime = activity.getEndTime();
        //用户已打卡天数
        Integer byActivityUserRecordNum = activityUserTaskRecordMapper.getByActivityUserRecordNum(startTime, endTime, activityId, userId);
        doAwarding(activity, byActivityUserRecordNum, userId);

        // 判断是否已全部完成活动，并且填充奖品信息
        String activityVal = activity.getVal();
        JSONObject activityJsonObject = JSON.parseObject(activityVal, JSONObject.class);
        Integer num = activityJsonObject.getInteger("num");
        if (Objects.equals(byActivityUserRecordNum, num)) {
            log.info("当前用户已完成任务，修改用户参与活动状态为已完成！");
            //更新用户活动状态
            ActivityUserJoin byUserIdAndActivityId = activityUserJoinMapper.getByUserIdAndActivityId(activityId, userId);
            byUserIdAndActivityId.setStatus(1);
            activityUserJoinMapper.updateById(byUserIdAndActivityId);
        }
        triggerSceneSceneActivityServiceImpl.business(userId, activityId);
        //删除缓存
        redisTemplate.delete("activity_adjust_task_" + userId + ":" + activityId);
        //判断是否是表情打卡，不需要发送动态
        if (activity.getTaskType() != null && activity.getTaskType() != 1) {
            PlazaAddDynamicVo plazaAddDynamicVo = new PlazaAddDynamicVo();
            plazaAddDynamicVo.setType(PlazaDynamicTypeEnum.ACTIVITY.getCode());
            plazaAddDynamicVo.setIsOpen(isOpen);
            plazaAddDynamicVo.setTargetId(taskRecod.getId());
            plazaService.addDynamic(plazaAddDynamicVo);
        }
        return true;
    }

    /**
     * 颁发奖品
     *
     * @param byActivityUserRecordNum
     * @param userId
     */
    //表示创建⼀个新的事务，如果当前存在事务，则把当前事务挂起。
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void doAwarding(Activity activity, Integer byActivityUserRecordNum, Long userId) {
        Long activityId = activity.getId();
        //用户获奖,插入奖品列表
        List<ActivityAwardRule> awardByActivityId = activityAwardRuleMapper.getAwardByActivityId(activityId);
        for (ActivityAwardRule activityAwardRule : awardByActivityId) {
            Long activityAwardId = activityAwardRule.getActivityAwardId();
            ActivityAward activityAward = activityAwardMapper.selectById(activityAwardId);
            //更新奖品表  奖品使用数+1
            JSONObject jsonObject = JSON.parseObject(activityAwardRule.getConfigVal(), JSONObject.class);
            //获取该奖品所需打卡天数
            Integer days = jsonObject.getInteger("days");
            //打卡没有达标或者奖品已被禁用或者该规则已被禁用
            if (!Objects.equals(byActivityUserRecordNum, days) || activityAwardRule.getStatus() == 0 || activityAward.getStatus() == 0) {
                log.info("当前奖品没有启用，奖品id：{}", activityAwardId);
                continue;
            }
            // 获取奖品锁
            String cacheKey = RedisKey.ACTIVITY_AWARD_LOCK.getKey(activityAwardId);
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(cacheKey, 1, 10, TimeUnit.SECONDS);
            Boolean isSend = (activityAward.getLimitType() != null && activityAward.getLimitType() == 0) ||
                    (activityAward.getLimitType() != null && activityAward.getLimitType() == 1 && activityAward.getUseNum() + 1 <= activityAward.getLimitNum());
            //发放奖品或勋章
            if (aBoolean != null && aBoolean && isSend) {
                log.info("当前用户已中奖，插入用户奖品信息：奖品id：{}", activityAwardId);
                Integer awardUsedNum = activityAward.getUseNum() + 1;
                activityAward.setUseNum(awardUsedNum);
                activityAwardMapper.updateById(activityAward);
                //插入获奖记录表
                ActivityUserAwardRecord awardRecord = getAwardRecord(activityAward, userId, activityAwardId, activityId, awardUsedNum);
                activityUserAwardRecordMapper.insert(awardRecord);

                //插入缓存
                iActivityService.setRedisCacheUserMedal(awardRecord);
                //更新用户奖品表（获得奖品数量累加）
                ActivityUserAward byUserId = activityUserAwardMapper.getByUserId(userId, activityAwardId);
                if (byUserId == null) {
                    activityUserAwardMapper.insert(getAward(userId, activityAward));
                } else {
                    byUserId.setNum(byUserId.getNum() + 1);
                    activityUserAwardMapper.updateById(byUserId);
                }
                //判断是否是勋章
                if (activityAward.getType().equals(1)) {
                    Integer numAwardIdByUserId = activityUserAwardRecordMapper.getNumAwardIdByUserId(1, userId);
                    iUserLevelService.setMedalNum(userId, numAwardIdByUserId);
                }
            }
            if (aBoolean != null && aBoolean) {
                try {
                    //判断奖品是否充足
                    //发放会员
                    if (isSend && activityAward.getEquityName().equals("VIP会员")) {
                        log.info("当前用户中奖VIP会员权益，赠送会员,用户id：{}", userId);
                        iVipPackageService.sendVip(userId, Long.parseLong(activityAward.getAward()), PayWayEnum.GIVE_AWAY_BY_ACTIVITY);
                    }
                } catch (Exception e) {
                    log.info("更新奖品失败：用户id：{}，活动id：{}", userId, activityId, e);
                } finally {
                    redisTemplate.delete(cacheKey);
                }
            }
        }
    }


    /**
     * 获取任务记录
     *
     * @param userId
     * @param activityId
     * @param taskId
     * @param moodStatus
     * @return
     */
    public ActivityUserTaskRecord getTaskRecod(Long userId, Long activityId, Long taskId, Integer moodStatus, String resource, String content, String aiComment, Integer isOpen) {
        ActivityUserTaskRecord activityUserTaskRecord = new ActivityUserTaskRecord();
        activityUserTaskRecord.setId(defaultIdentifierGenerator.nextId(activityUserTaskRecord));
        activityUserTaskRecord.setUserId(userId);
        activityUserTaskRecord.setActivityId(activityId);
        activityUserTaskRecord.setTaskItemId(taskId);
        activityUserTaskRecord.setMoodStatus(moodStatus);
        activityUserTaskRecord.setCreateUserId(userId);
        activityUserTaskRecord.setUpdateUserId(userId);
        activityUserTaskRecord.setCreateTime(new Date());
        activityUserTaskRecord.setUpdateTime(new Date());
        activityUserTaskRecord.setIsDelete(0);
        activityUserTaskRecord.setResources(resource);
        activityUserTaskRecord.setContent(content);
        activityUserTaskRecord.setIsOpen(isOpen);
        //TODO 调用AI专家分析
        activityUserTaskRecord.setAiComment(aiComment);
        return activityUserTaskRecord;
    }

    /**
     * 用户任务记录列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserTaskRecordListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityUserTaskRecordListedVo> list(PageValidate pageValidate, ActivityUserTaskRecordSearchValidate searchValidate) {
        Page<ActivityUserTaskRecord> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserTaskRecordListedVo> list = activityUserTaskRecordMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 用户任务记录详情
     *
     * @param id 主键参数
     * @return ActivityUserTaskRecord
     * @author fei
     */
    @Override
    public ActivityUserTaskRecordListedVo detail(Long id) {
        return activityUserTaskRecordMapper.getDetailById(id);
    }

    /**
     * 用户任务记录新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityUserTaskRecordCreateValidate createValidate) {
        ActivityUserTaskRecord model = new ActivityUserTaskRecord();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityUserTaskRecordMapper.insert(model);
    }

    /**
     * 用户任务记录编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityUserTaskRecordUpdateValidate updateValidate) {
        ActivityUserTaskRecord model = activityUserTaskRecordMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityUserTaskRecordMapper.updateById(model);
    }

    /**
     * 用户任务记录删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityUserTaskRecordMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

