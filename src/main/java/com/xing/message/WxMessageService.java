package com.xing.message;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2018/11/5 4:37 PM
 * @desc :
 */
public class WxMessageService {

    private WxMpTemplateMsgService wxMpTemplateMsgService;

    public WxMessageService(WxMpTemplateMsgService wxMpTemplateMsgService) {
        this.wxMpTemplateMsgService = wxMpTemplateMsgService;
    }





    public void sendTemplateMsg(WxMpTemplateMessage wxMpTemplateMessage) {
        try {
            wxMpTemplateMsgService.sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
