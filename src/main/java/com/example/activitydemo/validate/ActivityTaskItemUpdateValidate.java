package com.example.activitydemo.validate;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务列
 *
 * @author fei
 */
@Data
public class ActivityTaskItemUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    private Long id;

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
