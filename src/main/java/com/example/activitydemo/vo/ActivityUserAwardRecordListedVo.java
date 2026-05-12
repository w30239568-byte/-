package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户奖品记录
 */
@Data
public class ActivityUserAwardRecordListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 用户id ***/
    private Long userId;

    private String userName;

    /*** 奖励id ***/
    private Long awardId;

    /*** 类型 ***/
    private Integer type;

    /*** 权益 ***/
    private String award;

    private String awardName;

    private String equityName;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

    /*** 创建时间 ***/
    private Date createTime;
    /*** 更新时间 ***/
    private Date updateTime;

}
