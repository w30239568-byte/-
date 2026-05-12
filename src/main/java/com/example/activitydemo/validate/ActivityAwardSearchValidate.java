package com.example.activitydemo.validate;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class ActivityAwardSearchValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "pageNo不能为空")
    @Min(value = 1, message = "pageNo最小为1")
    private Integer pageNo = 1;

    @NotNull(message = "pageSize不能为空")
    @Min(value = 1, message = "pageSize最小为1")
    @Max(value = 100, message = "pageSize最大为100")
    private Integer pageSize = 20;

    private Long awardId;


    /*** 名称 ***/
    private String name;

    /*** 图片 ***/
    private String icon;

    /*** 类型 ***/
    private Integer type;

    /**
     * 是否筛选被使用过的
     */
    private Boolean isUse = false;

    /**
     * 详细类型: 1.活动勋章 2.成就勋章 3.vip 4.成长值 5.聊天次数 6.通话时长
     */
    private Integer detailType;

    /*** 权益 ***/
    private String award;

    /**
     * 权益名称
     */
    private String equityName;

    /*** 使用个数 ***/
    private Integer useNum;

    /*** 限制类型 ***/
    private Integer limitType;

    /*** 数量限制 ***/
    private Integer limitNum;

    /*** 状态 ***/
    private Integer status;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
