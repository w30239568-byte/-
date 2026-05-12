package com.example.activitydemo.controller.mobile;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.service.IActivityService;
import com.example.activitydemo.service.IActivityUserJoinService;
import com.example.activitydemo.service.IActivityUserTaskRecordService;
import com.example.activitydemo.vo.ActivityDetailVo;
import com.example.activitydemo.vo.ActivitySignInVo;
import com.example.activitydemo.vo.SignMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/activity")
public class ActivityController {

    @Autowired
    private IActivityUserJoinService iActivityUserJoinService;
    @Autowired
    private IActivityService iActivityService;
    @Autowired
    private IActivityUserTaskRecordService iActivityUserTaskRecordService;

    @GetMapping("/getActivityDetail")
    public AjaxResult<ActivityDetailVo> getActivityDetail(@RequestParam("activityId") @Min(value = 1, message = "activityId必须大于0") Long activityId,
                                                          @RequestParam("userId") @Min(value = 1, message = "userId必须大于0") Long userId) {
        return AjaxResult.success(iActivityService.getActivityDetail(activityId, userId));
    }

    @GetMapping("/activitySignUp")
    public AjaxResult<Object> activitySignUp(@RequestParam("activityId") @Min(value = 1, message = "activityId必须大于0") Long activityId,
                                             @RequestParam("userId") @Min(value = 1, message = "userId必须大于0") Long userId,
                                             @RequestParam(required = false, value = "targetId") @Min(value = 1, message = "targetId必须大于0") Long targetId) {
        String values = iActivityUserJoinService.activitySignUp(activityId, userId, targetId);
        return "success".equals(values) ? AjaxResult.success() : AjaxResult.failed(values);
    }

    @GetMapping("/getTaskList")
    public AjaxResult<List<ActivityTaskItem>> getTaskList(@RequestParam("activityId") @Min(value = 1, message = "activityId必须大于0") Long activityId,
                                                           @RequestParam("userId") @Min(value = 1, message = "userId必须大于0") Long userId) {
        return AjaxResult.success(iActivityService.getTaskList(activityId, userId));
    }

    @PostMapping("/signIn")
    public AjaxResult<Boolean> signIn(@Valid @RequestBody ActivitySignInVo activitySignInVo,
                                      @RequestHeader(value = "Idempotency-Key", required = false) String requestKey) {
        return AjaxResult.success(iActivityUserTaskRecordService.signIn(activitySignInVo.getActivityId(),
                activitySignInVo.getTaskId(), activitySignInVo.getUserId(), activitySignInVo.getMoodStatus(),
                activitySignInVo.getResource(), activitySignInVo.getContent(), activitySignInVo.getAiComment(),
                activitySignInVo.getIsOpen(), requestKey));
    }

    @GetMapping("/getSignMsg")
    public AjaxResult<SignMsgVo> getSignMsg(@RequestParam("activityId") @Min(value = 1, message = "activityId必须大于0") Long activityId,
                                            @RequestParam("userId") @Min(value = 1, message = "userId必须大于0") Long userId) {
        return AjaxResult.success(iActivityUserTaskRecordService.getSignMsg(activityId, userId));
    }
}
