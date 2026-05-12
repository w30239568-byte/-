package com.example.activitydemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 用户奖品记录
 */
@Data
public class ActivityUserAwardRecord {

    /***  ***/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /*** 用户id ***/
    private Long userId;

    /*** 奖励id ***/
    private Long awardId;

    private Long activityId;

    /*** 类型 ***/
    private Integer type;

    /*** 权益 ***/
    private String award;

    /*** 备注 ***/
    private String remark;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

    /*** 创建时间 ***/
    private Date createTime;

    /*** 更新时间 ***/
    private Date updateTime;

    /*** 是否删除 ***/
    @TableLogic
    private Integer isDelete;

}
