package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动与任务关联
 */
@Data
public class ActivityTaskRelationListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 活动id ***/
    private Long activityId;

    /*** 任务id ***/
    private Long taskItemId;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

    /*** 创建时间 ***/
    private Date createTime;
    /*** 更新时间 ***/
    private Date updateTime;

}
