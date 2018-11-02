package com.xing.global.config;

import com.xing.impl.WxMpSafeInRedisConfigStorage;
import com.xing.properties.WxProperties;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2018/11/2 2:23 PM
 * @desc :
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
@ConditionalOnProperty(name = "wechat.mp.appId")
public class WxGlobalAutoConfiguration {

    @Autowired
    private WxProperties wxProperties;


    @Bean
    @ConditionalOnProperty(name = "wechat.mp.redis.address")
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(wxProperties.getRedisConfig().getAddress());
        singleServerConfig.setPassword(wxProperties.getRedisConfig().getPassword());
        singleServerConfig.setDatabase(wxProperties.getRedisConfig().getDatabase());
        return Redisson.create(config);
    }


    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public WxMpConfigStorage wxMpSafeInRedisConfigStorage(RedissonClient redissonClient) {
        WxMpSafeInRedisConfigStorage configStorage = new WxMpSafeInRedisConfigStorage(redissonClient);
        configStorage.setAppId(wxProperties.getAppId());
        configStorage.setSecret(wxProperties.getSecret());
        configStorage.setToken(wxProperties.getToken());
        configStorage.setAesKey(wxProperties.getAesKey());
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public WxMpConfigStorage WxMpInMemoryConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(wxProperties.getAppId());
        configStorage.setSecret(wxProperties.getSecret());
        configStorage.setToken(wxProperties.getToken());
        configStorage.setAesKey(wxProperties.getAesKey());
        return configStorage;
    }
}
