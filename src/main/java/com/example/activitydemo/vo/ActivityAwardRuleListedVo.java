package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖励规则
 */
@Data
public class ActivityAwardRuleListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 名称 ***/
    private String name;

    /*** 奖品id ***/
    private Long activityAwardId;

    /*** 活动id ***/
    private Long activityId;

    private String activityName;

    private String awardName;

    private Integer awardType;

    private Integer detailType;

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

    /*** 创建时间 ***/
    private Date createTime;
    /*** 更新时间 ***/
    private Date updateTime;

    /**
     * 权益名称
     */
    private String equityName;

}
