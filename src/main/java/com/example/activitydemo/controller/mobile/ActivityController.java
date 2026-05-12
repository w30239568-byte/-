package com.example.activitydemo.controller.mobile;

import com.example.activitydemo.basecommon.AjaxResult;
import com.example.activitydemo.domain.ActivityTaskItem;
import com.example.activitydemo.service.IActivityService;
import com.example.activitydemo.service.IActivityUserJoinService;
import com.example.activitydemo.service.IActivityUserTaskRecordService;
import com.example.activitydemo.vo.ActivityDetailVo;
import com.example.activitydemo.vo.ActivitySignInVo;
import com.example.activitydemo.vo.SignMsgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("api/activity")
public class ActivityController {


    @Autowired
    private IActivityUserJoinService iActivityUserJoinService;
    @Autowired
    private IActivityService iActivityService;
    @Autowired
    private IActivityUserTaskRecordService iActivityUserTaskRecordService;


    /**
     * 获取活动信息
     *
     * @param activityId
     * @return
     */
    @GetMapping("/getActivityDetail")
    public AjaxResult<ActivityDetailVo> getActivityDetail(@RequestParam("activityId") Long activityId) {
        long userId = StpUtil.getLoginIdAsLong();
        return AjaxResult.success(iActivityService.getActivityDetail(activityId, userId));
    }

    /**
     * 用户报名参加活动
     *
     * @param activityId
     * @return
     */
    @GetMapping("/activitySignUp")
    public AjaxResult<Object> activitySignUp(@RequestParam("activityId") Long activityId,
                                             @RequestParam(required = false, value = "targetId") Long targetId) {
        long userId = StpUtil.getLoginIdAsLong();
        String values = iActivityUserJoinService.activitySignUp(activityId, userId, targetId);
        return values.equals("success") ? AjaxResult.success() : AjaxResult.failed(values);
    }


    /**
     * 获取该活动下一个任务
     *
     * @param activityId
     * @return
     */
    @GetMapping("/getTaskList")
    public AjaxResult<List<ActivityTaskItem>> getTaskList(@RequestParam("activityId") Long activityId) {
        long userId = StpUtil.getLoginIdAsLong();
        return AjaxResult.success(iActivityService.getTaskList(activityId, userId));
    }

    /**
     * 用户打卡
     *
     * @return
     */
    @PostMapping("/signIn")
    public AjaxResult<Boolean> signIn(@RequestBody ActivitySignInVo activitySignInVo) {
        long userId = StpUtil.getLoginIdAsLong();
        return AjaxResult.success(iActivityUserTaskRecordService.signIn(activitySignInVo.getActivityId(),
                activitySignInVo.getTaskId(), userId, activitySignInVo.getMoodStatus(),
                activitySignInVo.getResource(), activitySignInVo.getContent(), activitySignInVo.getAiComment(), activitySignInVo.getIsOpen()));
    }



    /**
     * 获取签到信息（连续打卡天数，累计打卡天数）
     *
     * @param activityId
     * @return
     */
    @GetMapping("/getSignMsg")
    public AjaxResult<SignMsgVo> getSignMsg(@RequestParam("activityId") Long activityId) {
        long userId = StpUtil.getLoginIdAsLong();
        return AjaxResult.success(iActivityUserTaskRecordService.getSignMsg(activityId, userId));
    }



}
