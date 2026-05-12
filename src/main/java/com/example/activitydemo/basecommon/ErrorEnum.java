package com.example.activitydemo.basecommon;

public enum ErrorEnum {

    SUCCESS(200, "成功"),
    FAILED(300, "失败"),

    PARAMS_VALID_ERROR(310, "参数校验错误"),
    PARAMS_TYPE_ERROR(311, "参数类型错误"),
    REQUEST_METHOD_ERROR(312, "请求方法错误"),
    ASSERT_ARGUMENT_ERROR(313, "断言参数错误"),
    ASSERT_MYBATIS_ERROR(314, "断言Mybatis错误"),

    LOGIN_ACCOUNT_ERROR(330, "登录账号或密码错误"),
    LOGIN_DISABLE_ERROR(331, "登录账号已被禁用了"),
    TOKEN_EMPTY(332, "token参数为空"),
    TOKEN_INVALID(333, "token参数无效"),
    CAPTCHA_ERROR(334, "验证码错误"),
    PAYMENT_ERROR(335, "发起支付失败"),
    REPEAT_COMMIT(336, "重复提交"),
    NEED_INVITE_CODE(337, "请填写邀请码"),
    INVITE_CODE_ERROR(338, "邀请码错误，请重新输入"),
    INVITE_CODE_USED_ERROR(339, "邀请码已使用，请重新输入"),
    TOKEN_ERROR(340, "token校验失败"),
    PERMISSION_GET_ERROR(341, "权限获取失败"),

    REPEAT_FORUM(342, "重复点击"),
    REPEAT_CLICK(343, "间隔时间才能点击"),
    CLICK_FAST(344, "点击速度太快"),
    NOT_VIP(345, "你还不是 vip 用户"),
    SMS_FREQUENTLY(346, "验证码发送过于频繁"),
    SMS_SEND_NUM_LIMIT(347, "验证码发送次数达到上限"),
    LIMIT_FORUM(348, "提交次数过多"),
    BUSINESS_ERROR(349, "业务异常"),

    NO_PERMISSION(403, "无相关权限"),
    REQUEST_404_ERROR(404, "请求接口不存在"),
    DATA_NOT_EXIST(405, "数据不存在"),

    INVITE_ERROR(406, "邀请异常"),

    SYSTEM_ERROR(500, "系统错误"),
    FEIGN_ERROR(501, "feign请求错误"),
    PARAMS_HEAD_ERROR(502, "参数头校验错误"),

    CACHE_VALID_ERROR(503, "缓存校验错误"),
    LOCK_ERROR(504, "获取锁失败"),
    /*** 下面错误会直接显示出来,为操作异常 ***/
    OPERATOR_ERROR(60001, "操作异常"),
    QUESTION_FAILED(60002, "失败"),
    DATA_ERROR(60003, "数据异常"),
    DATA_EXIST(60004, "数据已存在"),


    VOICE_ERROR(60004, "该角色没有配置音色"),
    NO_PAY(60005, "您还没购买改产品"),
    VOICE_TRAIN_ERROR(60006, "声音训练失败"),

    ;
    /**
     * 构造方法
     */
    private final int code;
    private final String msg;

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取状态码
     *
     * @return Long
     * @author fzr
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 获取提示
     *
     * @return String
     * @author fzr
     */
    public String getMsg() {
        return this.msg;
    }

}
