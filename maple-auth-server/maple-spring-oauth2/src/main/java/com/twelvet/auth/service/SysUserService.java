package com.twelvet.auth.service;


import com.twelvet.system.pojo.SysUser;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 * 用户 业务层
 */
public interface SysUserService {

    /**
     * 根据用户登录名称查询
     * @param username
     * @return
     */
    SysUser selectUserByUserName(String username);

}
