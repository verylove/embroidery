package cn.wind.xboot.message.sms.enums;

/**
 * <p>Title: SmsTypeEnum</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
public enum  SmsTypeEnum {
    SIGN_UP,
    SIGN_IN,
    FORGET,
    MODIFY_PASS,
    BANK_CARD,
    PAY_PASS,
    NULL;

    public static SmsTypeEnum valueOfOrNull(String name) {
        try {
            return SmsTypeEnum.valueOf(name);
        } catch (IllegalArgumentException e) {
           return SmsTypeEnum.NULL;
        }
    }
}
