package cn.wind.auth.security.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.wind.auth.security.attemptes.UserDetailsDao;
import cn.wind.common.domain.Attempts;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 自定义 数据库查询
 * @author xukk
 * @date 2018/3/14
 */
public class CustomDaoAuthenticationProvider  extends DaoAuthenticationProvider {
    @Resource
    private UserDetailsDao userDetailsDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = "";
        String scope = "";
        try {
            Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports", "Only UsernamePasswordAuthenticationToken is supported"));
            username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
            boolean cacheWasUsed = true;
            UserDetails user = this.getUserCache().getUserFromCache(username);
            if (authentication.getDetails() instanceof Map) {
                Map map = (Map) authentication.getDetails();
                Object scopeObj = map.get("scope");
                if (scopeObj == null) {
                    scope = "";
                } else {
                    scope = String.valueOf(scopeObj);
                }
            }
            username = scope + StrUtil.COLON + username;
            if (user == null) {
                cacheWasUsed = false;

                try {


                    user = this.retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
                } catch (UsernameNotFoundException var6) {
                    this.logger.debug("User '" + username + "' not found");
                    if (this.hideUserNotFoundExceptions) {
                        throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                    }

                    throw var6;
                }

                Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
            }

            try {
                this.getPreAuthenticationChecks().check(user);
                this.additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
            } catch (AuthenticationException var7) {
                if (!cacheWasUsed) {
                    throw var7;
                }

                cacheWasUsed = false;
                user = this.retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
                this.getPreAuthenticationChecks().check(user);
                this.additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
            }

            this.getPostAuthenticationChecks().check(user);
            if (!cacheWasUsed) {
                this.getUserCache().putUserInCache(user);
            }

            Object principalToReturn = user;
            if (this.isForcePrincipalAsString()) {
                principalToReturn = user.getUsername();
            }

            Authentication auth = this.createSuccessAuthentication(principalToReturn, authentication, user);
            //如果验证通过登录成功则重置尝试次数， 否则抛出异常
            userDetailsDao.resetFailAttempts(username);
            return auth;
        } catch (BadCredentialsException e) {
            //如果验证不通过，则更新尝试次数，当超过次数以后抛出账号锁定异常
            userDetailsDao.updateFailAttempts(username);
            throw e;
        } catch (LockedException e) {
            //该用户已经被锁定，则进入这个异常
            String error;
            Attempts userAttempts =
                    userDetailsDao.getAttempts(username);
            if (userAttempts != null) {
                Date lastAttempts = userAttempts.getLastModified();
                logger.info("用户已经被锁定，用户名 : "
                        + authentication.getName() + "，最后尝试登陆时间 : " + DateUtil.formatDate(lastAttempts));
                error = "用户已经被锁定";
            } else {
                error = e.getMessage();
            }
            throw new LockedException(error);
        }
    }
}
