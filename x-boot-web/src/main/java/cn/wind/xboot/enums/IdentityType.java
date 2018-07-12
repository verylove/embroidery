package cn.wind.xboot.enums;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/11 10:44
 * @Description:
 */
public enum IdentityType {
    LOVERS("爱好者"),
    TATTOO("纹身师");

    private final String type;

    private IdentityType(String type){
        this.type=type;
    }
    public String getType() {
        return type;
    }
}
