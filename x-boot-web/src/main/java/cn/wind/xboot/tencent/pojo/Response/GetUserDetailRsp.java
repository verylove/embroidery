package cn.wind.xboot.tencent.pojo.Response;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/28 14:16
 * @Description:
 */
public class GetUserDetailRsp extends BaseRsp{
    private Long id;
    private String account;
    private String icon;
    private Integer perLevel;
    private Long liveEarnings;
    private Integer isCollection;//1-关注 0-不关注
    private Long cityId;
    private Long cityRankNum;//城市排名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPerLevel() {
        return perLevel;
    }

    public void setPerLevel(Integer perLevel) {
        this.perLevel = perLevel;
    }

    public Long getLiveEarnings() {
        return liveEarnings;
    }

    public void setLiveEarnings(Long liveEarnings) {
        this.liveEarnings = liveEarnings;
    }

    public Integer getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection) {
        this.isCollection = isCollection;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCityRankNum() {
        return cityRankNum;
    }

    public void setCityRankNum(Long cityRankNum) {
        this.cityRankNum = cityRankNum;
    }
}
