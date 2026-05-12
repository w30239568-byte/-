package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户活动报名
 */
@Data
public class ActivityUserJoinCreateValidate {


    /*** 活动 id ***/
    @NotNull(message = "activityId不能为空")
    @Min(value = 1, message = "activityId必须大于0")
    private Long activityId;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
