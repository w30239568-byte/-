package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityTaskRelationService;
import com.example.activitydemo.validate.ActivityTaskRelationCreateValidate;
import com.example.activitydemo.validate.ActivityTaskRelationSearchValidate;
import com.example.activitydemo.validate.ActivityTaskRelationUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityTaskRelationDetailVo;
import com.example.activitydemo.vo.ActivityTaskRelationListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activityTaskRelation")
public class ActivityTaskRelationController {

    @Resource
    private IActivityTaskRelationService iActivityTaskRelationService;

    @GetMapping("/list")
    public AjaxResult<PageResult<ActivityTaskRelationListedVo>> list(@Validated PageValidate pageValidate,
                                                                     @Validated ActivityTaskRelationSearchValidate searchValidate) {
        PageResult<ActivityTaskRelationListedVo> list = iActivityTaskRelationService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityTaskRelationDetailVo> detail(@RequestParam("id") @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityTaskRelationDetailVo detail = iActivityTaskRelationService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityTaskRelationCreateValidate createValidate) {
        iActivityTaskRelationService.add(createValidate);
        return AjaxResult.success();
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityTaskRelationUpdateValidate updateValidate) {
        iActivityTaskRelationService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityTaskRelationService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
