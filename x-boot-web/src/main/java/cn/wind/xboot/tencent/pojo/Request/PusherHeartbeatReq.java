package cn.wind.xboot.tencent.pojo.Request;

public class PusherHeartbeatReq {
    private String roomID = "";
    private String userID = "";

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
