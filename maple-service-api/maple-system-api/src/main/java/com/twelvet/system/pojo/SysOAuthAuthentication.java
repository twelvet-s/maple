package com.twelvet.system.pojo;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: 系统第三方授权oauth实体
 */
public class SysOAuthAuthentication {

    /**
     * ID
     */
    private Long id;

    /**
     * 第三方授权名称
     */
    private String clientName;

    /**
     * client_id
     */
    private String clientId;

    /**
     * client_secret
     */
    private String clientSecret;

    /**
     * scope
     */
    private String scope;

    /**
     * accessTokenuri
     */
    private String accessTokenuri;

    /**
     * accessTokenuri
     */
    private String userAuthorizationuri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccessTokenuri() {
        return accessTokenuri;
    }

    public void setAccessTokenuri(String accessTokenuri) {
        this.accessTokenuri = accessTokenuri;
    }

    public String getUserAuthorizationuri() {
        return userAuthorizationuri;
    }

    public void setUserAuthorizationuri(String userAuthorizationuri) {
        this.userAuthorizationuri = userAuthorizationuri;
    }
}
