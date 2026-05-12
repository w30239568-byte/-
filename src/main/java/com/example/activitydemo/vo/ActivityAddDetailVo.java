package com.example.activitydemo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActivityAddDetailVo {

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

    /**
     * 任务列表
     */
    private List<Long> taskList;


}
