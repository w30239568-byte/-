package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户活动报名
 */
@Data
public class ActivityUserJoinListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户电话
     */
    private String mobile;

    /*** 活动 id ***/
    private Long activityId;

    /*** 活动名称 ***/
    private String activityName;

    /*** 完成状态 0:已完成 1:未完成 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

    /*** 创建时间 ***/
    private Date createTime;
    /*** 更新时间 ***/
    private Date updateTime;

}
