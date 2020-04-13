package com.twelvet.auth.security.handle;

import com.alibaba.fastjson.JSON;
import com.twelvet.framework.core.pojo.AjaxResult;
import com.twelvet.framework.enums.HttpStatus;
import com.twelvet.framework.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        // 获取状态码
        int code = HttpStatus.UNAUTHORIZED.getCode();
        // 发送json数据
        ServletUtils.renderString(
                httpServletResponse,
                JSON.toJSONString(AjaxResult.error(code, "拒绝访问"))
        );

    }
}
