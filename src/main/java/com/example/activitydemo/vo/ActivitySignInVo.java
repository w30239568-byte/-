package com.example.activitydemo.vo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ActivitySignInVo {

    @NotNull(message = "userId不能为空")
    @Min(value = 1, message = "userId必须大于0")
    private Long userId;

    @NotNull(message = "activityId不能为空")
    @Min(value = 1, message = "activityId必须大于0")
    private Long activityId;

    @NotNull(message = "taskId不能为空")
    @Min(value = 1, message = "taskId必须大于0")
    private Long taskId;

    @Min(value = 1, message = "moodStatus最小为1")
    @Max(value = 3, message = "moodStatus最大为3")
    private Integer moodStatus;

    @Size(max = 2000, message = "resource长度不能超过2000")
    private String resource;

    @Size(max = 1000, message = "content长度不能超过1000")
    private String content;

    @Size(max = 1000, message = "aiComment长度不能超过1000")
    private String aiComment;

    private Integer isOpen;
}
