package cn.wind.common.enums;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public enum SmsKeyCodeEnum {
    signUp("注册",0),signIn("登录",1),common("普通",2);
    private String title;
    private Integer index;

    SmsKeyCodeEnum(String title, Integer index) {
        this.title = title;
        this.index=index;
    }

    public Integer getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }
}
