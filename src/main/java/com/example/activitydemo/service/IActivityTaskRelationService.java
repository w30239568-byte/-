package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityTaskRelation;
import com.example.activitydemo.validate.ActivityTaskRelationCreateValidate;
import com.example.activitydemo.validate.ActivityTaskRelationSearchValidate;
import com.example.activitydemo.validate.ActivityTaskRelationUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityTaskRelationDetailVo;
import com.example.activitydemo.vo.ActivityTaskRelationListedVo;
import com.example.activitydemo.basecommon.PageResult;

/**
 * 活动与任务关联服务接口类
 *
 * @author fei
 */
public interface IActivityTaskRelationService extends IService<ActivityTaskRelation> {

    /**
     * 活动与任务关联列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityTaskRelationListedVo>
     * @author fei
     */
    PageResult<ActivityTaskRelationListedVo> list(PageValidate pageValidate, ActivityTaskRelationSearchValidate searchValidate);

    /**
     * 活动与任务关联详情
     *
     * @param id 主键ID
     * @return ActivityTaskRelationDetailVo
     * @author fei
     */
    ActivityTaskRelationDetailVo detail(Long id);

    /**
     * 活动与任务关联新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityTaskRelationCreateValidate createValidate);

    /**
     * 活动与任务关联编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityTaskRelationUpdateValidate updateValidate);

    /**
     * 活动与任务关联删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

}

