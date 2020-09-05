package com.fengdi.baseproject.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.fengdi.baseproject.di.module.TestModule;
import com.fengdi.baseproject.mvp.contract.TestContract;

import com.jess.arms.di.scope.ActivityScope;
import com.fengdi.baseproject.mvp.ui.activity.TestActivity;


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
@ActivityScope
@Component(modules = TestModule.class, dependencies = AppComponent.class)
public interface TestComponent {
    void inject(TestActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestComponent.Builder view(TestContract.View view);

        TestComponent.Builder appComponent(AppComponent appComponent);

        TestComponent build();
    }
}