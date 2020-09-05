package com.fengdi.baseproject.wxapi.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.fengdi.baseproject.app.constant.ConstantPay;
import com.fengdi.baseproject.wxapi.callback.WeiXinCallback;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsToast;
import com.jess.arms.utils.ObjectUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author :hexingbo
 * @Date :2019/5/29
 * @FileName： WeiXinUtil
 * @Describe :
 */
public class WeiXinUtil {

    private static final String TAG = "WeiXinUtil";
    private static final int THUMB_SIZE = 150;

    public static final String mUserName = "要跳转的小程序id";//小程序原始id ： 华世界商圈<gh_e5bce73bb313> ;华世界小程序<gh_5651df80543e>
    public static final String mWebpageUrl = "https://img.huacaigou.com/wxxcx/index.html";
    public static final String mPath = "pages/mall/index";//小程序页面路径
    public static final String mTitle = "华世界商圈";//小程序页面路径
    public static final String mDescription = "华世界商圈分享内容";//小程序页面路径
    public static final int mMiniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;//正式版:0，测试版:1，体验版:2

    private static final String Path_Property_Payment = "subpackages/propertyFee/pages/home/index";

    public static IWXAPI mWxApi;


    public static IWXAPI getWxApiInstance() {
        if (mWxApi == null) {
            //第二个参数是指你应用在微信开放平台上的AppID
            mWxApi = WXAPIFactory.createWXAPI(AppManager.getAppManager().getCurrentActivity().getApplicationContext(), ConstantPay.WECHAT_APPID, true);
            // 将该app注册到微信
            mWxApi.registerApp(ConstantPay.WECHAT_APPID);
        }
        return mWxApi;
    }

    /**
     * 移动应用拉起小程序功能：物业缴费
     *
     * @param context
     */
    public static void openWXMinApp_Property_Payment(Context context) {
        String appId = ConstantPay.WECHAT_APPID; // 填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = mUserName; // 填小程序原始id
        req.path = Path_Property_Payment;                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
        req.miniprogramType = mMiniprogramType;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }

    /**
     * 分享好友，点击跳转小程序
     *
     * @param title
     * @param description
     * @param bitmap
     * @param path
     */
    public static void shareWX_SmallProgram(String webpageUrl, String userName, String path, String title, String description, Bitmap bitmap) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.miniprogramType = mMiniprogramType;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.webpageUrl = ObjectUtils.isEmpty(webpageUrl) ? mWebpageUrl : webpageUrl; // 兼容低版本的网页链接
        miniProgramObj.userName = ObjectUtils.isEmpty(userName) ? mUserName : userName;     // 小程序原始id
        miniProgramObj.path = ObjectUtils.isEmpty(path) ? mPath : path;            //小程序页面路径

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
//        msg.title = ObjectUtils.isEmpty(title) ? mTitle : title;                            // 消息title
        msg.title = ObjectUtils.isEmpty(description) ? mDescription : description;                            // 消息title
        msg.description = ObjectUtils.isEmpty(description) ? mDescription : description;               // 消息desc

