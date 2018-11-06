package com.xing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2018/11/2 1:57 PM
 * @desc :
 */
@ConfigurationProperties(prefix = "wechat.mp")
public class WxProperties {

    private String appId;
    private String secret;
    private String token;
    private String aesKey;

    @NestedConfigurationProperty
    private RedisProperties redisProperties;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }
}
