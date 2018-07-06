package cn.wind.xboot.message.push;

import cn.wind.xboot.message.push.impl.UPusher;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xukk
 * @date 2018/5/23
 */
@Configuration
public class PushConfig {
    @Bean
    @ConditionalOnProperty(prefix="token",name="type" ,havingValue="u-push" ,matchIfMissing=true)
    public UPusher uPusher(PushConfigDto pushConfigDto){
        return new UPusher(pushConfigDto);
    }
    @Bean
    @ConfigurationProperties(prefix = "push")
    public PushConfigDto pushConfigDto(){
        return new PushConfigDto();
    }
    @Data
    public static  class PushConfigDto {
        private String appKey="5abdf5baf43e48434c000023";
        private String appMasterSecret="mtuyvoksrtv5kd6263wbopduyo24dc1r";
        private Long timestamp;

        public Long getTimestamp() {
            return (System.currentTimeMillis() / 1000L);
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
