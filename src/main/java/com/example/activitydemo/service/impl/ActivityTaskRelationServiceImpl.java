package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.ActivityTaskRelation;
import com.example.activitydemo.mapper.ActivityTaskRelationMapper;
import com.example.activitydemo.service.IActivityTaskRelationService;
import com.example.activitydemo.validate.ActivityTaskRelationCreateValidate;
import com.example.activitydemo.validate.ActivityTaskRelationSearchValidate;
import com.example.activitydemo.validate.ActivityTaskRelationUpdateValidate;
import com.example.activitydemo.vo.ActivityTaskRelationDetailVo;
import com.example.activitydemo.vo.ActivityTaskRelationListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 活动与任务关联实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityTaskRelationServiceImpl extends ServiceImpl<ActivityTaskRelationMapper, ActivityTaskRelation> implements IActivityTaskRelationService {

    @Resource
    private ActivityTaskRelationMapper activityTaskRelationMapper;

    /**
     * 活动与任务关联列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityTaskRelationListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityTaskRelationListedVo> list(PageValidate pageValidate, ActivityTaskRelationSearchValidate searchValidate) {
        Page<ActivityTaskRelation> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityTaskRelationListedVo> list = activityTaskRelationMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 活动与任务关联详情
     *
     * @param id 主键参数
     * @return ActivityTaskRelation
     * @author fei
     */
    @Override
    public ActivityTaskRelationDetailVo detail(Long id) {
        ActivityTaskRelation model = activityTaskRelationMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityTaskRelationDetailVo vo = new ActivityTaskRelationDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 活动与任务关联新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityTaskRelationCreateValidate createValidate) {
        ActivityTaskRelation model = new ActivityTaskRelation();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityTaskRelationMapper.insert(model);
    }

    /**
     * 活动与任务关联编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityTaskRelationUpdateValidate updateValidate) {
        ActivityTaskRelation model = activityTaskRelationMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityTaskRelationMapper.updateById(model);
    }

    /**
     * 活动与任务关联删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityTaskRelationMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

