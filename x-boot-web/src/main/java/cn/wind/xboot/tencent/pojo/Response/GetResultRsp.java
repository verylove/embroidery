package cn.wind.xboot.tencent.pojo.Response;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/29 16:59
 * @Description:
 */
public class GetResultRsp extends BaseRsp{
    private String winner;
    private String loser;
    private Long winnerWorth;
    private Long loserWorth;
    private int isEqual=0;//1-平局 0-不是平局

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public Long getWinnerWorth() {
        return winnerWorth;
    }

    public void setWinnerWorth(Long winnerWorth) {
        this.winnerWorth = winnerWorth;
    }

    public Long getLoserWorth() {
        return loserWorth;
    }

    public void setLoserWorth(Long loserWorth) {
        this.loserWorth = loserWorth;
    }

    public int getIsEqual() {
        return isEqual;
    }

    public void setIsEqual(int isEqual) {
        this.isEqual = isEqual;
    }
}
