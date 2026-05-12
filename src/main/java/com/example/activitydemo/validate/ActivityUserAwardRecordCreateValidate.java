package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户奖品记录
 */
@Data
public class ActivityUserAwardRecordCreateValidate {


    /*** 用户id ***/
    @NotNull(message = "userId不能为空")
    @Min(value = 1, message = "userId必须大于0")
    private Long userId;

    /*** 奖励id ***/
    @NotNull(message = "awardId不能为空")
    @Min(value = 1, message = "awardId必须大于0")
    private Long awardId;

    /*** 类型 ***/
    private Integer type;

    /*** 权益 ***/
    private String award;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
