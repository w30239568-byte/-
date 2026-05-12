package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;


@Data
public class ActivityUserTaskRecordSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

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
