package com.fengdi.baseproject.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fengdi.baseproject.app.constant.ConstantPay;
import com.fengdi.baseproject.wxapi.bean.WeiXin;
import com.fengdi.baseproject.wxapi.bean.WeiXinType;
import com.fengdi.baseproject.wxapi.bean.WeiXinUtil;
import com.jess.arms.integration.EventBusManager;
import com.jess.arms.utils.ObjectUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import timber.log.Timber;

/**
 * @作者 ：hexingbo
 * @时间 ：2019/4/1
 * @描述 ：WXEntryActivity 微信登陆分享回调Activity
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

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
        wxAPI.handleIntent(getIntent(), this);
        Timber.d("WXEntryActivity onNewIntent");
    }

    @Override
    public void onReq(BaseReq arg0) {
        Timber.d("WXEntryActivity onReq:" + arg0);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            Timber.d("微信分享操作.....");
            WeiXin weiXin = new WeiXin(WeiXinType.Event_WeiXin_Share, resp.errCode, "");
            EventBusManager.getInstance().post(weiXin);
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//登陆
            Timber.d("微信登录操作.....");
            SendAuth.Resp authResp = (SendAuth.Resp) resp;
            WeiXin weiXin = new WeiXin(WeiXinType.Event_WeiXin_Loging, resp.errCode, authResp.code);
            EventBusManager.getInstance().post(weiXin);
        } else if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            //移动应用跳转到小程序回调
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
            String extraData = launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            Timber.i("移动应用跳转到小程序回调：" + extraData);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


    /**
     * w微信登录，支付，分享返回事件
     *
     * @param weiXin
     */
    @Subscriber
    public void handleEvent(WeiXin weiXin) {
        getWeiXinResultActionType(weiXin);
    }


    /**
     * 微信请求返回处理
     *
     * @param weiXin
     */
    public void getWeiXinResultActionType(WeiXin weiXin) {
        if (!ObjectUtils.isEmpty(weiXin)) {
            Timber.i("weixin"+"收到eventbus请求 type:" + weiXin.getType());
            switch (weiXin.getType()) {
                case 1:  //todo 登录
                    if (!ObjectUtils.isEmpty(weiXin.getCode())) {
                        Timber.i("weixin"+"授权成功....." + weiXin.toString());

                    } else {
                        Timber.i("weixin"+ "授权失败.....");

                    }
                    break;
                case 2://todo 分享
                    switch (weiXin.getErrCode()) {
                        case BaseResp.ErrCode.ERR_OK:
                            Timber.i("weixin"+"微信分享成功.....");
                            break;
                        case BaseResp.ErrCode.ERR_USER_CANCEL://分享取消
                            Timber.i("weixin"+"微信分享取消.....");
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED://分享被拒绝
                            Timber.i("weixin"+"微信分享被拒绝.....");
                            break;
                        default:
                            break;
                    }
                    break;
                case 3://todo 微信支付
                    if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {//成功
                        Timber.i("weixin"+"微信支付成功.....");
                    }
                    break;
                default:
                    break;
            }
        }
    }

}


