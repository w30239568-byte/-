package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务列
 */
@Data
public class ActivityTaskItemCreateValidate {


    /*** 名称 ***/
    @NotBlank(message = "任务名称不能为空")
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    @NotNull(message = "type不能为空")
    private Integer type;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
