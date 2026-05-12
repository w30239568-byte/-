package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityUserAward;
import com.example.activitydemo.validate.ActivityUserAwardCreateValidate;
import com.example.activitydemo.validate.ActivityUserAwardSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityUserAwardDetailVo;
import com.example.activitydemo.vo.ActivityUserAwardListedVo;
import com.example.activitydemo.basecommon.PageResult;

/**
 * 用户奖品服务接口类
 *
 * @author fei
 */
public interface IActivityUserAwardService extends IService<ActivityUserAward> {

    /**
     * 用户奖品列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityUserAwardListedVo>
     * @author fei
     */
    PageResult<ActivityUserAwardListedVo> list(PageValidate pageValidate, ActivityUserAwardSearchValidate searchValidate);

    /**
     * 用户奖品详情
     *
     * @param id 主键ID
     * @return ActivityUserAwardDetailVo
     * @author fei
     */
    ActivityUserAwardDetailVo detail(Long id);

    /**
     * 用户奖品新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityUserAwardCreateValidate createValidate);

    /**
     * 用户奖品编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityUserAwardUpdateValidate updateValidate);

    /**
     * 用户奖品删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

}

