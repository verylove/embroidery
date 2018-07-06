package cn.wind.auth.security.attemptes;

import cn.hutool.core.util.StrUtil;
import cn.wind.common.domain.Attempts;
import cn.wind.common.enums.ServerEnum;
import cn.wind.klock.service.RedisService;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;

/**
 * @author xukk
 * @date 2018/3/14
 */
@Service
public class UserDetailsDaoImpl extends JdbcDaoSupport implements UserDetailsDao{
    private static final String SQL_USERS_UPDATE_LOCKED = "UPDATE t_sys_user SET account_non_locked = ?,lock_time=? WHERE username = ? ";
    private static final String SQL_USERS_COUNT = "SELECT count(*) FROM t_sys_user WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_GET = "SELECT * FROM t_sys_user_attempts WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_INSERT = "INSERT INTO t_sys_user_attempts (id,username, attempts, last_modified) VALUES(?,?,?,?)";
    private static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE t_sys_user_attempts SET attempts = attempts + 1, last_modified = ? WHERE username = ?";
    private static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE t_sys_user_attempts SET attempts = 0, last_modified = now() WHERE username = ?";

    private static final String SQL_CLIENTS_UPDATE_LOCKED = "UPDATE t_cli_client SET account_non_locked = ?,lock_time=? WHERE username = ?";
    private static final String SQL_CLIENTS_COUNT = "SELECT count(*) FROM t_cli_client WHERE username = ?";
    private static final String SQL_CLIENT_ATTEMPTS_GET = "SELECT * FROM  t_cli_client_attempts WHERE username = ?";
    private static final String SQL_CLIENT_ATTEMPTS_INSERT = "INSERT INTO t_cli_client_attempts (id,username, attempts, last_modified) VALUES(?,?,?,?)";
    private static final String SQL_CLIENT_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE t_cli_client_attempts SET attempts = attempts + 1, last_modified = ? WHERE username = ?";
    private static final String SQL_CLIENT_ATTEMPTS_RESET_ATTEMPTS = "UPDATE t_cli_client_attempts SET attempts = 0, last_modified = now() WHERE username = ?";
    private static final int MAX_ATTEMPTS = 5;
    @Resource
    private DataSource dataSource;
    @Resource
    private RedisService<Object> redisService;
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    @Override
    public void updateFailAttempts(String username) {
        Attempts user = getAttempts(username);
        String simpleUsername=simpleUsername(username);
        if (user == null) {
            if (isUserExists(username)) {
                // 如果之前没有记录，添加一条
                getJdbcTemplate().update(transferUsername(username,SQL_USER_ATTEMPTS_INSERT,SQL_CLIENT_ATTEMPTS_INSERT), IdWorker.getId(),simpleUsername, 1, new Date());
            }
        } else {
            if (isUserExists(username)) {
                // 存在用户则失败一次增加一次尝试次数
                getJdbcTemplate().update(transferUsername(username,SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS,SQL_CLIENT_ATTEMPTS_UPDATE_ATTEMPTS), new Date(),simpleUsername);
            }
            if (user.getAttempts() + 1 >= MAX_ATTEMPTS) {
                long lockTime=3600;
                // 大于尝试次数则锁定
                getJdbcTemplate().update(transferUsername(username,SQL_USERS_UPDATE_LOCKED,SQL_CLIENTS_UPDATE_LOCKED), false,new Date (System.currentTimeMillis()+lockTime*1000l),simpleUsername);

                redisService.setEx("locked_time:"+username,username,lockTime);
                // 并且抛出账号锁定异常
                throw new LockedException("用户账号已被锁定，请过一小时再尝试");
            }
        }
    }
    @Override
    public Attempts getAttempts(String username) {
        String simpleUsername=simpleUsername(username);
        try {
            Attempts userAttempts = getJdbcTemplate().queryForObject(transferUsername(username,SQL_USER_ATTEMPTS_GET,SQL_CLIENT_ATTEMPTS_GET),
                    new Object[] { simpleUsername }, (rs, rowNum) -> {
                        Attempts user = new Attempts();
                        user.setId(rs.getLong("id"));
                        user.setUsername(rs.getString("username"));
                        user.setAttempts(rs.getInt("attempts"));
                        user.setLastModified(new Date(rs.getTimestamp("last_modified").getTime()));
                        return user;
                    });
            return userAttempts;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void resetFailAttempts(String username) {
        String simpleUsername=simpleUsername(username);
        getJdbcTemplate().update(
                transferUsername(username,SQL_USER_ATTEMPTS_RESET_ATTEMPTS,SQL_CLIENT_ATTEMPTS_RESET_ATTEMPTS), simpleUsername);
    }

    private boolean isUserExists(String username) {
        String simpleUsername=simpleUsername(username);
        boolean result = false;
        int count = getJdbcTemplate().queryForObject(
                transferUsername(username,SQL_USERS_COUNT,SQL_CLIENTS_COUNT), new Object[] { simpleUsername }, Integer.class);
        if (count > 0) {
            result = true;
        }
        return result;
    }

    private String transferUsername(String username, String userSQL, String clientSQL){
        String[] usernameArr = username.split(StrUtil.COLON);
        if (usernameArr.length == 2) {
            String scope = usernameArr[0];
            if (StringUtils.equalsAny(scope, ServerEnum.app.name(),ServerEnum.webApp.name())) {
                return clientSQL;
            }
        }
        return userSQL;
    }

    private String simpleUsername(String username){
        String[] usernameArr = username.split(StrUtil.COLON);
        if (usernameArr.length == 2) {
            username = usernameArr[1];
            return username;
        }
        return username;
    }
}
