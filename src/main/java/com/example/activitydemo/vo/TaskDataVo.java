package com.example.activitydemo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDataVo {

    /**
     * 打卡时间
     */
    private Date signTime;

    /**
     * 任务名字
     */
    private String taskName;
    /**
     * 任务状态,(0:已完成 1:未完成)
     */
    private Integer taskStatus;

    private String icon;

    private Integer moodStatus;
    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 打卡资源，图片，视频
     */
    private String resources;
    /**
     * 打卡内容
     */
    private String content;
    /**
     * AI专家点评
     */
    private String aiComment;

}
