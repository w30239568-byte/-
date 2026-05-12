package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityUserJoin;
import com.example.activitydemo.validate.ActivityUserJoinCreateValidate;
import com.example.activitydemo.validate.ActivityUserJoinSearchValidate;
import com.example.activitydemo.validate.ActivityUserJoinUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityUserJoinListedVo;
import com.example.activitydemo.basecommon.PageResult;

/**
 * 用户活动报名服务接口类
 *
 * @author fei
 */
public interface IActivityUserJoinService extends IService<ActivityUserJoin> {

    /**
     * 用户活动报名列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserJoinListedVo>
     * @author fei
     */
    PageResult<ActivityUserJoinListedVo> list(PageValidate pageValidate, ActivityUserJoinSearchValidate searchValidate);

    /**
     * 用户活动报名详情
     *
     * @param id 主键ID
     * @return ActivityUserJoinDetailVo
     * @author fei
     */
    ActivityUserJoinListedVo detail(Long id);

    /**
     * 用户活动报名新增
     *
     * @param createValidate 参数
     * @author fei
     */
    Boolean add(ActivityUserJoinCreateValidate createValidate);

    /**
     * 用户活动报名编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityUserJoinUpdateValidate updateValidate);

    /**
     * 用户活动报名删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

    String activitySignUp(Long activityId,Long userId,Long targetId);

}

