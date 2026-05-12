package com.example.activitydemo.vo;

import lombok.Data;

@Data
public class ActivitySignInVo {


    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 心情状态,1要加油，2一般般，3棒棒哒
     */
    private Integer moodStatus;

    /**
     * 打卡资源
     */
    private String resource;

    /**
     * 打卡内容
     */
    private String content;

    /**
     * AI专家点评
     */
    private String aiComment;

    private Integer isOpen;


}
