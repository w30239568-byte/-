package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.Activity;
import com.example.activitydemo.domain.ActivityUserJoin;
import com.example.activitydemo.mapper.ActivityMapper;
import com.example.activitydemo.mapper.ActivityUserJoinMapper;
import com.example.activitydemo.service.IActivityUserJoinService;
import com.example.activitydemo.validate.ActivityUserJoinCreateValidate;
import com.example.activitydemo.validate.ActivityUserJoinSearchValidate;
import com.example.activitydemo.validate.ActivityUserJoinUpdateValidate;
import com.example.activitydemo.vo.ActivityUserJoinListedVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ActivityUserJoinServiceImpl extends ServiceImpl<ActivityUserJoinMapper, ActivityUserJoin> implements IActivityUserJoinService {

    @Resource
    private ActivityUserJoinMapper activityUserJoinMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private DefaultIdentifierGenerator defaultIdentifierGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String activitySignUp(Long activityId, Long userId, Long targetId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null || activity.getIsDelete() != null && activity.getIsDelete() == 1) {
            return "活动不存在";
        }
        if (activityUserJoinMapper.getByUserIdAndActivityId(activityId, userId) != null) {
            return "当前活动已参与，请勿重复报名";
        }
        if (System.currentTimeMillis() > activity.getEndTime().getTime()) {
            return "活动已结束";
        }
        if (activity.getLimitType() != null && activity.getLimitType() == 1 && activity.getLimitNum() != null) {
            Integer joinNum = activity.getJoinNum() == null ? 0 : activity.getJoinNum();
            if (joinNum + 1 > activity.getLimitNum()) {
                return "参与人数达到上限";
            }
        }

        Date now = new Date();
        ActivityUserJoin activityUserJoin = new ActivityUserJoin();
        activityUserJoin.setId(defaultIdentifierGenerator.nextId(activityUserJoin));
        activityUserJoin.setActivityId(activityId);
        activityUserJoin.setUserId(userId);
        activityUserJoin.setStatus(0);
        activityUserJoin.setCreateUserId(userId);
        activityUserJoin.setUpdateUserId(userId);
        activityUserJoin.setCreateTime(now);
        activityUserJoin.setUpdateTime(now);
        activityUserJoin.setJoinTime(activity.getStartTime() != null && activity.getStartTime().after(now) ? activity.getStartTime() : now);
        activityUserJoin.setIsDelete(0);
        activityUserJoinMapper.insert(activityUserJoin);

        Integer joinNum = activity.getJoinNum() == null ? 0 : activity.getJoinNum();
        activity.setJoinNum(joinNum + 1);
        activityMapper.updateById(activity);
        return "success";
    }

    @Override
    public PageResult<ActivityUserJoinListedVo> list(PageValidate pageValidate, ActivityUserJoinSearchValidate searchValidate) {
        Page<ActivityUserJoin> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserJoinListedVo> list = activityUserJoinMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    @Override
    public ActivityUserJoinListedVo detail(Long id) {
        ActivityUserJoin model = activityUserJoinMapper.selectById(id);
        Assert.notNull(model, "数据不存在");
        ActivityUserJoinListedVo vo = new ActivityUserJoinListedVo();
        BeanUtils.copyProperties(model, vo);
        vo.setUserId(model.getUserId() == null ? null : String.valueOf(model.getUserId()));
        return vo;
    }

    @Override
    public Boolean add(ActivityUserJoinCreateValidate createValidate) {
        Activity activity = activityMapper.selectById(createValidate.getActivityId());
        if (activity == null) {
            return false;
        }
        if (activity.getLimitType() != null && activity.getLimitType() == 1 && activity.getLimitNum() != null) {
            Integer joinNum = activity.getJoinNum() == null ? 0 : activity.getJoinNum();
            if (joinNum + 1 > activity.getLimitNum()) {
                return false;
            }
        }
        ActivityUserJoin model = new ActivityUserJoin();
        BeanUtils.copyProperties(createValidate, model);
        Date now = new Date();
        model.setId(defaultIdentifierGenerator.nextId(model));
        model.setUserId(createValidate.getCreateUserId());
        model.setCreateTime(now);
        model.setUpdateTime(now);
        model.setJoinTime(now);
        model.setIsDelete(0);
        activityUserJoinMapper.insert(model);
        return true;
    }

    @Override
    public void edit(ActivityUserJoinUpdateValidate updateValidate) {
        ActivityUserJoin model = activityUserJoinMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityUserJoinMapper.updateById(model);
    }

    @Override
    public void del(Long id) {
        int i = activityUserJoinMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在");
    }
}
