package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;


@Data
public class ActivityTaskRelationSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /*** 活动id ***/
    private Long activityId;

    /*** 任务id ***/
    private Long taskItemId;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
