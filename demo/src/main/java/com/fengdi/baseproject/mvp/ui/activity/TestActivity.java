package com.fengdi.baseproject.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fengdi.baseproject.R;
import com.fengdi.baseproject.app.utils.ImageChoiceUtil;
import com.fengdi.baseproject.wxapi.bean.WeiXinUtil;
import com.fengdi.baseproject.di.component.DaggerTestComponent;
import com.fengdi.baseproject.mvp.contract.TestContract;
import com.fengdi.baseproject.mvp.presenter.TestPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsPermissionUtil;
import com.jess.arms.utils.ArmsToast;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.qmuiteam.qmui.layout.QMUIFrameLayout;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIFullScreenPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 17:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.qLinTest)
    QMUILinearLayout qLinTest;

    @Inject
    RxPermissions mRxPermissions;

    @Inject
    RxErrorHandler mErrorHandler;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQrcode();
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.qLinTest, R.id.button6,R.id.button7,R.id.button8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                //图片选择器
                ImageChoiceUtil.goToPictureSelector(false, false, 500, 500
                        , new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                if (result != null) {
                                    for (LocalMedia media : result) {
                                        if (media == null) {
                                            continue;
                                        }
                                        Timber.i("是否压缩:%s", media.isCompressed());
                                        Timber.i("压缩:%s", media.getCompressPath());
                                        Timber.i("原图:%s", media.getPath());
                                        Timber.i("是否裁剪:%s", media.isCut());
                                        Timber.i("裁剪:%s", media.getCutPath());
                                        Timber.i("是否开启原图:%s", media.isOriginal());
                                        Timber.i("原图路径:%s", media.getOriginalPath());
                                        Timber.i("Android Q 特有Path:%s", media.getAndroidQToPath());
                                        Timber.i("宽高: %s", media.getWidth() + "," + media.getHeight());
                                        Timber.i("Size: %s", media.getSize());
                                        // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
                                    }
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                break;
            case R.id.button2:
                ArmsPermissionUtil.requestPermission(new ArmsPermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        //扫码
                        scanQrcode();
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> permissions) {

                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

                    }
                }, mRxPermissions, mErrorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

                break;
            case R.id.button3:
                qLinTest.setRadiusAndShadow(QMUIDisplayHelper.dp2px(this, 10),
                        QMUIDisplayHelper.dp2px(this, 10),
                        0.2f);
                break;
            case R.id.button4:
                new QMUIDialog.MessageDialogBuilder(this)
                        .setMessage("我是一个测试对话框，哈哈哈！！")
                        .addAction(new QMUIDialogAction(this, R.string.str_dingding, new QMUIDialogAction.ActionListener() {

                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }))
                        .setSkinManager(QMUISkinManager.defaultInstance(this))
                        .show();
                break;
            case R.id.button5:
                QMUITipDialog tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord("发送成功")
                        .create();
                tipDialog.show();

                button2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                    }
                }, 1500);

                break;
            case R.id.qLinTest:
                QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
                QMUIFrameLayout frameLayout = new QMUIFrameLayout(this);
                frameLayout.setBackground(
                        QMUIResHelper.getAttrDrawable(this, R.attr.qmui_skin_support_popup_bg));
                builder.background(R.attr.qmui_skin_support_popup_bg);
                QMUISkinHelper.setSkinValue(frameLayout, builder);
                frameLayout.setRadius(QMUIDisplayHelper.dp2px(this, 12));
                int padding = QMUIDisplayHelper.dp2px(this, 20);
                frameLayout.setPadding(padding, padding, padding, padding);

                TextView textView = new TextView(this);
                textView.setLineSpacing(QMUIDisplayHelper.dp2px(this, 4), 1.0f);
                textView.setPadding(padding, padding, padding, padding);
                textView.setText("这是自定义显示的内容");
                textView.setTextColor(
                        QMUIResHelper.getAttrColor(this, Color.WHITE));

                builder.clear();
                builder.textColor(Color.WHITE);
                QMUISkinHelper.setSkinValue(textView, builder);
                textView.setGravity(Gravity.CENTER);

                builder.release();

                int size = QMUIDisplayHelper.dp2px(this, 200);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(size, size);
                frameLayout.addView(textView, lp);

                QMUIFullScreenPopup popup = QMUIPopups.fullScreenPopup(this)
                        .addView(frameLayout)
                        .closeBtn(false)//取消按钮
                        .skinManager(QMUISkinManager.defaultInstance(this))
                        .onBlankClick(new QMUIFullScreenPopup.OnBlankClickListener() {
                            @Override
                            public void onBlankClick(QMUIFullScreenPopup popup) {
                                Toast.makeText(TestActivity.this, "点击到空白区域", Toast.LENGTH_SHORT).show();
                                popup.dismiss();
                            }
                        })
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                Toast.makeText(TestActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                            }
                        });
                popup.show(view);
                break;
            case R.id.button6:
                PayReq request = new PayReq();
                request.appId = "wx69cc28a101ca5619";//appid
                request.nonceStr = "rPEWF3CiZexEXxF2";//随机字符串，不长于32位。推荐随机数生成算法
                request.partnerId = "1529279761";//微信支付分配的商户号
                request.prepayId = "wx15103320836971bae77df98c5154010000";//微信返回的支付交易会话ID
                request.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
                request.timeStamp = "1597458800";//时间戳
                request.sign = "CC57B50B67AE8A57F0562793B576C906";//签名

                boolean sendReqResult = WeiXinUtil.getWxApiInstance().sendReq(request);

                break;
            case R.id.button7:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = String.valueOf(System.currentTimeMillis());
                WeiXinUtil.getWxApiInstance().sendReq(req);
                break;
            case R.id.button8:
                AppManager.getAppManager().startActivity(TestCMakeActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 扫码
     */
    private final int FLAG_SCAN_QRCODE = 0x111;

    /**
     * 跳转到扫码二维码界面
     */
    private void scanQrcode() {
        //扫一扫
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, FLAG_SCAN_QRCODE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_SCAN_QRCODE && resultCode == Activity.RESULT_OK && data != null) {
            String result = data.getStringExtra(Constant.CODED_CONTENT);

            ArmsToast.show("扫描到:\t\t" + result);

        }
    }
}
