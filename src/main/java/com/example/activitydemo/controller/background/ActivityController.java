package com.example.activitydemo.controller.background;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.basecommon.PageResult;
import com.example.activitydemo.basecommon.PageValidate;
import com.example.activitydemo.service.IActivityService;
import com.example.activitydemo.validate.ActivityCreateValidate;
import com.example.activitydemo.validate.ActivitySearchValidate;
import com.example.activitydemo.validate.ActivityUpdateValidate;
import com.example.activitydemo.vo.ActivityDetailVo;
import com.example.activitydemo.vo.ActivityListedVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Slf4j
@Validated
@RestController
@RequestMapping("api/activity")
public class ActivityController {


    @Autowired
    private IActivityService activityService;


    @RequestMapping("/list")
    public AjaxResult<PageResult<ActivityListedVo>> list(@RequestParam(required = false, defaultValue = "1") @Min(value = 1, message = "pageNo最小为1") Integer pageNo,
                                                         @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "pageSize最小为1") @Max(value = 100, message = "pageSize最大为100") Integer pageSize,
                                                         @RequestParam(required = false) Integer status,
                                                         @RequestParam(required = false) String name,
                                                         @RequestParam(required = false) Integer createType) {
        PageValidate pageValidate = new PageValidate();
        pageValidate.setPageNo(pageNo);
        pageValidate.setPageSize(pageSize);

        ActivitySearchValidate searchValidate = new ActivitySearchValidate();
        searchValidate.setStatus(status);
        searchValidate.setName(name);
        searchValidate.setCreateType(createType);
        PageResult<ActivityListedVo> list = activityService.list(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    @GetMapping("/detail")
    public AjaxResult<ActivityDetailVo> detail(@RequestParam @Min(value = 1, message = "id必须大于0") Long id) {
        ActivityDetailVo detail = activityService.detail(id);
        return AjaxResult.success(detail);
    }

    @GetMapping("/del")
    public AjaxResult<Object> del(@RequestParam @Min(value = 1, message = "id必须大于0") Long id) {
        Boolean del = activityService.del(id);
        return !del ? AjaxResult.failed("当前活动已被参与，禁止删除！") : AjaxResult.success();
    }

    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ActivityCreateValidate activityAddDetailVo) {
        String val = activityAddDetailVo.getVal();
        if (val == null) {
            return AjaxResult.failed("请输入活动天数");
        }
        JSONObject jsonObject = JSON.parseObject(val, JSONObject.class);
        try {
            Integer num = jsonObject.getInteger("num");
            if (num == null) {
                return AjaxResult.failed("活动天数输入错误或任务数量与天数不匹配！");
            }
            if (num != activityAddDetailVo.getTaskList().size()) {
                return AjaxResult.failed("活动天数输入错误或任务数量与天数不匹配！");
            }
        } catch (Exception e) {
            return AjaxResult.failed("活动天数输入错误或任务数量与天数不匹配！");
        }

        activityService.add(activityAddDetailVo, null);
        return AjaxResult.success(true);
    }

    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ActivityUpdateValidate activityDetailVo) {
        String val = activityDetailVo.getVal();
        if (val == null) {
            return AjaxResult.failed("请输入活动天数");
        }
        JSONObject jsonObject = JSON.parseObject(val, JSONObject.class);
        try {
            Integer num = jsonObject.getInteger("num");
            if (num == null) {
                return AjaxResult.failed("活动天数输入错误或任务数量与天数不匹配！");
            }
            if (num != activityDetailVo.getTaskList().size()) {
                return AjaxResult.failed("活动天数输入错误或任务数量与天数不匹配！");
            }
        } catch (Exception e) {
            return AjaxResult.failed("活动天数输入错误或任务数量与天数不匹配！");
        }
        Boolean edit = activityService.edit(activityDetailVo, null);
        return !edit ? AjaxResult.failed("当前活动已被参与，禁止修改！") : AjaxResult.success();
    }
}
