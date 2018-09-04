package cn.wind.xboot.tencent.pojo.Response;

import cn.wind.db.bc.entity.BcGift;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/29 10:27
 * @Description:
 */
public class GetGiftListRsp extends BaseRsp{

    @JsonProperty(value = "gifts")
    private ArrayList<BcGift> gifts;

    @JsonIgnore
    public ArrayList<BcGift> getGifts() {
        return gifts;
    }

    public void setGifts(ArrayList<BcGift> gifts) {
        this.gifts = gifts;
    }
}
