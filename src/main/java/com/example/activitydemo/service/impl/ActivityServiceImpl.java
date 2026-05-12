package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.*;
import com.example.activitydemo.mapper.ActivityMapper;
import com.example.activitydemo.mapper.ActivityTaskItemMapper;
import com.example.activitydemo.mapper.ActivityTaskRelationMapper;
import com.example.activitydemo.mapper.ActivityUserJoinMapper;
import com.example.activitydemo.service.IActivityService;
import com.example.activitydemo.validate.ActivityCreateValidate;
import com.example.activitydemo.validate.ActivitySearchValidate;
import com.example.activitydemo.validate.ActivityUpdateValidate;
import com.example.activitydemo.vo.ActivityDetailVo;
import com.example.activitydemo.vo.ActivityListedVo;
import com.example.activitydemo.vo.ActivityTaskItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivityTaskRelationMapper activityTaskRelationMapper;
    @Resource
    private DefaultIdentifierGenerator defaultIdentifierGenerator;
    @Resource
    private ActivityTaskItemMapper activityTaskItemMapper;
    @Resource
    private ActivityUserJoinMapper activityUserJoinMapper;


    @Override
    public List<ActivityTaskItem> getTaskList(Long activityId, Long userId) {

        List<ActivityTaskItem> resultList = new ArrayList<>();
        //查询活动任务列表
        List<ActivityTaskRelation> byActivityId = activityTaskRelationMapper.getByActivityId(activityId);
        for (ActivityTaskRelation activityTaskRelation : byActivityId) {
            ActivityUserTaskRecord taskRecord = activityUserTaskRecordMapper.getTaskRecord(activityTaskRelation.getActivityId(), userId, activityTaskRelation.getTaskItemId());
            if (taskRecord == null) {
                ActivityTaskItem activityTaskItem = activityTaskItemMapper.selectById(activityTaskRelation.getTaskItemId());
                resultList.add(activityTaskItem);
            }
        }
        return resultList;
    }
    @Override
    public ActivityDetailVo getActivityDetail(Long id, Long userId) {
        ActivityDetailVo activityDetailVo = new ActivityDetailVo();
        Activity activity = activityMapper.selectById(id);
        BeanUtils.copyProperties(activity, activityDetailVo);
        ActivityUserJoin byUserIdAndActivityId = activityUserJoinMapper.getByUserIdAndActivityId(id, userId);
        activityDetailVo.setIfSign(byUserIdAndActivityId != null);
        //判断是否实物商品
        activityDetailVo.setIsMaterial(activityAwardRuleMapper.getIsMaterial(activity.getId()) > 0 ? 1 : 0);
        return activityDetailVo;
    }

    /**
     * 活动列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityListedVo> list(PageValidate pageValidate, ActivitySearchValidate searchValidate) {
        Page<Activity> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        if (searchValidate != null) {
            searchValidate.setNowDate(new Date());
        }
        IPage<ActivityListedVo> list = activityMapper.list(page, searchValidate);
        List<ActivityListedVo> records = list.getRecords();
        Date nowDate = new Date();
        for (ActivityListedVo record : records) {
            if (nowDate.getTime() - record.getEndTime().getTime() > 0) {
                //已结束
                record.setStatus(2);
            } else if (nowDate.getTime() - record.getEndTime().getTime() <= 0 && nowDate.getTime() - record.getStartTime().getTime() > 0) {
                //进行中
                record.setStatus(1);
            } else {
                record.setStatus(0);
            }
        }
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), records);
    }

    /**
     * 活动详情
     *
     * @param id 主键参数
     * @return Activity
     * @author fei
     */
    @Override
    public ActivityDetailVo detail(Long id) {
        Activity model = activityMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityDetailVo vo = new ActivityDetailVo();
        BeanUtils.copyProperties(model, vo);
        List<ActivityTaskItem> taskList = new ArrayList<>();
        List<Long> itemTaskList = activityTaskRelationMapper.getItemTaskList(id);
        if (itemTaskList != null && itemTaskList.size() > 0) {
            for (Long aLong : itemTaskList) {
                ActivityTaskItem activityTaskItem = activityTaskItemMapper.selectById(aLong);
                if (activityTaskItem != null) {
                    taskList.add(activityTaskItem);
                }
            }
        }
        vo.setTaskList(taskList);
        return vo;
    }

    /**
     * 活动新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(ActivityCreateValidate createValidate, Long userId) {
        Activity model = new Activity();
        BeanUtils.copyProperties(createValidate, model);
        Long activityId = defaultIdentifierGenerator.nextId(model);
        model.setId(activityId);
        model.setStartTime(createValidate.getStartTime());
        model.setEndTime(createValidate.getEndTime());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setColorMsg(createValidate.getColorMsg());
        model.setIsHot(createValidate.getIsHot());
        model.setTaskType(createValidate.getTaskType());
        model.setCreateType(1);
        List<ActivityTaskItemVo> taskList = createValidate.getTaskList();
        for (ActivityTaskItemVo aLong : taskList) {
            ActivityTaskRelation activityTaskRelation = new ActivityTaskRelation();
            activityTaskRelation.setId(defaultIdentifierGenerator.nextId(activityTaskRelation));
            activityTaskRelation.setActivityId(activityId);
            activityTaskRelation.setTaskItemId(aLong.getTaskId());
            activityTaskRelation.setSorts(aLong.getSorts());
            activityTaskRelation.setCreateUserId(userId);
            activityTaskRelation.setUpdateUserId(userId);
            activityTaskRelation.setCreateTime(new Date());
            activityTaskRelation.setUpdateTime(new Date());
            activityTaskRelation.setIsDelete(0);
            activityTaskRelationMapper.insert(activityTaskRelation);
        }
        activityMapper.insert(model);
    }

    /**
     * 活动编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(ActivityUpdateValidate updateValidate, Long userId) {
        if (activityUserJoinMapper.getByActivityId(updateValidate.getId()) != null) {
            return false;
        }
        Activity model = activityMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        model.setIsHot(updateValidate.getIsHot());
        model.setTaskType(updateValidate.getTaskType());
        activityMapper.updateById(model);
        List<ActivityTaskItemVo> taskList = updateValidate.getTaskList();
        if (taskList != null && taskList.size() > 0) {
            //删除任务
            activityTaskRelationMapper.deleteTask(updateValidate.getId());
            for (ActivityTaskItemVo aLong : taskList) {
                ActivityTaskRelation activityTaskRelation = new ActivityTaskRelation();
                activityTaskRelation.setId(defaultIdentifierGenerator.nextId(activityTaskRelation));
                activityTaskRelation.setActivityId(updateValidate.getId());
                activityTaskRelation.setTaskItemId(aLong.getTaskId());
                activityTaskRelation.setSorts(aLong.getSorts());
                activityTaskRelation.setCreateUserId(userId);
                activityTaskRelation.setUpdateUserId(userId);
                activityTaskRelation.setCreateTime(new Date());
                activityTaskRelation.setUpdateTime(new Date());
                activityTaskRelation.setIsDelete(0);
                activityTaskRelationMapper.insert(activityTaskRelation);
            }
        }
        return true;
    }

    /**
     * 活动删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public Boolean del(Long id) {
        if (activityUserJoinMapper.getByActivityId(id) != null) {
            return false;
        }
        int i = activityMapper.deleteById(id);
        //删除任务
        activityTaskRelationMapper.deleteTask(id);

        Assert.isTrue(i > 0, "数据不存在!");
        return true;
    }


}

