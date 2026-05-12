package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.domain.ActivityAwardRule;
import com.example.activitydemo.mapper.ActivityAwardMapper;
import com.example.activitydemo.mapper.ActivityAwardRuleMapper;
import com.example.activitydemo.service.IActivityAwardRuleService;
import com.example.activitydemo.validate.ActivityAwardRuleCreateValidate;
import com.example.activitydemo.validate.ActivityAwardRuleSearchValidate;
import com.example.activitydemo.validate.ActivityAwardRuleUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityAwardRuleDetailVo;
import com.example.activitydemo.vo.ActivityAwardRuleListedVo;
import com.example.activitydemo.basecommon.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 奖励规则实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityAwardRuleServiceImpl extends ServiceImpl<ActivityAwardRuleMapper, ActivityAwardRule> implements IActivityAwardRuleService {

    @Resource
    private ActivityAwardRuleMapper activityAwardRuleMapper;
    @Resource
    private ActivityAwardMapper activityAwardMapper;

    /**
     * 奖励规则列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityAwardRuleListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityAwardRuleListedVo> list(PageValidate pageValidate, ActivityAwardRuleSearchValidate searchValidate) {
        Page<ActivityAwardRule> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityAwardRuleListedVo> list = activityAwardRuleMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 奖励规则详情
     *
     * @param id 主键参数
     * @return ActivityAwardRule
     * @author fei
     */
    @Override
    public ActivityAwardRuleDetailVo detail(Long id) {
        ActivityAwardRule model = activityAwardRuleMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityAwardRuleDetailVo vo = new ActivityAwardRuleDetailVo();
        BeanUtils.copyProperties(model, vo);
        ActivityAward activityAward = activityAwardMapper.selectById(model.getActivityAwardId());
        vo.setEquityName(activityAward.getEquityName());
        vo.setDetailType(activityAward.getDetailType());
        vo.setAwardName(activityAward.getName());
        return vo;
    }

    /**
     * 奖励规则新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityAwardRuleCreateValidate createValidate) {
        ActivityAwardRule model = new ActivityAwardRule();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityAwardRuleMapper.insert(model);
    }

    /**
     * 奖励规则编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityAwardRuleUpdateValidate updateValidate) {
        ActivityAwardRule model = activityAwardRuleMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityAwardRuleMapper.updateById(model);
    }

    /**
     * 奖励规则删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityAwardRuleMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

