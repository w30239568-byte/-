package com.example.activitydemo.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户活动报名实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityUserJoinServiceImpl extends ServiceImpl<ActivityUserJoinMapper, ActivityUserJoin> implements IActivityUserJoinService {

    @Resource
    private ActivityUserJoinMapper activityUserJoinMapper;
    @Resource
    private ActivityMapper activityMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String activitySignUp(Long activityId, Long userId, Long targetId) {

        Activity activity = activityMapper.selectById(activityId);
        if (activityUserJoinMapper.getByUserIdAndActivityId(activityId, userId) != null) {
            return "当前活动已参与，请误重复报名！";
        }
        //判断活动是否结束
        if (System.currentTimeMillis() > activity.getEndTime().getTime()) {
            return "活动已结束！";
        }
        //判断参与人数是否达到上限
        if (activity.getLimitNum() != null && activity.getLimitType() == 1 && activity.getJoinNum() + 1 > activity.getLimitNum()) {
            return "参与人数达到上限！";
        }
        ActivityUserJoin activityUserJoin = new ActivityUserJoin();
        activityUserJoin.setId(defaultIdentifierGenerator.nextId(activityUserJoin));
        activityUserJoin.setActivityId(activityId);
        activityUserJoin.setStatus(0);
        activityUserJoin.setCreateUserId(userId);
        activityUserJoin.setUpdateUserId(userId);
        activityUserJoin.setIsDelete(0);
        if (targetId != null) {
            activityUserJoin.setJoinType(1);
            activityUserJoin.setTargetId(targetId);
            activityUserJoin.setDeptId(inviteAgentMapper.selectById(targetId).getDeptId());
        } else {
            activityUserJoin.setJoinType(0);
        }
        Date nowDate = new Date();
        activityUserJoin.setCreateTime(nowDate);
        activityUserJoin.setUpdateTime(nowDate);
        if (activity.getStartTime().getTime() > nowDate.getTime()) {
            activityUserJoin.setJoinTime(activity.getStartTime());
        } else {
            activityUserJoin.setJoinTime(nowDate);
        }
        activityUserJoinMapper.insert(activityUserJoin);
        //更新报名人数字段
        Integer joinNum = activity.getJoinNum() == null ? 0 : activity.getJoinNum();
        activity.setJoinNum(joinNum + 1);
        activityMapper.updateById(activity);
        return "success";
    }

    /**
     * 用户活动报名列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserJoinListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityUserJoinListedVo> list(PageValidate pageValidate, ActivityUserJoinSearchValidate searchValidate) {
        Page<ActivityUserJoin> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserJoinListedVo> list = activityUserJoinMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 用户活动报名详情
     *
     * @param id 主键参数
     * @return ActivityUserJoin
     * @author fei
     */
    @Override
    public ActivityUserJoinListedVo detail(Long id) {
        ActivityUserJoin model = activityUserJoinMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        return null;
    }

    /**
     * 用户活动报名新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public Boolean add(ActivityUserJoinCreateValidate createValidate) {
        //判断是否超过报名限制
        Activity activity = activityMapper.selectById(createValidate.getActivityId());
        Integer limitType = activity.getLimitType();
        if (limitType == 1) {
            Integer joinNum = activity.getJoinNum();
            Integer limitNum = activity.getLimitNum();
            if (joinNum + 1 > limitNum) {
                return false;
            }
        }
        ActivityUserJoin model = new ActivityUserJoin();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityUserJoinMapper.insert(model);
        return true;
    }

    /**
     * 用户活动报名编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityUserJoinUpdateValidate updateValidate) {
        ActivityUserJoin model = activityUserJoinMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityUserJoinMapper.updateById(model);
    }

    /**
     * 用户活动报名删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityUserJoinMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

