package cn.wind.xboot.enums;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * <p>Title: PermType</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/22
 */
public enum PermType implements IEnum {
    MENU("MENU","菜单"),
    BUTTON("BUTTON","按钮"),
    URL("URL","跳转链接");
    private String value;
    private String desc;

    PermType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    @Override
    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
