package cn.wind.common.token;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/9 09:08
 * @Description:
 */
public class AesToken {
    private String id;
    private Long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
