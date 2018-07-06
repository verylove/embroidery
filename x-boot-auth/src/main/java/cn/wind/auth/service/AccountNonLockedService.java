package cn.wind.auth.service;

/**
 * @author xukk
 * @date 2018/3/14
 */
public interface AccountNonLockedService {
    /**
     * x
     * @param scope
     * @param username
     */
    void resetAccountNonLocked(String scope, String username);
}
