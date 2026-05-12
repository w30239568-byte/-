package com.example.activitydemo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.ActivityUserAwardRecord;
import com.example.activitydemo.mapper.ActivityUserAwardRecordMapper;
import com.example.activitydemo.service.IActivityUserAwardRecordService;
import com.example.activitydemo.validate.ActivityUserAwardRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordUpdateValidate;
import com.example.activitydemo.vo.ActivityUserAwardRecordDetailVo;
import com.example.activitydemo.vo.ActivityUserAwardRecordListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户奖品记录实现类
 *
 * @author fei
 */
@Slf4j
@Service
public class ActivityUserAwardRecordServiceImpl extends ServiceImpl<ActivityUserAwardRecordMapper, ActivityUserAwardRecord> implements IActivityUserAwardRecordService {

    @Resource
    private ActivityUserAwardRecordMapper activityUserAwardRecordMapper;

    /**
     * 用户奖品记录列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserAwardRecordListedVo>
     * @author fei
     */
    @Override
    public PageResult<ActivityUserAwardRecordListedVo> list(PageValidate pageValidate, ActivityUserAwardRecordSearchValidate searchValidate) {
        Page<ActivityUserAwardRecord> page = new Page<>(pageValidate.getPageNo(), pageValidate.getPageSize());
        IPage<ActivityUserAwardRecordListedVo> list = activityUserAwardRecordMapper.list(page, searchValidate);
        return PageResult.iPageHandle(list.getTotal(), list.getCurrent(), list.getSize(), list.getRecords());
    }

    /**
     * 用户奖品记录详情
     *
     * @param id 主键参数
     * @return ActivityUserAwardRecord
     * @author fei
     */
    @Override
    public ActivityUserAwardRecordDetailVo detail(Long id) {
        ActivityUserAwardRecord model = activityUserAwardRecordMapper.selectById(id);
        Assert.notNull(model, "数据不存在");

        ActivityUserAwardRecordDetailVo vo = new ActivityUserAwardRecordDetailVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    /**
     * 用户奖品记录新增
     *
     * @param createValidate 参数
     * @author fei
     */
    @Override
    public void add(ActivityUserAwardRecordCreateValidate createValidate) {
        ActivityUserAwardRecord model = new ActivityUserAwardRecord();
        BeanUtils.copyProperties(createValidate, model);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        activityUserAwardRecordMapper.insert(model);
    }

    /**
     * 用户奖品记录编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    @Override
    public void edit(ActivityUserAwardRecordUpdateValidate updateValidate) {
        ActivityUserAwardRecord model = activityUserAwardRecordMapper.selectById(updateValidate.getId());
        Assert.notNull(model, "数据不存在!");
        BeanUtils.copyProperties(updateValidate, model);
        model.setUpdateTime(new Date());
        activityUserAwardRecordMapper.updateById(model);
    }

    /**
     * 用户奖品记录删除
     *
     * @param id 主键ID
     * @author fei
     */
    @Override
    public void del(Long id) {
        int i = activityUserAwardRecordMapper.deleteById(id);
        Assert.isTrue(i > 0, "数据不存在!");
    }

}

