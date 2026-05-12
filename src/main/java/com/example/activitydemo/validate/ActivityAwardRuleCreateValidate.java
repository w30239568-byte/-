package com.example.activitydemo.validate;

import lombok.Data;

/**
 * 奖励规则
 */
@Data
public class ActivityAwardRuleCreateValidate {


    /*** 名称 ***/
    private String name;

    /*** 奖品id ***/
    private Long activityAwardId;

    /*** 活动id ***/
    private Long activityId;

    private Integer awardType;

    /*** 类型 ***/
    private Integer type;

    /*** 配置 ***/
    private String configVal;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
