package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.validate.ActivityTaskItemCreateValidate;
import com.example.activitydemo.validate.ActivityTaskItemSearchValidate;
import com.example.activitydemo.validate.ActivityTaskItemUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityTaskItemDetailVo;
import com.example.activitydemo.vo.ActivityTaskItemListedVo;
import com.example.activitydemo.basecommon.PageResult;

/**
 * 任务列服务接口类
 *
 * @author fei
 */
public interface IActivityTaskItemService extends IService<ActivityTaskItem> {

    /**
     * 任务列列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityTaskItemListedVo>
     * @author fei
     */
    PageResult<ActivityTaskItemListedVo> list(PageValidate pageValidate, ActivityTaskItemSearchValidate searchValidate);

    /**
     * 任务列详情
     *
     * @param id 主键ID
     * @return ActivityTaskItemDetailVo
     * @author fei
     */
    ActivityTaskItemDetailVo detail(Long id);

    /**
     * 任务列新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityTaskItemCreateValidate createValidate);

    /**
     * 任务列编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityTaskItemUpdateValidate updateValidate);

    /**
     * 任务列删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

}

