package com.example.activitydemo.validate;

import com.example.activitydemo.vo.ActivityTaskItemVo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动
 */
@Data
public class ActivityCreateValidate {


    /*** 名称 ***/
    private String name;

    /*** 内容 ***/
    private String content;

    /*** 描述 ***/
    private String remark;

    /**
     * banner背景图
     */
    private String bannerImg;

    /*** 限制类型 ***/
    private Integer limitType;

    /*** 人数限制 ***/
    private Integer limitNum;

    /**
     * 颜色（保存各个地方的颜色信息）
     */
    private String colorMsg;

    /*** 报名人数 ***/
    private Integer joinNum;
    /**
     * 是否精选: 1.是 2.否
     */
    private Integer isHot;

    /*** 类型 ***/
    private Integer type;
    /**
     * 打卡形式: 1.表情打卡 2.拍照打卡 3.视频打卡
     */
    private Integer taskType;

    /*** 类型对应值 ***/
    private String val;

    /*** 开始时间 ***/
    private Date startTime;

    /*** 结束时间 ***/
    private Date endTime;

    /*** 封面图 ***/
    private String coverImg;

    /*** 介绍图 ***/
    private String introduceImg;

    /*** 状态 ***/
//    private Integer status;

    private List<ActivityTaskItemVo> taskList;

    /*** 创建人ID ***/
    private Integer createUserId;

    /*** 更新人ID ***/
    private Integer updateUserId;

}
