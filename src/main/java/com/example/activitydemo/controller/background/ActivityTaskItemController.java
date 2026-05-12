package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityTaskItemService;
import com.example.activitydemo.validate.ActivityTaskItemCreateValidate;
import com.example.activitydemo.validate.ActivityTaskItemSearchValidate;
import com.example.activitydemo.validate.ActivityTaskItemUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityTaskItemDetailVo;
import com.example.activitydemo.vo.ActivityTaskItemListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activityTaskItem")
public class ActivityTaskItemController {

    @Resource
    private IActivityTaskItemService iActivityTaskItemService;

    @RequestMapping("/list")
    public AjaxResult<PageResult<ActivityTaskItemListedVo>> list(@Validated PageValidate pageValidate, @Validated @RequestBody ActivityTaskItemSearchValidate searchValidate) {
        PageResult<ActivityTaskItemListedVo> list = iActivityTaskItemService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityTaskItemDetailVo> detail(@RequestParam("id") @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityTaskItemDetailVo detail = iActivityTaskItemService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityTaskItemCreateValidate createValidate) {
        iActivityTaskItemService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityTaskItemUpdateValidate updateValidate) {
        iActivityTaskItemService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityTaskItemService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
