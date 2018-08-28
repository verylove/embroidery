package cn.wind.xboot.tencent.pojo.Response;

import cn.wind.db.ar.entity.ArUser;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/22 17:48
 * @Description:
 */
public class GetUserListRsp extends BaseRsp {
    @JsonProperty(value = "users")
    private ArrayList<ArUser> users;

    @JsonIgnore
    public ArrayList<ArUser> getList() {
        return users;
    }

    public void setList(ArrayList<ArUser> list) {
        this.users = list;
    }
}
