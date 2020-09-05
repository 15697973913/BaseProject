package com.fengdi.baseproject.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.fengdi.baseproject.app.constant.ConstantPay;
import com.fengdi.baseproject.wxapi.bean.WeiXin;
import com.fengdi.baseproject.wxapi.bean.WeiXinType;
import com.fengdi.baseproject.wxapi.bean.WeiXinUtil;
import com.jess.arms.integration.EventBusManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.simple.eventbus.EventBus;

import timber.log.Timber;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxAPI = WeiXinUtil.getWxApiInstance();
        wxAPI.registerApp(ConstantPay.WECHAT_APPID);
        wxAPI.handleIntent(getIntent(), this);
        EventBus.getDefault().register(this);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxAPI.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @SuppressLint("TimberArgCount")
    @Override
    public void onResp(BaseResp resp) {
        Timber.i("WXPay", "微信支付回调 返回错误码:" + resp.errCode + " 错误名称:" + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {//微信支付
            WeiXin weiXin = new WeiXin(WeiXinType.Event_WeiXin_Pay, resp.errCode, "");
            EventBusManager.getInstance().post(weiXin);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}
