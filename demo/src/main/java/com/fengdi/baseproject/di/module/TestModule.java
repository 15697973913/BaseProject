package com.fengdi.baseproject.di.module;

import androidx.fragment.app.FragmentActivity;

import com.fengdi.baseproject.mvp.contract.UserContract;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.fengdi.baseproject.mvp.contract.TestContract;
import com.fengdi.baseproject.mvp.model.TestModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


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
@Module
public abstract class TestModule {

    @Binds
    abstract TestContract.Model bindTestModel(TestModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(TestContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }
}