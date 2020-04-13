package com.twelvet.system.feign;

import com.twelvet.framework.constant.ServiceNameConstants;
import com.twelvet.system.feign.factoty.RemoteUserFallbackFactory;
import com.twelvet.system.pojo.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description:
 * 用户服务层
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 通过账号名称获取用户
     * @param username
     * @return
     */
    @GetMapping("user/get/{username}")
    SysUser selectSysUserByUserName(@PathVariable("username") String username);

}
