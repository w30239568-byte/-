package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.ActivityUserAward;
import com.example.activitydemo.mapper.ActivityUserAwardMapper;
import com.example.activitydemo.service.IActivityUserAwardService;
import com.example.activitydemo.validate.ActivityUserAwardCreateValidate;
import com.example.activitydemo.validate.ActivityUserAwardSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardUpdateValidate;
import com.example.activitydemo.vo.ActivityUserAwardDetailVo;
import com.example.activitydemo.vo.ActivityUserAwardListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户奖品实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityUserAwardServiceImpl extends ServiceImpl<ActivityUserAwardMapper, ActivityUserAward> implements IActivityUserAwardService {

    @Resource
    private ActivityUserAwardMapper activityUserAwardMapper;

    /**
     * 用户奖品列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserAwardListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityUserAwardListedVo> list(PageValidate pageValidate, ActivityUserAwardSearchValidate searchValidate) {
        Page<ActivityUserAward> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserAwardListedVo> list = activityUserAwardMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 用户奖品详情
     *
     * @param id 主键参数
     * @return ActivityUserAward
     * @author fei
     */
    @Override
    public ActivityUserAwardDetailVo detail(Long id) {
        ActivityUserAward model = activityUserAwardMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityUserAwardDetailVo vo = new ActivityUserAwardDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 用户奖品新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityUserAwardCreateValidate createValidate) {
        ActivityUserAward model = new ActivityUserAward();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityUserAwardMapper.insert(model);
    }

    /**
     * 用户奖品编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityUserAwardUpdateValidate updateValidate) {
        ActivityUserAward model = activityUserAwardMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityUserAwardMapper.updateById(model);
    }

    /**
     * 用户奖品删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityUserAwardMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

