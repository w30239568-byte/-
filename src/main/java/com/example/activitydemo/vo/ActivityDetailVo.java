package com.example.activitydemo.vo;

import com.example.activitydemo.domain.ActivityTaskItem;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ActivityDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String content;

    private String bannerImg;

    private String remark;

    private String colorMsg;

    private Integer limitType;

    private Integer limitNum;

    private Integer joinNum;

    private Integer isHot;

    private Integer type;

    private Integer taskType;

    private String val;

    private Date startTime;

    private Date endTime;

    private String coverImg;

    private String introduceImg;

    private Integer status;

    private Long createUserId;

    private Long updateUserId;

    private Boolean ifSign;

    private Integer isMaterial;

    private List<ActivityTaskItem> taskList;
}
