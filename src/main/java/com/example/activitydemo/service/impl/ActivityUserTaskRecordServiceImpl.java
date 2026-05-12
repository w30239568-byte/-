package com.example.activitydemo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.Activity;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.domain.ActivityAwardRule;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.domain.ActivityUserAward;
import com.example.activitydemo.domain.ActivityUserAwardRecord;
import com.example.activitydemo.domain.ActivityUserJoin;
import com.example.activitydemo.domain.ActivityUserTaskRecord;
import com.example.activitydemo.mapper.ActivityAwardMapper;
import com.example.activitydemo.mapper.ActivityAwardRuleMapper;
import com.example.activitydemo.mapper.ActivityMapper;
import com.example.activitydemo.mapper.ActivityTaskItemMapper;
import com.example.activitydemo.mapper.ActivityTaskRelationMapper;
import com.example.activitydemo.mapper.ActivityUserAwardMapper;
import com.example.activitydemo.mapper.ActivityUserAwardRecordMapper;
import com.example.activitydemo.mapper.ActivityUserJoinMapper;
import com.example.activitydemo.mapper.ActivityUserTaskRecordMapper;
import com.example.activitydemo.service.IActivityUserTaskRecordService;
import com.example.activitydemo.validate.ActivityUserTaskRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordUpdateValidate;
import com.example.activitydemo.vo.ActivityUserPriceVo;
import com.example.activitydemo.vo.ActivityUserTaskRecordListedVo;
import com.example.activitydemo.vo.SignMsgVo;
import com.example.activitydemo.vo.TaskDataVo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ActivityUserTaskRecordServiceImpl extends ServiceImpl<ActivityUserTaskRecordMapper, ActivityUserTaskRecord> implements IActivityUserTaskRecordService {

    private static final String USER_SIGN_KEY = "activity:user:sign:";
    private static final String AWARD_STOCK_KEY = "activity:award:stock:";
    private static final String SIGN_IDEMPOTENT_KEY = "idempotent:signin:";
    private static final long SIGN_IDEMPOTENT_EXPIRE_SECONDS = 120L;

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
    @Resource
    private DefaultIdentifierGenerator defaultIdentifierGenerator;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final DefaultRedisScript<Long> awardStockScript;

    public ActivityUserTaskRecordServiceImpl() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/award_stock.lua"));
        script.setResultType(Long.class);
        this.awardStockScript = script;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SignMsgVo getSignMsg(Long activityId, long userId) {
        SignMsgVo signMsgVo = new SignMsgVo();
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) {
            return signMsgVo;
        }
        signMsgVo.setActivityId(activityId);
        signMsgVo.setActivityName(activity.getName());
        signMsgVo.setCoverImg(activity.getCoverImg());
        signMsgVo.setStartTime(activity.getStartTime());
        signMsgVo.setEndTime(activity.getEndTime());
        signMsgVo.setCreateType(activity.getCreateType());
        signMsgVo.setPlanBanner(activity.getBannerImg());
        signMsgVo.setTotalNum(activityTaskRelationMapper.countNumByActivityId(activityId));
        signMsgVo.setStatus(new Date().after(activity.getEndTime()) ? 1 : 2);

        List<ActivityUserTaskRecord> recordList = activityUserTaskRecordMapper.getByActivityIdAndTimeAndUserId(
                activity.getStartTime(), activity.getEndTime(), activityId, userId);
        signMsgVo.setSignNums(recordList.size());
        signMsgVo.setTaskList(buildTaskDataList(recordList));

        String signKey = USER_SIGN_KEY + userId + ":" + activityId;
        String continuous = stringRedisTemplate.opsForValue().get(signKey);
        signMsgVo.setContinuousNums(continuous == null ? 0 : Integer.parseInt(continuous));

        ActivityUserJoin join = activityUserJoinMapper.getByUserIdAndActivityId(activityId, userId);
        boolean complete = join != null && Integer.valueOf(1).equals(join.getStatus());
        signMsgVo.setIfComplete(complete);
        if (complete) {
            List<Long> awardIds = activityUserAwardRecordMapper.getAllAwardByUserId(userId, activityId);
            List<ActivityUserPriceVo> prices = new ArrayList<>();
            for (Long awardId : awardIds) {
                ActivityAward award = activityAwardMapper.selectById(awardId);
                if (award == null) {
                    continue;
                }
                ActivityUserPriceVo vo = new ActivityUserPriceVo();
                vo.setPrizeName(award.getName());
                vo.setPrizeIcon(award.getIcon());
                prices.add(vo);
            }
            signMsgVo.setPriceList(prices);
        }
        return signMsgVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean signIn(Long activityId, Long taskId, Long userId, Integer moodStatus, String resource, String content, String aiComment, Integer isOpen, String requestKey) {
        if (activityId == null || taskId == null || userId == null) {
            return false;
        }
        if (!StringUtils.hasText(requestKey)) {
            throw new IllegalArgumentException("请求唯一键不能为空");
        }
        String idempotentKey = buildSignIdempotentKey(userId, activityId, requestKey);
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(idempotentKey, "1");
        if (Boolean.FALSE.equals(lock)) {
            throw new IllegalArgumentException("重复提交");
        }
        if (Boolean.TRUE.equals(lock)) {
            stringRedisTemplate.expire(idempotentKey, SIGN_IDEMPOTENT_EXPIRE_SECONDS, TimeUnit.SECONDS);
        }

        Activity activity = activityMapper.selectById(activityId);
        if (activity == null || activity.getEndTime() == null || System.currentTimeMillis() > activity.getEndTime().getTime()) {
            return false;
        }

        ActivityUserJoin join = activityUserJoinMapper.getByUserIdAndActivityId(activityId, userId);
        if (join == null) {
            return false;
        }

        ActivityUserTaskRecord latest = activityUserTaskRecordMapper.getLatelyByActivityAndUserId(activityId, userId);
        if (latest != null && latest.getTaskItemId() != null && latest.getTaskItemId().equals(taskId)
                && isSameDay(latest.getCreateTime(), new Date())) {
            return true;
        }

        ActivityUserTaskRecord taskRecord = buildTaskRecord(userId, activityId, taskId, moodStatus, resource, content, aiComment, isOpen, requestKey);
        try {
            activityUserTaskRecordMapper.insert(taskRecord);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("重复提交");
        }
        refreshContinuousDays(userId, activityId, latest);

        Integer signedCount = activityUserTaskRecordMapper.getByActivityUserRecordNum(activity.getStartTime(), activity.getEndTime(), activityId, userId);
        doAwarding(activity, signedCount, userId);

        Integer requiredNum = parseRequiredSignNum(activity.getVal());
        if (requiredNum != null && signedCount >= requiredNum) {
            join.setStatus(1);
            join.setUpdateUserId(userId);
            join.setUpdateTime(new Date());
            activityUserJoinMapper.updateById(join);
        }
        stringRedisTemplate.delete("activity_adjust_task_" + userId + ":" + activityId);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void doAwarding(Activity activity, Integer signedCount, Long userId) {
        List<ActivityAwardRule> rules = activityAwardRuleMapper.getAwardByActivityId(activity.getId());
        for (ActivityAwardRule rule : rules) {
            if (!Integer.valueOf(1).equals(rule.getStatus())) {
                continue;
            }
            Integer days = parseDays(rule.getConfigVal());
            if (days == null || !days.equals(signedCount)) {
                continue;
            }
            ActivityAward award = activityAwardMapper.selectById(rule.getActivityAwardId());
            if (award == null || !Integer.valueOf(1).equals(award.getStatus())) {
                continue;
            }
            if (activityUserAwardRecordMapper.countByUserAndAwardAndActivity(userId, award.getId(), activity.getId()) > 0) {
                continue;
            }

            if (award.getLimitType() != null && award.getLimitType() == 1) {
                grantLimitedAward(activity.getId(), userId, award);
            } else {
                grantUnlimitedAward(activity.getId(), userId, award);
            }
        }
    }

    private void grantLimitedAward(Long activityId, Long userId, ActivityAward award) {
        String key = AWARD_STOCK_KEY + award.getId();
        initAwardStockKey(key, award);

        Long luaResult = stringRedisTemplate.execute(awardStockScript, Collections.singletonList(key), "1");
        if (luaResult == null || luaResult < 0) {
            return;
        }

        int updated = activityAwardMapper.incrUseNumWithLimit(award.getId());
        if (updated <= 0) {
            stringRedisTemplate.opsForValue().increment(key, 1L);
            return;
        }

        try {
            writeAwardRecordAndUserAward(activityId, userId, award);
        } catch (Exception e) {
            stringRedisTemplate.opsForValue().increment(key, 1L);
            throw e;
        }
    }

    private void grantUnlimitedAward(Long activityId, Long userId, ActivityAward award) {
        activityAwardMapper.incrUseNum(award.getId());
        writeAwardRecordAndUserAward(activityId, userId, award);
    }

    private void writeAwardRecordAndUserAward(Long activityId, Long userId, ActivityAward award) {
        Date now = new Date();
        ActivityUserAwardRecord record = new ActivityUserAwardRecord();
        record.setId(defaultIdentifierGenerator.nextId(record));
        record.setUserId(userId);
        record.setAwardId(award.getId());
        record.setActivityId(activityId);
        record.setType(award.getType());
        record.setAward(award.getAward());
        record.setCreateUserId(userId);
        record.setUpdateUserId(userId);
        record.setCreateTime(now);
        record.setUpdateTime(now);
        record.setIsDelete(0);
        activityUserAwardRecordMapper.insert(record);

        ActivityUserAward userAward = activityUserAwardMapper.getByUserId(userId, award.getId());
        if (userAward == null) {
            ActivityUserAward add = new ActivityUserAward();
            add.setId(defaultIdentifierGenerator.nextId(add));
            add.setActivityAwardId(award.getId());
            add.setName(award.getName());
            add.setIcon(award.getIcon());
            add.setType(award.getType());
            add.setNum(1);
            add.setCreateUserId(userId);
            add.setUpdateUserId(userId);
            add.setCreateTime(now);
            add.setUpdateTime(now);
            add.setIsDelete(0);
            activityUserAwardMapper.insert(add);
        } else {
            userAward.setNum(userAward.getNum() == null ? 1 : userAward.getNum() + 1);
            userAward.setUpdateUserId(userId);
            userAward.setUpdateTime(now);
            activityUserAwardMapper.updateById(userAward);
        }
    }

    private List<TaskDataVo> buildTaskDataList(List<ActivityUserTaskRecord> recordList) {
        List<TaskDataVo> taskList = new ArrayList<>();
        for (ActivityUserTaskRecord record : recordList) {
            TaskDataVo vo = new TaskDataVo();
            vo.setTaskId(record.getTaskItemId());
            vo.setSignTime(record.getCreateTime());
            vo.setTaskStatus(0);
            ActivityTaskItem item = activityTaskItemMapper.selectById(record.getTaskItemId());
            if (item != null) {
                vo.setTaskName(item.getName());
                vo.setIcon(item.getIcon());
            }
            vo.setMoodStatus(record.getMoodStatus());
            vo.setResources(record.getResources());
            vo.setContent(record.getContent());
            vo.setAiComment(record.getAiComment());
            taskList.add(vo);
        }
        return taskList;
    }

    private ActivityUserTaskRecord buildTaskRecord(Long userId, Long activityId, Long taskId, Integer moodStatus,
                                                   String resource, String content, String aiComment, Integer isOpen, String requestKey) {
        ActivityUserTaskRecord record = new ActivityUserTaskRecord();
        record.setId(defaultIdentifierGenerator.nextId(record));
        record.setUserId(userId);
        record.setActivityId(activityId);
        record.setTaskItemId(taskId);
        record.setMoodStatus(moodStatus);
        record.setResources(resource);
        record.setContent(content);
        record.setAiComment(aiComment);
        record.setIsOpen(isOpen);
        record.setRequestKey(requestKey);
        record.setCreateUserId(userId);
        record.setUpdateUserId(userId);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setIsDelete(0);
        return record;
    }

    private String buildSignIdempotentKey(Long userId, Long activityId, String requestKey) {
        return SIGN_IDEMPOTENT_KEY + userId + ":" + activityId + ":" + requestKey;
    }

    private void refreshContinuousDays(Long userId, Long activityId, ActivityUserTaskRecord latestRecord) {
        String key = USER_SIGN_KEY + userId + ":" + activityId;
        int newValue = 1;
        if (latestRecord != null && latestRecord.getCreateTime() != null) {
            LocalDate today = LocalDate.now();
            LocalDate latestDate = latestRecord.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long days = ChronoUnit.DAYS.between(latestDate, today);
            if (days == 1) {
                String oldValue = stringRedisTemplate.opsForValue().get(key);
                int current = oldValue == null ? 0 : Integer.parseInt(oldValue);
                newValue = current + 1;
            }
        }
        stringRedisTemplate.opsForValue().set(key, String.valueOf(newValue), 30, TimeUnit.DAYS);
    }

    private boolean isSameDay(Date first, Date second) {
        LocalDate d1 = first.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = second.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return d1.equals(d2);
    }

    private Integer parseRequiredSignNum(String val) {
        try {
            JSONObject jsonObject = JSON.parseObject(val);
            return jsonObject == null ? null : jsonObject.getInteger("num");
        } catch (Exception ignored) {
            return null;
        }
    }

    private Integer parseDays(String configVal) {
        try {
            JSONObject jsonObject = JSON.parseObject(configVal);
            return jsonObject == null ? null : jsonObject.getInteger("days");
        } catch (Exception ignored) {
            return null;
        }
    }

    private void initAwardStockKey(String key, ActivityAward award) {
        Integer limitNum = award.getLimitNum() == null ? 0 : award.getLimitNum();
        Integer usedNum = award.getUseNum() == null ? 0 : award.getUseNum();
        int remaining = Math.max(limitNum - usedNum, 0);
        Boolean absent = stringRedisTemplate.opsForValue().setIfAbsent(key, String.valueOf(remaining));
        if (Boolean.TRUE.equals(absent)) {
            stringRedisTemplate.expire(key, 1, TimeUnit.DAYS);
        }
    }

    @Override
    public PageResult<ActivityUserTaskRecordListedVo> list(PageValidate pageValidate, ActivityUserTaskRecordSearchValidate searchValidate) {
        Page<ActivityUserTaskRecord> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserTaskRecordListedVo> list = activityUserTaskRecordMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    @Override
    public ActivityUserTaskRecordListedVo detail(Long id) {
        return activityUserTaskRecordMapper.getDetailById(id);
    }

    @Override
    public void add(ActivityUserTaskRecordCreateValidate createValidate) {
        ActivityUserTaskRecord model = new ActivityUserTaskRecord();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityUserTaskRecordMapper.insert(model);
    }

    @Override
    public void edit(ActivityUserTaskRecordUpdateValidate updateValidate) {
        ActivityUserTaskRecord model = activityUserTaskRecordMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityUserTaskRecordMapper.updateById(model);
    }

    @Override
    public void del(Long id) {
        int i = activityUserTaskRecordMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在");
    }
}
