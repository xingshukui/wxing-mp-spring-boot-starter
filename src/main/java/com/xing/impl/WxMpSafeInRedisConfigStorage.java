package com.xing.impl;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2018/11/2 2:58 PM
 * @desc :
 */
public class WxMpSafeInRedisConfigStorage extends WxMpInMemoryConfigStorage {

    private final static String ACCESS_TOKEN_KEY = "wechat_access_token_";

    private final static String JSAPI_TICKET_KEY = "wechat_jsapi_ticket_";

    private final static String CARDAPI_TICKET_KEY = "wechat_cardapi_ticket_";

    /**
     * 使用连接池保证线程安全
     */
    protected final RedissonClient redissonClient;

    private String accessTokenKey;

    private String jsapiTicketKey;

    private String cardapiTicketKey;

    public WxMpSafeInRedisConfigStorage(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 每个公众号生成独有的存储key
     *
     * @param appId
     */
    @Override
    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
        this.jsapiTicketKey = JSAPI_TICKET_KEY.concat(appId);
        this.cardapiTicketKey = CARDAPI_TICKET_KEY.concat(appId);
    }


    @Override
    public Lock getAccessTokenLock() {
        //todo 实现lock
        return super.getAccessTokenLock();
    }

    @Override
    public String getAccessToken() {
        return (String) redissonClient.getBucket(accessTokenKey).get();
    }

    @Override
    public boolean isAccessTokenExpired() {
        //todo 判断过期？
        return false;
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        redissonClient.getBucket(accessTokenKey).set(accessToken, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireAccessToken() {
        redissonClient.getBucket(accessTokenKey).delete();
    }

    @Override
    public String getJsapiTicket() {
        return (String) redissonClient.getBucket(jsapiTicketKey).get();
    }

    @Override
    public boolean isJsapiTicketExpired() {
        //todo 判断过期?
        return false;
    }

    @Override
    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        redissonClient.getBucket(jsapiTicketKey).set(jsapiTicket, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireJsapiTicket() {
        redissonClient.getBucket(jsapiTicketKey).delete();
    }

    @Override
    public String getCardApiTicket() {
        return (String) redissonClient.getBucket(cardapiTicketKey).get();
    }

    @Override
    public boolean isCardApiTicketExpired() {
        //todo 判断过期
        return false;
    }

    @Override
    public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        redissonClient.getBucket(cardapiTicketKey).set(cardApiTicket, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void expireCardApiTicket() {
        redissonClient.getBucket(cardapiTicketKey).delete();
    }
}
