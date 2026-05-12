package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户任务记录
 */
@Data
public class ActivityUserTaskRecordListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;
    /*** 用户id ***/
    private Long userId;
    /**
     * 用户名字
     */
    private String nickname;

    private String avatar;

    /*** 活动id ***/
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /*** 活动任务id ***/
    private Long taskItemId;
    /**
     * 任务名称
     */
    private String taskName;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

    /*** 创建时间 ***/
    private Date createTime;
    /*** 更新时间 ***/
    private Date updateTime;

}
