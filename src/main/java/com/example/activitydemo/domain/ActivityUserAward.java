package com.example.activitydemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 用户奖品
 */
@Data
public class ActivityUserAward {

    /***  ***/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /*** 奖品id ***/
    private Long activityAwardId;

    /*** 名称 ***/
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    private Integer type;

    /*** 数量 ***/
    private Integer num;

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
