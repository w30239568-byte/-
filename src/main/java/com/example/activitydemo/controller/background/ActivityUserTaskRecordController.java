package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityUserTaskRecordService;
import com.example.activitydemo.validate.ActivityUserTaskRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserTaskRecordUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityUserTaskRecordListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("api/activityUserTaskRecord")
public class ActivityUserTaskRecordController {

    @Resource
    private IActivityUserTaskRecordService iActivityUserTaskRecordService;

    @GetMapping("/list")
    public AjaxResult<PageResult<ActivityUserTaskRecordListedVo>> list(@Validated PageValidate pageValidate,
                                                                       @Validated ActivityUserTaskRecordSearchValidate searchValidate) {
        PageResult<ActivityUserTaskRecordListedVo> list = iActivityUserTaskRecordService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityUserTaskRecordListedVo> detail(@Validated @RequestParam("id") Long id) {
        ActivityUserTaskRecordListedVo detail = iActivityUserTaskRecordService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityUserTaskRecordCreateValidate createValidate) {
        iActivityUserTaskRecordService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityUserTaskRecordUpdateValidate updateValidate) {
        iActivityUserTaskRecordService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityUserTaskRecordService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
