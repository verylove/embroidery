package cn.wind.xboot.tencent.pojo;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/30 14:24
 * @Description:
 */
public class MsgInfo {
    private Integer type;//1-文字 2-表情

    private Integer Index;//表情索引

    private String content;//1-文字内容 2-表情data

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIndex() {
        return Index;
    }

    public void setIndex(Integer index) {
        Index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
