package com.twelvet.auth.service.impl;

import com.twelvet.auth.mapper.SysUserMapper;
import com.twelvet.auth.service.SysUserService;
import com.twelvet.system.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 * 用户 业务层处理
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public SysUser selectUserByUserName(String username) {
        return sysUserMapper.selectUserByUserName(username);
    }
}
