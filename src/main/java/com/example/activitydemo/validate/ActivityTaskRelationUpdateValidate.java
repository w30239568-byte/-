package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 活动与任务关联
 *
 * @author fei
 */
@Data
public class ActivityTaskRelationUpdateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    /***  ***/
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id必须大于0")
    private Long id;

    /*** 活动id ***/
    private Long activityId;

    /*** 任务id ***/
    private Long taskItemId;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
