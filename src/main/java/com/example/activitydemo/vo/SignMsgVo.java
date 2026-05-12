package com.example.activitydemo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SignMsgVo {


    private Long activityId;

    private Date startTime;

    private String coverImg;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动天数
     */
    private Integer totalNum;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 活动状态，1已结束 2进行中
     */
    private Integer status;

    /**
     * 连续打卡天数
     */
    private Integer continuousNums;

    /**
     * 累计打卡天数
     */
    private Integer signNums;

    /**
     * 是否已全部完成
     */
    private Boolean ifComplete;

    /**
     * 计划banner
     */
    private String planBanner;


    /**
     * 排名
     */
    private Integer rankNum;

    /**
     * 排名百分比
     */
    private String rankPercen;

    /**
     * 奖品信息
     */
    private List<ActivityUserPriceVo> priceList;


    /**
     * 已打卡任务列表
     */
    private List<TaskDataVo> taskList;

    /**
     * 是否展示AI跟进
     */
    private Boolean isShowAiAnalyse;

    /**
     * AI返回的原始报文
     */
    private String oldAiMessage;


    /**
     * 活动创建形式: 1.通用活动 2.定制活动
     */
    private Integer createType;

    /*** 更新时间 ***/
    private Date updateTime;
}
