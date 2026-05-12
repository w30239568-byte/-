package com.example.activitydemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityUserTaskRecord {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long activityId;

    private Long taskItemId;

    private Integer moodStatus;

    private String resources;

    private String content;

    private String aiComment;

    private Integer isOpen;

    private String requestKey;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer isDelete;
}
