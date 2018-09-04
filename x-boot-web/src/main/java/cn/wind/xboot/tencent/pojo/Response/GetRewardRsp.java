package cn.wind.xboot.tencent.pojo.Response;

import cn.wind.db.ar.entity.ArUser;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/29 15:54
 * @Description:
 */
public class GetRewardRsp extends BaseRsp{
    private ArUser user;

    private Long roomScore;//pk用

    private Long otherRoomScore;//pk用

    public ArUser getUser() {
        return user;
    }

    public void setUser(ArUser user) {
        this.user = user;
    }

    public Long getRoomScore() {
        return roomScore;
    }

    public void setRoomScore(Long roomScore) {
        this.roomScore = roomScore;
    }

    public Long getOtherRoomScore() {
        return otherRoomScore;
    }

    public void setOtherRoomScore(Long otherRoomScore) {
        this.otherRoomScore = otherRoomScore;
    }
}
