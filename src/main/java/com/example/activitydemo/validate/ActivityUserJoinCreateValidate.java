package com.example.activitydemo.validate;

import lombok.Data;

/**
 * 用户活动报名
 */
@Data
public class ActivityUserJoinCreateValidate {


    /*** 活动 id ***/
    private Long activityId;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
