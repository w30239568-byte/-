package com.example.activitydemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class ActivityUserJoin {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long activityId;

    private Long userId;

    private Integer status;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date updateTime;

    private Date joinTime;

    @TableLogic
    private Integer isDelete;
}
