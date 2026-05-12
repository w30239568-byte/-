package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户奖品
 */
@Data
public class ActivityUserAwardCreateValidate {


    /*** 奖品id ***/
    @NotNull(message = "activityAwardId不能为空")
    @Min(value = 1, message = "activityAwardId必须大于0")
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

}
