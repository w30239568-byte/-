package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityUserAwardService;
import com.example.activitydemo.validate.ActivityUserAwardCreateValidate;
import com.example.activitydemo.validate.ActivityUserAwardSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityUserAwardDetailVo;
import com.example.activitydemo.vo.ActivityUserAwardListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activityUserAward")
public class ActivityUserAwardController {

    @Resource
    private IActivityUserAwardService iActivityUserAwardService;

    @GetMapping("/list")
    public AjaxResult<PageResult<ActivityUserAwardListedVo>> list(@Validated PageValidate pageValidate,
                                                                  @Validated ActivityUserAwardSearchValidate searchValidate) {
        PageResult<ActivityUserAwardListedVo> list = iActivityUserAwardService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityUserAwardDetailVo> detail(@RequestParam("id") @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityUserAwardDetailVo detail = iActivityUserAwardService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityUserAwardCreateValidate createValidate) {
        iActivityUserAwardService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityUserAwardUpdateValidate updateValidate) {
        iActivityUserAwardService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityUserAwardService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
