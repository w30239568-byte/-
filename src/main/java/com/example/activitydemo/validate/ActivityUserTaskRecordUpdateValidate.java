package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户任务记录
 *
 * @author fei
 */
@Data
public class ActivityUserTaskRecordUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须大于0")
    private Long id;

    /*** 用户id ***/
    private Long userId;

    /*** 活动id ***/
    private Long activityId;

    /*** 活动任务id ***/
    private Long taskItemId;

    /*** 创建人ID ***/
    private Long createUserId;

    /*** 更新人ID ***/
    private Long updateUserId;

}
