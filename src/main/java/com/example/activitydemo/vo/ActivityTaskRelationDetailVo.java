package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 活动与任务关联
 */
@Data
public class ActivityTaskRelationDetailVo implements Serializable {

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


}
