package com.example.activitydemo.basecommon;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PageValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    // 当前分页
    @NotNull(message = "pageNo不能为空")
    @Min(value = 1, message = "pageNo最小为1")
    private Integer pageNo = 1;

    // 每页条数
    @NotNull(message = "pageSize不能为空")
    @Min(value = 1, message = "pageSize最小为1")
    @Max(value = 100, message = "pageSize最大为100")
    private Integer pageSize = 20;

}
