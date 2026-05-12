package com.example.activitydemo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityUserAwardVo {

    private Long arawdId;

    /*** 名称 ***/
    private String awardName;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户账户
     */
    private String userAccount;
    /**
     * 手机号
     */
    private String mobile;

    /*** 更新时间 ***/
    private Date updateTime;
}