        msg.thumbData = compressByQuality(bitmap, 1 * 128 * 120, false);//小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        mWxApi.sendReq(req);
    }


    /**
     * 分享到朋友圈或者好友
     *
     * @param title
     * @param description
     * @param friendsCircle
     */
    public static void shareWX_FriendsOrCircle(String imgUrl, String title, String description, Bitmap bitmap, boolean friendsCircle) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ObjectUtils.isEmpty(imgUrl) ? mWebpageUrl : imgUrl;//分享url

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = ObjectUtils.isEmpty(title) ? mTitle : title;                    // 消息title
//        msg.title = ObjectUtils.isEmpty(description) ? mDescription : description;                    // 消息title
        msg.description = ObjectUtils.isEmpty(description) ? mDescription : description;               // 消息desc

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
        bitmap.recycle();

        msg.thumbData = bmpToByteArray(thumbBmp, true);//图片内容大小不超过10MB  图片的二进制数据
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = friendsCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        mWxApi.sendReq(req);

    }

    public static void shareFileToWeiXin(String filePath,String title) {
        WXFileObject fileObj = new WXFileObject();
        fileObj.fileData = inputStreamToByte(filePath);//文件路径
        fileObj.filePath = filePath;

        //使用媒体消息分享 
        WXMediaMessage msg = new WXMediaMessage(fileObj);
        msg.title = title;

        //发送请求 
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //创建唯一标识 
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        mWxApi.sendReq(req);
    }

    public static void shareAssetsFileToWeiXin(String filePath) {
        WXFileObject fileObj = new WXFileObject();
        fileObj.fileData = inputStreamToByte(filePath);//文件路径
        fileObj.filePath = filePath;

        //使用媒体消息分享
        WXMediaMessage msg = new WXMediaMessage(fileObj);
        msg.title = "title";

        //发送请求
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //创建唯一标识
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        mWxApi.sendReq(req);
    }

    /**
     * 分享到朋友圈或者好友
     */
    public static void shareWX_FriendsOrCircleImg(Bitmap bitmap, boolean friendsCircle) {

        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = friendsCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        mWxApi.sendReq(req);
    }


    public enum WX_ShearType {
        WX_Friend, //朋友
        WX_FriendsCircle,//朋友圈
        WX_SmallProgram;//小程序
    }


    /**
     * 微信分享
     *
     * @param webpageUrl  分享到小程序传：兼容低版本的网页链接
     * @param imgUrl      图片地址或者分享url
     * @param userName    小程序原始id
     * @param path        小程序页面路径
     * @param title       标题
     * @param description 描述
     * @param bitmap      小程序消息封面图片，小于128k或者分享到朋友或者圈的封面图片byte数组
     * @param type        分享类型  WX_Friend：好友；WX_FriendsCircle：朋友圈；WX_SmallProgram：小程序
     */
    public static void shearToWeiXin(String webpageUrl, String imgUrl, String userName, String path, String title, String description, Bitmap bitmap, WX_ShearType type) {
        switch (type) {
            case WX_SmallProgram:
                // TODO: 2019/5/29 微信小程序
                shareWX_SmallProgram(webpageUrl, userName, path, title, description, bitmap);
                break;
            case WX_Friend:
                // TODO: 2019/5/29 微信好友
                shareWX_FriendsOrCircle(imgUrl, title, description, bitmap, false);
                break;
            case WX_FriendsCircle:
                // TODO: 2019/5/29 微信朋友圈
                /**
                 * 使用方法：
                 * //contentLly是布局文件
                 * Bitmap bitmap = getBitmapByView(contentLly);
                 * ImageUtils.savePhotoToSDCard(bitmap, "/sdcard/file", "img");
                 */
                shareWX_FriendsOrCircle(imgUrl, title, description, bitmap, true);
                break;
            default:
                break;
        }
    }


    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static List<String> stringsToList(final String[] src) {
        if (src == null || src.length == 0) {
            return null;
        }
        final List<String> result = new ArrayList<String>();
        for (int i = 0; i < src.length; i++) {
            result.add(src[i]);
        }
        return result;
    }

    /**
     * 微信请求返回处理
     *
     * @param weiXin
     */
    public static void getWeiXinResultActionType(WeiXin weiXin, WeiXinCallback callback) {
        if (!ObjectUtils.isEmpty(weiXin)) {
            Log.i("weixin", "收到eventbus请求 type:" + weiXin.getType());
            switch (weiXin.getType()) {
                case 1:  //todo 登录
                    if (!ObjectUtils.isEmpty(weiXin.getCode())) {
                        Log.i("weixin", "授权成功.....");
                        if (callback != null)
                            callback.wxLoginSuccess();
                    } else {
                        Log.i("weixin", "授权失败.....");
                        ArmsToast.show("授权失败");
                        if (callback != null)
                            callback.wxLoginError();
                    }
                    break;
                case 2://todo 分享
                    switch (weiXin.getErrCode()) {
                        case BaseResp.ErrCode.ERR_OK:
                            Log.i("weixin", "微信分享成功.....");
                            if (callback != null)
                                callback.wxShearSuccess();
                            break;
                        case BaseResp.ErrCode.ERR_USER_CANCEL://分享取消
                            Log.i("weixin", "微信分享取消.....");
                            ArmsToast.show("微信分享取消");
                            if (callback != null)
                                callback.wxSheareError();
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED://分享被拒绝
                            Log.i("weixin", "微信分享被拒绝.....");
                            ArmsToast.show("微信分享被拒绝");
                            if (callback != null)
                                callback.wxSheareError();
                            break;
                    }
                    break;
                case 3://todo 微信支付
                    if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {//成功
                        Log.i("weixin", "微信支付成功.....");
                        if (callback != null)
                            callback.wxPaySuccess();
                    } else {
                        ArmsToast.show("微信支付失败");
                        if (callback != null)
                            callback.wxPayError();
                    }
                    break;
            }
        }
    }


    /**
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 按质量压缩图片
     *
     * @param src         bitmap图片
     * @param maxByteSize 最大字节数
     * @param recycle
     * @return
     */
    public static byte[] compressByQuality(final Bitmap src, final long maxByteSize, final boolean recycle) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) {
                bytes = baos.toByteArray();
            } else {
                // find the best quality using binary search
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return bytes;
    }

    /**
     * 将输入的流转换为byte数组
     *
     * @param path 路径
     * @return byte数组
     */
    public static byte[] inputStreamToByte(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
