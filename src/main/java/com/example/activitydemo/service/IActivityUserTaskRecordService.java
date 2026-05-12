package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityUserTaskRecord;
import com.example.activitydemo.validate.ActivityUserTaskRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityUserTaskRecordListedVo;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.vo.SignMsgVo;

/**
 * 用户任务记录服务接口类
 *
 * @author fei
 */
public interface IActivityUserTaskRecordService extends IService<ActivityUserTaskRecord> {

    /**
     * 用户任务记录列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserTaskRecordListedVo>
     * @author fei
     */
    PageResult<ActivityUserTaskRecordListedVo> list(PageValidate pageValidate, ActivityUserTaskRecordSearchValidate searchValidate);

    /**
     * 用户任务记录详情
     *
     * @param id 主键ID
     * @return ActivityUserTaskRecordDetailVo
     * @author fei
     */
    ActivityUserTaskRecordListedVo detail(Long id);

    /**
     * 用户任务记录新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityUserTaskRecordCreateValidate createValidate);

    /**
     * 用户任务记录编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityUserTaskRecordUpdateValidate updateValidate);

    /**
     * 用户任务记录删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

    Boolean signIn(Long activityId, Long taskId, Long userId, Integer moodStatus, String resource, String content, String aiComment, Integer isOpen, String requestKey);

    SignMsgVo getSignMsg(Long activityId, long userId);
}

