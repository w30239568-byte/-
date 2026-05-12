package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;


@Data
public class ActivityUserJoinSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;


    // 当前分页
    @DecimalMin(value = "1", message = "pageNo参数必须大于0的数字")
    public Integer pageNo = 1;

    // 每页条数
    @DecimalMin(value = "1", message = "pageSize参数必须是大于0的数字")
    @DecimalMax(value = "60", message = "pageSize参数必须是小于60的数字")
    private Integer pageSize = 20;
    /*** 活动 id ***/
    private Long activityId;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
