package com.twelvet.auth.config;

import com.twelvet.auth.security.handle.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: WEB拦截配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 设置权限路径
        http
                // CRSF禁用，因为不使用session
                .csrf().disable()
                // 设置拦截路径
                .authorizeRequests()
                // 放行路径
                .antMatchers(
                        "/login"
                        ).permitAll()
                // 全部拦截
                .anyRequest().authenticated();


        // 自定义登录路径
        http.formLogin()
                // 自定义登录页面（需要自己实现Controller）
                //.loginPage("/login")
                // 处理登录地址
                .loginProcessingUrl("/login")
                // 错误地址
                .failureUrl("/login?error=1");

    }

}