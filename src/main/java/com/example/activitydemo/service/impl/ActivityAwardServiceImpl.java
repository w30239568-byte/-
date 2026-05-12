package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.domain.ActivityAwardRule;
import com.example.activitydemo.mapper.ActivityAwardMapper;
import com.example.activitydemo.mapper.ActivityAwardRuleMapper;
import com.example.activitydemo.mapper.ActivityUserAwardRecordMapper;
import com.example.activitydemo.service.IActivityAwardService;
import com.example.activitydemo.validate.ActivityAwardCreateValidate;
import com.example.activitydemo.validate.ActivityAwardSearchValidate;
import com.example.activitydemo.validate.ActivityAwardUpdateValidate;
import com.example.activitydemo.vo.ActivityAwardDetailVo;
import com.example.activitydemo.vo.ActivityAwardListedVo;
import com.example.activitydemo.vo.ActivityUserAwardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 奖品实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityAwardServiceImpl extends ServiceImpl<ActivityAwardMapper, ActivityAward> implements IActivityAwardService {

    @Resource
    private ActivityAwardMapper activityAwardMapper;
    @Resource
    private ActivityAwardRuleMapper activityAwardRuleMapper;
    @Resource
    private ActivityUserAwardRecordMapper activityUserAwardRecordMapper;
    @Resource
    private DefaultIdentifierGenerator defaultIdentifierGenerator;

    /**
     * 奖品列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityAwardListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityAwardListedVo> list(PageValidate pageValidate, ActivityAwardSearchValidate searchValidate) {
        Page<ActivityAward> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityAwardListedVo> list = activityAwardMapper.list(page, searchValidate);
        PageResult<ActivityAwardListedVo> result = PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
        if (searchValidate.getIsUse()) {
            List<ActivityAwardListedVo> lists = result.getLists();
            //排除已使用的
            List<ActivityAwardRule> allRules = activityAwardRuleMapper.getAllRules();
            if (allRules != null) {
                List<Long> collect = allRules.stream().map(ActivityAwardRule::getActivityAwardId).collect(Collectors.toList());
                List<ActivityAwardListedVo> collectNew = lists.stream().filter(activityAwardListedVo -> !collect.contains(activityAwardListedVo.getId())).collect(Collectors.toList());
                result.setLists(collectNew);
                result.setCount((long) collectNew.size());
            }
        }
        return result;
    }

    /**
     * 该奖品获得列表
     *
     * @param pageValidate
     * @param searchValidate
     * @return
     */
    @Override
    public PageResult<ActivityUserAwardVo> userGetList(PageValidate pageValidate, ActivityAwardSearchValidate searchValidate) {
        Page<ActivityAward> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserAwardVo> list = activityUserAwardRecordMapper.userGetList(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());

    }

    /**
     * 奖品详情
     *
     * @param id 主键参数
     * @return ActivityAward
     * @author fei
     */
    @Override
    public ActivityAwardDetailVo detail(Long id) {
        ActivityAward model = activityAwardMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityAwardDetailVo vo = new ActivityAwardDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 奖品新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityAwardCreateValidate createValidate) {
        ActivityAward model = new ActivityAward();
        BeanUtils.copyProperties(createValidate, model);
        model.setId(defaultIdentifierGenerator.nextId(model));
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityAwardMapper.insert(model);
    }

    /**
     * 奖品编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityAwardUpdateValidate updateValidate) {
        ActivityAward model = activityAwardMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityAwardMapper.updateById(model);
    }

    /**
     * 奖品删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(Long id) {
        int i = activityAwardMapper.deleteById(id);
        //删除奖品规则
        activityAwardRuleMapper.deleteByAwardId(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

