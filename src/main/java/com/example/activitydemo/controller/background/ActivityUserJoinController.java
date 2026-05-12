package com.example.activitydemo.controller.background;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityUserJoinService;
import com.example.activitydemo.validate.ActivityUserJoinCreateValidate;
import com.example.activitydemo.validate.ActivityUserJoinSearchValidate;
import com.example.activitydemo.validate.ActivityUserJoinUpdateValidate;
import com.example.activitydemo.validate.IdLongValidate;
import com.example.activitydemo.vo.ActivityUserJoinListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activityUserJoin")
public class ActivityUserJoinController {

    @Resource
    private IActivityUserJoinService iActivityUserJoinService;

    @PostMapping("/list")
    public AjaxResult<PageResult<ActivityUserJoinListedVo>> list(@Validated @RequestBody ActivityUserJoinSearchValidate searchValidate) {

        PageValidate pageValidate = new PageValidate();
        pageValidate.setPageSize(searchValidate.getPageSize());
        pageValidate.setPageNo(searchValidate.getPageNo());
        PageResult<ActivityUserJoinListedVo> list = iActivityUserJoinService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityUserJoinListedVo> detail(@RequestParam("id") @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityUserJoinListedVo detail = iActivityUserJoinService.detail(id);
        return AjaxResult.success(detail);
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityUserJoinCreateValidate createValidate) {
        Boolean boole = iActivityUserJoinService.add(createValidate);
        if (boole) {
            return AjaxResult.success();
        } else {
            return AjaxResult.failed("报名人数已满");
        }

    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityUserJoinUpdateValidate updateValidate) {
        iActivityUserJoinService.edit(updateValidate);
        return AjaxResult.success();
    }

    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdLongValidate idValidate) {
        iActivityUserJoinService.del(idValidate.getId());
        return AjaxResult.success();
    }

}
