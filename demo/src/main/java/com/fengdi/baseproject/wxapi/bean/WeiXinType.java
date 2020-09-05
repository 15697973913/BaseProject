package com.fengdi.baseproject.wxapi.bean;

/**
 *  @作者 ：hexingbo
 *  @时间 ：2019/4/1
 *  @描述 ：WeiXinType
 */
public interface WeiXinType {
    /**
     * 微信分享成功
     */
    int Event_WeiXin_Share = 2;
    /**
     * 微信登录成功
     */
    int Event_WeiXin_Loging = 1;
    /**
     * 微信支付成功
     */
    int Event_WeiXin_Pay = 3;

}
