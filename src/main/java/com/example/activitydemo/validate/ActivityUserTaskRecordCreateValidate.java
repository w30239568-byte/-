package com.example.activitydemo.validate;

import lombok.Data;

/**
 * 用户任务记录
 */
@Data
public class ActivityUserTaskRecordCreateValidate {


    /*** 用户id ***/
    private Long userId;

    /*** 活动id ***/
    private Long activityId;

    /*** 活动任务id ***/
    private Long taskItemId;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
