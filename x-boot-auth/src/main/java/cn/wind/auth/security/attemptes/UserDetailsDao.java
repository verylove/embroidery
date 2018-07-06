package cn.wind.auth.security.attemptes;


import cn.wind.common.domain.Attempts;

/**
 * @author xukk
 * @date 2018/3/14
 */
public interface UserDetailsDao {
    /**
     *  修改失败次数
     * @param username
     */
    void updateFailAttempts(String username);

    /**
     * 充值失败次数
     * @param username
     */
    void resetFailAttempts(String username);

    /**
     * 获取次数
     * @param username
     * @return
     */
    Attempts getAttempts(String username);
}
