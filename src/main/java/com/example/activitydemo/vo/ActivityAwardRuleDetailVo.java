package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 奖励规则
 */
@Data
public class ActivityAwardRuleDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 名称 ***/
    private String name;

    /*** 奖品id ***/
    private Long activityAwardId;

    private Integer awardType;

    private String awardName;

    private Integer detailType;

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

    /**
     * 权益名称
     */
    private String equityName;


}
