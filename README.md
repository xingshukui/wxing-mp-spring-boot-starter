# wxing-mp-spring-boot-starter

本项目主要是对微信公众号相关基础功能进行封装
1. 对access_token集中管理，不至于调用上限。
    
    项目用了开源的weixin-java-mp，里面有2种方式存access_token
    >1. 内存，对应实现WxMpInMemoryConfigStorage，此种方式对于分布式服务不适用。
    >2. redis，对应实现WxMpSafeInRedisConfigStorage，此种方式使用与分布式应用
    
    本项目中做了2种配置：
        
        //redis存储
        @Bean
        @ConditionalOnBean(RedissonClient.class)
        public WxMpConfigStorage wxMpSafeInRedisConfigStorage(JedisPool jedisPool) {
            WxMpSafeInRedisConfigStorage configStorage = new WxMpSafeInRedisConfigStorage(redissonClient);
            configStorage.setAppId(wxCommonProperties.getAppId());
            configStorage.setSecret(wxCommonProperties.getSecret());
            configStorage.setToken(wxCommonProperties.getToken());
            configStorage.setAesKey(wxCommonProperties.getAesKey());
            return configStorage;
        }
        
        //内存存储
        @Bean
        @ConditionalOnMissingBean(RedissonClient.class)
        public WxMpConfigStorage WxMpInMemoryConfigStorage() {
            WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
            configStorage.setAppId(wxCommonProperties.getAppId());
            configStorage.setSecret(wxCommonProperties.getSecret());
            configStorage.setToken(wxCommonProperties.getToken());
            configStorage.setAesKey(wxCommonProperties.getAesKey());
            return configStorage;
        }
    
2. 微信配置集中，基础功能封装，不同服务以jar包形式调用，不用重复造轮子，具体配置类WxCommonProperties，WxMessageAutoConfiguration....

#### 实现功能
 WxMpService中有各种api，如果想加入自己的东西，可以自己定义
    
    

        @Bean
        public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
            return wxMpService;
        }


* 自定义

    1.message，如下，WxMessageService中可加入自己的东西
    
        @Bean
        public WxMessageService wxMessageService(WxMpConfigStorage wxMpConfigStorage) {
            WxMpService wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
            return new WxMessageService(wxMpService.getTemplateMsgService());
        }

* 其他自定义