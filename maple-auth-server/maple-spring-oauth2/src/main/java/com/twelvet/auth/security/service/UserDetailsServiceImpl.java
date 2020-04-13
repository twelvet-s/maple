package com.twelvet.auth.security.service;

import com.twelvet.auth.service.SysUserService;
import com.twelvet.system.feign.RemoteUserService;
import com.twelvet.system.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 * 获取用户
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /*@Autowired
    private PermissionService permissionService;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        // 获取用户
        SysUser user = sysUserService.selectUserByUserName(username);

        List<GrantedAuthority> authorities = new ArrayList<>();

        return new User(user.getLoginName(), user.getPassword(), authorities);
    }
}
