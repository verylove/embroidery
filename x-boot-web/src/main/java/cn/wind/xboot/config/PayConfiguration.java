package cn.wind.xboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/27 10:35
 * @Description:
 */
@Component
//@ConfigurationProperties(locations = {"classpath:config/myProps.yml"},prefix = "myProps")
@ConfigurationProperties(prefix = "pay")
public class PayConfiguration {
    private Wx wx;
    private Ali ali;
    @Data
    public  static class Wx {
        private String appId;
        private String mchid;
        private String signKey;


    }
    @Data
    public  static class Ali{
        private String appId;
        private String privateKey;
        private String alipayPublicKey;

    }

    public Wx getWx() {
        return wx;
    }

    public void setWx(Wx wx) {
        this.wx = wx;
    }

    public Ali getAli() {
        return ali;
    }

    public void setAli(Ali ali) {
        this.ali = ali;
    }
}
