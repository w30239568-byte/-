package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class ActivitySearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /*** 名称 ***/
    private String name;

    /*** 内容 ***/
    private String content;

    /*** 描述 ***/
    private String remark;

    /*** 限制类型 ***/
    private Integer limitType;

    /*** 人数限制 ***/
    private Integer limitNum;

    /*** 报名人数 ***/
    private Integer joinNum;

    /*** 类型 ***/
    private Integer type;

    /*** 类型对应值 ***/
    private String val;

    /**
     * 活动创建形式: 1.通用活动 2.定制活动
     */
    private Integer createType;

    /*** 开始时间 ***/
    private Date startTime;

    /*** 结束时间 ***/
    private Date endTime;

    /*** 封面图 ***/
    private String coverImg;

    /*** 介绍图 ***/
    private String introduceImg;

    /*** 状态 0:未开始 1:进行中 2:已结束***/
    private Integer status;

    private Date nowDate;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
