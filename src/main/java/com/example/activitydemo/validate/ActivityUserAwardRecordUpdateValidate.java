package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户奖品记录
 *
 * @author fei
 */
@Data
public class ActivityUserAwardRecordUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须大于0")
    private Long id;

    /*** 用户id ***/
    private Long userId;

    /*** 奖励id ***/
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
