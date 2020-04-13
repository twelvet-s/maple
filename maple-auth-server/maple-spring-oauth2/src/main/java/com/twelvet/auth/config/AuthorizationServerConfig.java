package com.twelvet.auth.config;

import com.twelvet.auth.exception.AuthExceptionEntryPoint;
import com.twelvet.auth.exception.CustomAccessDeineHandler;
import com.twelvet.auth.exception.OAuth2WebResponseExceptionTranslator;
import com.twelvet.auth.security.service.RedisAuthenticationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;


/**
 * @author L
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 自定义用户认证逻辑,开启refresh_token需要用到
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 密码模式需要注入认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * redis
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 自定义返回异常格式
     */
    @Autowired
    private OAuth2WebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 增强成功获取token的信息
     */
    @Autowired
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

    /**
     * 编码模式
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * 获取数据库的授权信息
     *
     * @return
     */
    @Bean
    public ClientDetailsService jdbcClientDetails() {
        // 基于 JDBC 实现，需要事先在数据库配置客户端信息
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 设置token储存方式
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // 使用mysql存储
        //return new JdbcTokenStore(dataSource);
        // 使用redis存放token
        RedisTokenStore redis = new RedisTokenStore(redisConnectionFactory);
        return redis;
    }

    /**
     * 授权码储存方式(使用自定义redis)
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new RedisAuthenticationCodeServices(redisConnectionFactory);
    }

    /**
     * 安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 设置oauth_client_details中的密码编码器
        security.passwordEncoder(bCryptPasswordEncoder());
        // 允许表单认证（client_id，client_secret），不然只能是头部
        security.allowFormAuthenticationForClients();
        // 放行路径，不然会被spring security拦截
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");

        // 登录或未登录用户无权限处理
        security.authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomAccessDeineHandler());
    }


    /**
     * 声明授权和token的端点以及token的服务的一些配置信息，
     * 比如采用什么存储方式、token的有效期等
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 使用redis储存token（切记：不要关了refresh_token运行，又开启，会导致没有refresh_token）
                .tokenStore(tokenStore())
                // 加入密码模式
                .authenticationManager(authenticationManager)
                // token储存方式（redis）
                .authorizationCodeServices(authorizationCodeServices())
                // 仅允许 GET POST 请求获取 token，即访问端点：oauth/token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 自定义登录逻辑
                .userDetailsService(userDetailsService)
                // 自定义异常返回信息
                .exceptionTranslator(customWebResponseExceptionTranslator)
                // 复用refresh_token
                .reuseRefreshTokens(false)
                .tokenEnhancer(tokenEnhancer);

    }

    /**
     * 获取数据库中的客户端信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetails());
    }



}