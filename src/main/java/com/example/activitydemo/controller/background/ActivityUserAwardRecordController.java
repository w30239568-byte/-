package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityUserAwardRecordService;
import com.example.activitydemo.validate.ActivityUserAwardRecordCreateValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordSearchValidate;
import com.example.activitydemo.validate.ActivityUserAwardRecordUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityUserAwardRecordDetailVo;
import com.example.activitydemo.vo.ActivityUserAwardRecordListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activityUserAwardRecord")
public class ActivityUserAwardRecordController {

    @Resource
    private IActivityUserAwardRecordService iActivityUserAwardRecordService;

    @GetMapping("/list")
    public AjaxResult<PageResult<ActivityUserAwardRecordListedVo>> list(@Validated PageValidate pageValidate,
                                                                        @Validated ActivityUserAwardRecordSearchValidate searchValidate) {
        PageResult<ActivityUserAwardRecordListedVo> list = iActivityUserAwardRecordService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityUserAwardRecordDetailVo> detail(@RequestParam("id") @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityUserAwardRecordDetailVo detail = iActivityUserAwardRecordService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityUserAwardRecordCreateValidate createValidate) {
        iActivityUserAwardRecordService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityUserAwardRecordUpdateValidate updateValidate) {
        iActivityUserAwardRecordService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityUserAwardRecordService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
