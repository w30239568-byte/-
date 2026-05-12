package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 活动与任务关联
 */
@Data
public class ActivityTaskRelationCreateValidate {


    /*** 活动id ***/
    @NotNull(message = "activityId不能为空")
    @Min(value = 1, message = "activityId必须大于0")
    private Long activityId;

    /*** 任务id ***/
    @NotNull(message = "taskItemId不能为空")
    @Min(value = 1, message = "taskItemId必须大于0")
    private Long taskItemId;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
