package com.example.activitydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.domain.ActivityAwardRule;
import com.example.activitydemo.validate.ActivityAwardRuleCreateValidate;
import com.example.activitydemo.validate.ActivityAwardRuleSearchValidate;
import com.example.activitydemo.validate.ActivityAwardRuleUpdateValidate;
import com.example.activitydemo.vo.ActivityAwardRuleDetailVo;
import com.example.activitydemo.vo.ActivityAwardRuleListedVo;

/**
 * 奖励规则服务接口类
 *
 * @author fei
 */
public interface IActivityAwardRuleService extends IService<ActivityAwardRule> {

    /**
     * 奖励规则列表
     *
     * @param pageValidate   分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<ActivityAwardRuleListedVo>
     * @author fei
     */
    PageResult<ActivityAwardRuleListedVo> list(PageValidate pageValidate, ActivityAwardRuleSearchValidate searchValidate);

    /**
     * 奖励规则详情
     *
     * @param id 主键ID
     * @return ActivityAwardRuleDetailVo
     * @author fei
     */
    ActivityAwardRuleDetailVo detail(Long id);

    /**
     * 奖励规则新增
     *
     * @param createValidate 参数
     * @author fei
     */
    void add(ActivityAwardRuleCreateValidate createValidate);

    /**
     * 奖励规则编辑
     *
     * @param updateValidate 参数
     * @author fei
     */
    void edit(ActivityAwardRuleUpdateValidate updateValidate);

    /**
     * 奖励规则删除
     *
     * @param id 主键ID
     * @author fei
     */
    void del(Long id);

}

