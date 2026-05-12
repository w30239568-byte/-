package com.example.activitydemo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动
 */
@Data
public class ActivityListedVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

    /*** 名称 ***/
    private String name;

    /*** 描述 ***/
    private String remark;

    /**
     * banner背景图
     */
    private String bannerImg;

    /*** 限制类型 ***/
    private Integer limitType;

    /*** 人数限制 ***/
    private Integer limitNum;

    /*** 报名人数 ***/
    private Integer joinNum;
    /**
     * 是否精选: 1.是 2.否
     */
    private Integer isHot;

    /**
     * 活动创建形式: 1.通用活动 2.定制活动
     */
    private Integer createType;

    /**
     * 颜色（保存各个地方的颜色信息）
     */
    private String colorMsg;

    /*** 类型 ***/
    private Integer type;
    /**
     * 打卡形式: 1.表情打卡 2.拍照打卡 3.视频打卡
     */
    private Integer taskType;

    /*** 类型对应值 ***/
    private String val;

    /*** 开始时间 ***/
    private Date startTime;
    /*** 结束时间 ***/
    private Date endTime;
    /*** 封面图 ***/
    private String coverImg;

    /*** 介绍图 ***/
    private String introduceImg;

    /*** 状态 ***/
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
