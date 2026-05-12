package com.example.activitydemo.validate;

import lombok.Data;

/**
 * 任务列
 */
@Data
public class ActivityTaskItemCreateValidate {


    /*** 名称 ***/
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    private Integer type;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
