package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.Activity;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.domain.ActivityTaskRelation;
import com.example.activitydemo.domain.ActivityUserJoin;
import com.example.activitydemo.domain.ActivityUserTaskRecord;
import com.example.activitydemo.mapper.ActivityAwardRuleMapper;
import com.example.activitydemo.mapper.ActivityMapper;
import com.example.activitydemo.mapper.ActivityTaskItemMapper;
import com.example.activitydemo.mapper.ActivityTaskRelationMapper;
import com.example.activitydemo.mapper.ActivityUserJoinMapper;
import com.example.activitydemo.mapper.ActivityUserTaskRecordMapper;
import com.example.activitydemo.service.IActivityService;
import com.example.activitydemo.validate.ActivityCreateValidate;
import com.example.activitydemo.validate.ActivitySearchValidate;
import com.example.activitydemo.validate.ActivityUpdateValidate;
import com.example.activitydemo.vo.ActivityDetailVo;
import com.example.activitydemo.vo.ActivityListedVo;
import com.example.activitydemo.vo.ActivityTaskItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Resource
    private ActivityUserTaskRecordMapper activityUserTaskRecordMapper;
    @Resource
    private ActivityAwardRuleMapper activityAwardRuleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ActivityTaskItem> getTaskList(Long activityId, Long userId) {
        List<ActivityTaskItem> resultList = new ArrayList<>();
        List<ActivityTaskRelation> relationList = activityTaskRelationMapper.getByActivityId(activityId);
        for (ActivityTaskRelation relation : relationList) {
            ActivityUserTaskRecord taskRecord = activityUserTaskRecordMapper.getTaskRecord(activityId, userId, relation.getTaskItemId());
            if (taskRecord == null) {
                ActivityTaskItem activityTaskItem = activityTaskItemMapper.selectById(relation.getTaskItemId());
                if (activityTaskItem != null) {
                    resultList.add(activityTaskItem);
                }
            }
        }
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityDetailVo getActivityDetail(Long id, Long userId) {
        ActivityDetailVo activityDetailVo = new ActivityDetailVo();
        Activity activity = activityMapper.selectById(id);
        if (activity == null) {
            return activityDetailVo;
        }
        BeanUtils.copyProperties(activity, activityDetailVo);
        ActivityUserJoin join = activityUserJoinMapper.getByUserIdAndActivityId(id, userId);
        activityDetailVo.setIfSign(join != null);
        Integer materialCount = activityAwardRuleMapper.getIsMaterial(activity.getId());
        activityDetailVo.setIsMaterial(materialCount != null && materialCount > 0 ? 1 : 0);
        return activityDetailVo;
    }

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
            if (nowDate.after(record.getEndTime())) {
                record.setStatus(2);
            } else if (!nowDate.before(record.getStartTime())) {
                record.setStatus(1);
            } else {
                record.setStatus(0);
            }
        }
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), records);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ActivityDetailVo detail(Long id) {
        Activity model = activityMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityDetailVo vo = new ActivityDetailVo();
        BeanUtils.copyProperties(model, vo);
        List<ActivityTaskItem> taskList = new ArrayList<>();
        List<Long> itemTaskList = activityTaskRelationMapper.getItemTaskList(id);
        if (itemTaskList != null) {
            for (Long taskItemId : itemTaskList) {
                ActivityTaskItem activityTaskItem = activityTaskItemMapper.selectById(taskItemId);
                if (activityTaskItem != null) {
                    taskList.add(activityTaskItem);
                }
            }
        }
        vo.setTaskList(taskList);
        return vo;
    }

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
        if (taskList != null) {
            for (ActivityTaskItemVo taskItemVo : taskList) {
                ActivityTaskRelation relation = new ActivityTaskRelation();
                relation.setId(defaultIdentifierGenerator.nextId(relation));
                relation.setActivityId(activityId);
                relation.setTaskItemId(taskItemVo.getTaskId());
                relation.setSorts(taskItemVo.getSorts());
                relation.setCreateUserId(userId);
                relation.setUpdateUserId(userId);
                relation.setCreateTime(new Date());
                relation.setUpdateTime(new Date());
                relation.setIsDelete(0);
                activityTaskRelationMapper.insert(relation);
            }
        }
        activityMapper.insert(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(ActivityUpdateValidate updateValidate, Long userId) {
        if (activityUserJoinMapper.getByActivityId(updateValidate.getId()) != null) {
            return false;
        }
        Activity model = activityMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        model.setIsHot(updateValidate.getIsHot());
        model.setTaskType(updateValidate.getTaskType());
        activityMapper.updateById(model);

        List<ActivityTaskItemVo> taskList = updateValidate.getTaskList();
        if (taskList != null && !taskList.isEmpty()) {
            activityTaskRelationMapper.deleteTask(updateValidate.getId());
            for (ActivityTaskItemVo taskItemVo : taskList) {
                ActivityTaskRelation relation = new ActivityTaskRelation();
                relation.setId(defaultIdentifierGenerator.nextId(relation));
                relation.setActivityId(updateValidate.getId());
                relation.setTaskItemId(taskItemVo.getTaskId());
                relation.setSorts(taskItemVo.getSorts());
                relation.setCreateUserId(userId);
                relation.setUpdateUserId(userId);
                relation.setCreateTime(new Date());
                relation.setUpdateTime(new Date());
                relation.setIsDelete(0);
                activityTaskRelationMapper.insert(relation);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean del(Long id) {
        if (activityUserJoinMapper.getByActivityId(id) != null) {
            return false;
        }
        int i = activityMapper.deleteById(id);
        activityTaskRelationMapper.deleteTask(id);
        Assert.isTrue(i > 0, "数据不存在");
        return true;
    }
}
