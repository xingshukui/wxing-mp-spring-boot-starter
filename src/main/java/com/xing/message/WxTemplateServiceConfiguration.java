package com.xing.message;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2018/11/5 4:22 PM
 * @desc :
 */
@Configuration
@ConditionalOnProperty(name = "wechat.mp.template_msg_able")
public class WxTemplateServiceConfiguration {


    @Bean
    public WxMessageService wxMessageService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return new WxMessageService(wxMpService.getTemplateMsgService());
    }

}
