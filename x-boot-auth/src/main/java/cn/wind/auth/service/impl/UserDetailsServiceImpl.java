package cn.wind.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.wind.auth.domain.UserInfo;
import cn.wind.common.enums.ServerEnum;
import cn.wind.common.utils.BlankUtil;
import cn.wind.db.cli.entity.CliClient;
import cn.wind.db.cli.service.ICliClientService;
import cn.wind.db.sys.entity.SysUser;
import cn.wind.db.sys.service.ISysUserService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author xukk
 * @date 2018/3/14
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ICliClientService clientService;

    @Autowired
    private  MessageSource messageSource;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    @PostConstruct
    public void setMessageSource() {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameArr = username.split(StrUtil.COLON);
        String scope = usernameArr[0];
        username = usernameArr[1];
        Set<GrantedAuthority> grantedAuthorities = Sets.newHashSet();
        if (StringUtils.equalsAny(scope, ServerEnum.app.name(),ServerEnum.webApp.name())) {
           CliClient client= clientService.findByUsername(username);
            if (client== null) {
                throw new UsernameNotFoundException(this.messages.getMessage("usernameNotFound"));
            }
            GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("ROLE_READ");
            GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority("ROLE_WRITE");
            grantedAuthorities.add(grantedAuthority1);
            grantedAuthorities.add(grantedAuthority2);
            return new UserInfo(client.getId(),client.getUsername(),client.getPassword(),
                    client.getEnabled(), client.getAccountNonExpired(), client.getCredentialsNonExpired(), client.getAccountNonLocked(), grantedAuthorities);
        }
        SysUser userDto=userService.findByUsername(username);
        if (userDto== null) {
            throw new UsernameNotFoundException(this.messages.getMessage("usernameNotFound"));
        }
        if(BlankUtil.isNotBlank(userDto.getRoles())){
            userDto.getRoles().forEach(v->{
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+v.getName().toUpperCase());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        if(BlankUtil.isNotBlank(userDto.getPermissions())){
            userDto.getPermissions().forEach(v->{
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+v.getName().toUpperCase());
                grantedAuthorities.add(authority);
            });
        }
        return new UserInfo(userDto.getId(),userDto.getUsername(), userDto.getPassword(),
                userDto.getEnabled(),userDto.getAccountNonExpired(), userDto.getCredentialsNonExpired(),userDto.getAccountNonLocked(), grantedAuthorities);
    }
}
