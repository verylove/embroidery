package cn.wind.xboot.tencent.pojo.Request;

public class GetRoomListReq {
    private int category = 0;//1-大咖秀 0-娱乐
    private int cnt = 0;
    private int index = 0;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
