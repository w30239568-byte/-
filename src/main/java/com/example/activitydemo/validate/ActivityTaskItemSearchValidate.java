package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;


@Data
public class ActivityTaskItemSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /*** 名称 ***/
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    private Integer type;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
