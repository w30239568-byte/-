package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.Activity;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.validate.ActivityCreateValidate;
import com.example.activitydemo.validate.ActivitySearchValidate;
import com.example.activitydemo.validate.ActivityUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityDetailVo;
import com.example.activitydemo.vo.ActivityListedVo;
import com.example.activitydemo.basecommon.PageResult;

import java.util.List;

/**
 * 活动服务接口类
 *
 * @author fei
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 活动列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityListedVo>
     * @author fei
     */
    PageResult<ActivityListedVo> list(PageValidate pageValidate, ActivitySearchValidate searchValidate);

    /**
     * 活动详情
     *
     * @param id 主键ID
     * @return ActivityDetailVo
     * @author fei
     */
    ActivityDetailVo detail(Long id);

    /**
     * 活动新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityCreateValidate createValidate,Long userId);

    /**
     * 活动编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    Boolean edit(ActivityUpdateValidate updateValidate,Long userId);

    /**
     * 活动删除
     *
     * @param id 主键ID
     * @author fei
     */
    Boolean del(Long id);

    /**
     * 获取活动信息
     *
     * @param id
     * @return
     */
    ActivityDetailVo getActivityDetail(Long id, Long userId);

    List<ActivityTaskItem> getTaskList(Long activityId, Long userId);


}

