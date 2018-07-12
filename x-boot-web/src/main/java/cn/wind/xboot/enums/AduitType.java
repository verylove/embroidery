package cn.wind.xboot.enums;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/11 10:36
 * @Description:认证状态
 */
public enum AduitType {
    NAMEADUIT("实名认证"),
    SIGNADUIT("签约保障"),
    STOREADUIT("店铺认证");

    private final String aType;

    private AduitType(String aType){
        this.aType=aType;
    }
    public String getaType() {
        return aType;
    }
}
