package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户奖品
 */
@Data
public class ActivityUserAwardDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 奖品id ***/
    private Long activityAwardId;

    /*** 名称 ***/
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    private Integer type;

    /*** 数量 ***/
    private Integer num;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;


}
