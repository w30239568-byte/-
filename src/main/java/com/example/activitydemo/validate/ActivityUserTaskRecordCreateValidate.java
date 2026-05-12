package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户任务记录
 */
@Data
public class ActivityUserTaskRecordCreateValidate {


    /*** 用户id ***/
    @NotNull(message = "userId不能为空")
    @Min(value = 1, message = "userId必须大于0")
    private Long userId;

    /*** 活动id ***/
    @NotNull(message = "activityId不能为空")
    @Min(value = 1, message = "activityId必须大于0")
    private Long activityId;

    /*** 活动任务id ***/
    @NotNull(message = "taskItemId不能为空")
    @Min(value = 1, message = "taskItemId必须大于0")
    private Long taskItemId;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
