package cn.wind.xboot.enums;

import com.baomidou.mybatisplus.enums.IEnum;

/**
 * <p>Title: ButtonType</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/22
 */
public enum ButtonType implements IEnum {
    ADD("add","添加"),
    EDIT("edit","编辑"),
    DELETE("delete","删除"),
    ENABLE("enable","启动"),
    DISABLE("disable","禁用"),
    CLEARAll("clearAll","清空"),
    EDITPERM("editPerm","编辑权限"),
    ;
    private String value;
    private String desc;

    ButtonType(String value, String desc) {
        this.value= value;
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
