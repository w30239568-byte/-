package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户活动报名
 *
 * @author fei
 */
@Data
public class ActivityUserJoinUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 活动 id ***/
    private Long activityId;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
