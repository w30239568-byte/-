package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.domain.ActivityAward;
import com.example.activitydemo.validate.ActivityAwardCreateValidate;
import com.example.activitydemo.validate.ActivityAwardSearchValidate;
import com.example.activitydemo.validate.ActivityAwardUpdateValidate;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.vo.ActivityAwardDetailVo;
import com.example.activitydemo.vo.ActivityAwardListedVo;
import com.example.activitydemo.vo.ActivityUserAwardVo;
import com.example.activitydemo.basecommon.PageResult;

/**
 * 奖品服务接口类
 *
 * @author fei
 */
public interface IActivityAwardService extends IService<ActivityAward> {

    /**
     * 奖品列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityAwardListedVo>
     * @author fei
     */
    PageResult<ActivityAwardListedVo> list(PageValidate pageValidate, ActivityAwardSearchValidate searchValidate);

    /**
     * 奖品详情
     *
     * @param id 主键ID
     * @return ActivityAwardDetailVo
     * @author fei
     */
    ActivityAwardDetailVo detail(Long id);

    /**
     * 奖品新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityAwardCreateValidate createValidate);

    /**
     * 奖品编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityAwardUpdateValidate updateValidate);

    /**
     * 奖品删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

    PageResult<ActivityUserAwardVo> userGetList(PageValidate pageValidate, ActivityAwardSearchValidate searchValidate);
}

