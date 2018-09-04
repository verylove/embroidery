package cn.wind.xboot.tencent.pojo.Request;

import net.sf.json.JSONArray;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/30 10:36
 * @Description:
 */
public class SendMsgReq {
    @JsonProperty(value = "SyncOtherMachine")
    private Integer SyncOtherMachine = 1;
    @JsonProperty(value = "From_Account")
    private String From_Account;
    @JsonProperty(value = "To_Account")
    private String To_Account;
    @JsonProperty(value = "MsgLifeTime")
    private Integer MsgLifeTime=604800;
    @JsonProperty(value = "MsgRandom")
    private Integer MsgRandom;
    @JsonProperty(value = "MsgBody")
    private JSONArray MsgBody;

    @JsonIgnore
    public Integer getSyncOtherMachine() {
        return SyncOtherMachine;
    }

    public void setSyncOtherMachine(Integer syncOtherMachine) {
        SyncOtherMachine = syncOtherMachine;
    }

    @JsonIgnore
    public String getFrom_Account() {
        return From_Account;
    }

    public void setFrom_Account(String from_Account) {
        From_Account = from_Account;
    }

    @JsonIgnore
    public String getTo_Account() {
        return To_Account;
    }

    public void setTo_Account(String to_Account) {
        To_Account = to_Account;
    }

    @JsonIgnore
    public Integer getMsgLifeTime() {
        return MsgLifeTime;
    }

    public void setMsgLifeTime(Integer msgLifeTime) {
        MsgLifeTime = msgLifeTime;
    }

    @JsonIgnore
    public Integer getMsgRandom() {
        return MsgRandom;
    }

    public void setMsgRandom(Integer msgRandom) {
        MsgRandom = msgRandom;
    }

    @JsonIgnore
    public JSONArray getMsgBody() {
        return MsgBody;
    }

    public void setMsgBody(JSONArray msgBody) {
        MsgBody = msgBody;
    }
}
