package com.twelvet.auth.security.service;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;


/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 自定义redis授权码储存方式
 */
public class RedisAuthenticationCodeServices extends RandomValueAuthorizationCodeServices {
    //private static Logger log = Logger.getLogger(RedisAuthenticationCodeServices.class);

    /**
     * 授权码前缀
     */
    private static final String AUTH_CODE_KEY = "twelvet:auth_code";


    /**
     * 授权码过期时间
     */
    private static final int EXPIRE = 600;

    private RedisConnectionFactory connectionFactory;

    /**
     * 获得Redis工厂
     *
     * @param connectionFactory
     */
    public RedisAuthenticationCodeServices(RedisConnectionFactory connectionFactory) {
        // 抛出错误
        Assert.notNull(connectionFactory, "RedisConnectionFactory required");
        // 定义连接工厂
        this.connectionFactory = connectionFactory;
    }

    /**
     * 取出Redis连接
     *
     * @return RedisConnection
     */
    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    /**
     * 设置auth_code
     *
     * @param code           授权码键值
     * @param authentication 授权码内容
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        // 得到redis连接
        RedisConnection conn = getConnection();
        // 拼接键值
        String key = AUTH_CODE_KEY + ":" + code;
        try {
            // 设置auth_code（Hash）
            conn.hSet(
                    // key
                    AUTH_CODE_KEY.getBytes(StandardCharsets.UTF_8),
                    // Hash键值
                    code.getBytes(StandardCharsets.UTF_8),
                    // 内容
                    SerializationUtils.serialize(authentication)
            );

        } catch (Exception e) {
            //log.error("保存authentication code 失败", e);
        } finally {
            conn.close();
        }

    }

    /**
     * 移除auth_code
     *
     * @param code
     * @return OAuth2Authentication
     */
    @Override
    protected OAuth2Authentication remove(String code) {
        // 获得连接
        RedisConnection conn = getConnection();
        // 定义OAuth2Authentication
        OAuth2Authentication authentication;
        try {
            // 获取授权码，用于对比
            authentication = SerializationUtils.deserialize(
                    conn.hGet(
                            AUTH_CODE_KEY.getBytes(StandardCharsets.UTF_8),
                            code.getBytes(StandardCharsets.UTF_8)
                    )
            );

            // 不为空删除auth_code
            if (null != authentication) {
                conn.hDel(
                        AUTH_CODE_KEY.getBytes(StandardCharsets.UTF_8),
                        code.getBytes(StandardCharsets.UTF_8)
                );
            }

            return authentication;
        } catch (Exception e) {
            return null;
        } finally {
            conn.close();
        }
    }
}