package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityAwardRuleService;
import com.example.activitydemo.validate.ActivityAwardRuleCreateValidate;
import com.example.activitydemo.validate.ActivityAwardRuleSearchValidate;
import com.example.activitydemo.validate.ActivityAwardRuleUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityAwardRuleDetailVo;
import com.example.activitydemo.vo.ActivityAwardRuleListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activityAwardRule")
public class ActivityAwardRuleController {

    @Resource
    private IActivityAwardRuleService iActivityAwardRuleService;

    @GetMapping("/list")
    public AjaxResult<PageResult<ActivityAwardRuleListedVo>> list(@Validated PageValidate pageValidate,
                                                                  @Validated ActivityAwardRuleSearchValidate searchValidate) {
        PageResult<ActivityAwardRuleListedVo> list = iActivityAwardRuleService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityAwardRuleDetailVo> detail(@RequestParam("id") @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityAwardRuleDetailVo detail = iActivityAwardRuleService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityAwardRuleCreateValidate createValidate) {
        iActivityAwardRuleService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityAwardRuleUpdateValidate updateValidate) {
        iActivityAwardRuleService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityAwardRuleService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
