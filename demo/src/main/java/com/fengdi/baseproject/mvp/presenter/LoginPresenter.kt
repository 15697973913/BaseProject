package com.fengdi.baseproject.mvp.presenter

import android.app.Application
import com.fengdi.baseproject.mvp.contract.LoginContract
import com.fengdi.baseproject.mvp.ui.activity.MainActivity
import com.fengdi.baseproject.mvp.ui.activity.TestActivity
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.yzq.zxinglibrary.android.CaptureActivity
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2020 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LoginPresenter
@Inject
constructor(model: LoginContract.Model, rootView: LoginContract.View) :
        BasePresenter<LoginContract.Model, LoginContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    @Inject
    lateinit var mApplication: Application

    @Inject
    lateinit var mImageLoader: ImageLoader

    @Inject
    lateinit var mAppManager: AppManager

    override fun onDestroy() {
        super.onDestroy();
    }

    fun login(phone: String, password: String) {

        //扫一扫
        AppManager.getAppManager().startActivity(TestActivity::class.java)
//        AppManager.getAppManager().startActivity(MainActivity::class.java)

    }

}
