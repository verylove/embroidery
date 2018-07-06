package cn.wind.common.enums;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
public enum ComConfigEnum {
    NUM_OF_DAY_SMS("一天内同一个手机号最大短信数","20"),
    NUM_OF_IP_SMS("一天内同一个IP最大短信数","20");
    private String title;
    private String defaultVal;
    ComConfigEnum(String title, String defaultVal) {
        this.title = title;
        this.defaultVal=defaultVal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public String getTitle() {
        return title;
    }
}
