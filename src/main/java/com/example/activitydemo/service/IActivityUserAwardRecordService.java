package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityUserAwardRecord;
import com.example.activitydemo.validate.ActivityUserAwardRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityUserAwardRecordDetailVo;
import com.example.activitydemo.vo.ActivityUserAwardRecordListedVo;
import com.example.activitydemo.basecommon.PageResult;

/**
 * 用户奖品记录服务接口类
 *
 * @author fei
 */
public interface IActivityUserAwardRecordService extends IService<ActivityUserAwardRecord> {

    /**
     * 用户奖品记录列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserAwardRecordListedVo>
     * @author fei
     */
    PageResult<ActivityUserAwardRecordListedVo> list(PageValidate pageValidate, ActivityUserAwardRecordSearchValidate searchValidate);

    /**
     * 用户奖品记录详情
     *
     * @param id 主键ID
     * @return ActivityUserAwardRecordDetailVo
     * @author fei
     */
    ActivityUserAwardRecordDetailVo detail(Long id);

    /**
     * 用户奖品记录新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityUserAwardRecordCreateValidate createValidate);

    /**
     * 用户奖品记录编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityUserAwardRecordUpdateValidate updateValidate);

    /**
     * 用户奖品记录删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

}

