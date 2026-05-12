package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;


@Data
public class ActivityAwardRuleSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /*** 名称 ***/
    private String name;

    /*** 奖品id ***/
    private Long activityAwardId;

    private Integer awardType;

    /*** 活动id ***/
    private Long activityId;

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
