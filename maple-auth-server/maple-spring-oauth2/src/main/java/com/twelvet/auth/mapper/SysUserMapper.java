package com.twelvet.auth.mapper;


import com.twelvet.system.pojo.SysUser;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 * system用户mapper映射
 */
public interface SysUserMapper {

    /**
     * 根据用户名称查询用户
     * @param userName
     * @return
     */
    SysUser selectUserByUserName(String userName);

}
