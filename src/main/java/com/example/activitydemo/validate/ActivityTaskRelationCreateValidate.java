package com.example.activitydemo.validate;

import lombok.Data;

/**
 * 活动与任务关联
 */
@Data
public class ActivityTaskRelationCreateValidate {


    /*** 活动id ***/
    private Long activityId;

    /*** 任务id ***/
    private Long taskItemId;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
