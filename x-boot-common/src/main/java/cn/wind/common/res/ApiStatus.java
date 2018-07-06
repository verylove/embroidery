package cn.wind.common.res;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public enum ApiStatus implements ApiInfo {
    /**
     * 成功
     */
    SUCCESS(900000, "SUCCESS"),
    /**
     * 一般错误
     */
    FAIL(999999, "GENERAL_FAIL"),

    /**
     * 100 系列
     */
    UNKOWN_DATA_STORE_ERROR(900100, "未知的存储操作错误"),
    INVALID_OPERATION(900101, "无效的操作方法"),
    SPECIFIED_OBJECT_CANNOT_BE_FOUND(900102, "指定的对象不存在"),
    SPECIFIED_OBJECT_ALREADY_EXISTS(900103, "指定的对象已存在"),
    PARAMETER_INVALID(900104, "请求参数无效"),
    PARAMETER_ERROR(900105, "请求参数错误"),
    PARAMETER_EMPTY(900105, "请求参数为空"),
    CHECK_FAIL(900106, "校验失败"),
    TIMESTAMP_ABNORMAL(900107, "时间异常"),
    /**
     * 200系列
     */
    MICRO_SERVICE_UNAVAILABLE(900200, "服务暂不可用"),
    PRIVATEKEY_LESS(900201, "缺少秘钥"),
    AUTHORATION_LESS(900202, "缺少授权信息"),
    SMSCODE_LESS(900203, "缺少短信验证码"),
    TOKEN_LESS(900204, "缺少令牌"),
    SMSCODE_INVALID(900205, "短信验证码失效"),
    SMSCODE_UN_IDENTICAL(900206, "短信验证不一致"),
    TOKEN_UN_IDENTICAL(900206, "令牌不一致"),
    TOKEN_FAIL_REFRESH(900207, "刷新授权信息失败"),
    SMSCODE_SEND_FREQUENT(900208, "短信发送频繁"),
    SMSCODE_OVER_DAY_LIMIT(900209, "一天内同一个手机号短信发送超过最大额度"),
    SMSCODE_OVER_IP_LIMIT(900210, "一天内用一个IP短信发送超过最大额度"),
    SESSION_INVALID(900211, "session失效"),

    /**
     * 300系列
     */
    ENCRYPT_ERROR(900300, "编码失败"),
    DECRYPT_ERROR(900301, "解码失败"),

    /**
     * 400系列
     */
    PLATFORM_TYPE_ERROR(900400, "平台类型有误"),
    USER_IDENTIFIACTION_ERROR(900401, "用户标识有误");

    private int code;

    private String desc;

    private ApiStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String desc() {
        return this.desc;
    }
}