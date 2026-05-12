package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 奖品
 */
@Data
public class ActivityAwardCreateValidate {


    /*** 名称 ***/
    @NotBlank(message = "奖品名称不能为空")
    @Size(max = 64, message = "奖品名称长度不能超过64")
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    @NotNull(message = "type不能为空")
    private Integer type;

    /**
     * 详细类型: 1.活动勋章 2.成就勋章 3.vip 4.成长值 5.聊天次数 6.通话时长
     */
    private Integer detailType;

    /**
     * 权益名称
     */
    private String equityName;

    /*** 权益 ***/
    private String award;

    private String units;

    /*** 使用个数 ***/
    private Integer useNum;

    /*** 限制类型 ***/
    private Integer limitType;

    /*** 数量限制 ***/
    private Integer limitNum;

    /*** 状态 ***/
    @NotNull(message = "status不能为空")
    private Integer status;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
