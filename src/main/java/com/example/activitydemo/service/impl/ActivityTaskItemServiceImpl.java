package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.mapper.ActivityTaskItemMapper;
import com.example.activitydemo.mapper.ActivityTaskRelationMapper;
import com.example.activitydemo.service.IActivityTaskItemService;
import com.example.activitydemo.validate.ActivityTaskItemCreateValidate;
import com.example.activitydemo.validate.ActivityTaskItemSearchValidate;
import com.example.activitydemo.validate.ActivityTaskItemUpdateValidate;
import com.example.activitydemo.vo.ActivityTaskItemDetailVo;
import com.example.activitydemo.vo.ActivityTaskItemListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 任务列实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityTaskItemServiceImpl extends ServiceImpl<ActivityTaskItemMapper, ActivityTaskItem> implements IActivityTaskItemService {

    @Resource
    private ActivityTaskItemMapper activityTaskItemMapper;
    @Resource
    private DefaultIdentifierGenerator defaultIdentifierGenerator;
    @Resource
    private ActivityTaskRelationMapper activityTaskRelationMapper;

    /**
     * 任务列列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityTaskItemListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityTaskItemListedVo> list(PageValidate pageValidate, ActivityTaskItemSearchValidate searchValidate) {
        Page<ActivityTaskItem> page = new Page<>(pageValidate.getPageNo(), 1000);
        IPage<ActivityTaskItemListedVo> list = activityTaskItemMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 任务列详情
     *
     * @param id 主键参数
     * @return ActivityTaskItem
     * @author fei
     */
    @Override
    public ActivityTaskItemDetailVo detail(Long id) {
        ActivityTaskItem model = activityTaskItemMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityTaskItemDetailVo vo = new ActivityTaskItemDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 任务列新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityTaskItemCreateValidate createValidate) {
        ActivityTaskItem model = new ActivityTaskItem();
        BeanUtils.copyProperties(createValidate, model);
        model.setId(defaultIdentifierGenerator.nextId(model));
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityTaskItemMapper.insert(model);
    }

    /**
     * 任务列编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityTaskItemUpdateValidate updateValidate) {
        ActivityTaskItem model = activityTaskItemMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityTaskItemMapper.updateById(model);
    }

    /**
     * 任务列删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityTaskItemMapper.deleteById(id);

        activityTaskRelationMapper.deleteRelation(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

