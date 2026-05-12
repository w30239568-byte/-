package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 奖励规则
 */
@Data
public class ActivityAwardRuleCreateValidate {


    /*** 名称 ***/
    @NotBlank(message = "规则名称不能为空")
    private String name;

    /*** 奖品id ***/
    @NotNull(message = "activityAwardId不能为空")
    @Min(value = 1, message = "activityAwardId必须大于0")
    private Long activityAwardId;

    /*** 活动id ***/
    @NotNull(message = "activityId不能为空")
    @Min(value = 1, message = "activityId必须大于0")
    private Long activityId;

    private Integer awardType;

    /*** 类型 ***/
    @NotNull(message = "type不能为空")
    private Integer type;

    /*** 配置 ***/
    @NotBlank(message = "configVal不能为空")
    private String configVal;

    /*** 状态 ***/
    @NotNull(message = "status不能为空")
    private Integer status;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
