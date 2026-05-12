package com.example.activitydemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 奖励规则
 */
@Data
public class ActivityAwardRule {

    /***  ***/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /*** 名称 ***/
    private String name;

    /*** 奖品id ***/
    private Long activityAwardId;

    /*** 活动id ***/
    private Long activityId;

    private Integer awardType;

    /*** 类型 ***/
    private Integer type;

    /*** 配置 ***/
    private String configVal;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

    /*** 创建时间 ***/
    private Date createTime;

    /*** 更新时间 ***/
    private Date updateTime;

    /*** 是否删除 ***/
    @TableLogic
    private Integer isDelete;

}
