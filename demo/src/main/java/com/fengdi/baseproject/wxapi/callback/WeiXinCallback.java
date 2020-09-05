package com.fengdi.baseproject.wxapi.callback;


import com.fengdi.baseproject.wxapi.bean.WeiXin;

import org.simple.eventbus.Subscriber;

/**
 * @Author :hexingbo
 * @Date :2019/5/29
 * @FileName： WeiXinCallback
 * @Describe :
 */
public interface WeiXinCallback {
    @Subscriber
    void handleEvent(WeiXin weiXin);

    void wxShearSuccess();

    void wxSheareError();

    void wxLoginSuccess();

    void wxLoginError();

    void wxPaySuccess();

    void wxPayError();

}
