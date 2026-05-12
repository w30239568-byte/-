package com.example.activitydemo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * 奖品
 */
@Data
public class ActivityAward {

    /***  ***/
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /*** 名称 ***/
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    private Integer type;

    /**
     * 详细类型: 1.活动勋章 2.成就勋章 3.vip 4.成长值 5.聊天次数 6.通话时长
     */
    private Integer detailType;

    /**
     * 权益名称
     */
    private String equityName;

    /**
     * 单位
     */
    private String units;

    /*** 权益 ***/
    private String award;

    /*** 使用个数 ***/
    private Integer useNum;

    /*** 限制类型 ***/
    private Integer limitType;

    /*** 数量限制 ***/
    private Integer limitNum;

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
