package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityAwardService;
import com.example.activitydemo.validate.ActivityAwardCreateValidate;
import com.example.activitydemo.validate.ActivityAwardSearchValidate;
import com.example.activitydemo.validate.ActivityAwardUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityAwardDetailVo;
import com.example.activitydemo.vo.ActivityAwardListedVo;
import com.example.activitydemo.vo.ActivityUserAwardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("api/activityAward")
public class ActivityAwardController {

    @Resource
    private IActivityAwardService iActivityAwardService;

    @PostMapping("/list")
    public AjaxResult<PageResult<ActivityAwardListedVo>> list(@Validated PageValidate pageValidate,
                                                              @Validated @RequestBody ActivityAwardSearchValidate searchValidate) {
        pageValidate.setPageSize(searchValidate.getPageSize());
        pageValidate.setPageNo(searchValidate.getPageNo());
        searchValidate.setEquityName(searchValidate.getAward());
        PageResult<ActivityAwardListedVo> list = iActivityAwardService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityAwardDetailVo> detail(@RequestParam("id") Long id) {
        ActivityAwardDetailVo detail = iActivityAwardService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityAwardCreateValidate createValidate) {
        iActivityAwardService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityAwardUpdateValidate updateValidate) {
        iActivityAwardService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityAwardService.del(idValidate.getId());
        return AjaxResult.success();
    }

    @GetMapping("/userGetList")
    public AjaxResult<PageResult<ActivityUserAwardVo>> userGetList(@Validated PageValidate pageValidate,
                                                                   @Validated @RequestBody ActivityAwardSearchValidate searchValidate) {
        PageResult<ActivityUserAwardVo> list = iActivityAwardService.userGetList(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

}
