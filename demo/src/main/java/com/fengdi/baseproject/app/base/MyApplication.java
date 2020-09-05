package com.fengdi.baseproject.app.base;

import com.jess.arms.base.BaseApplication;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        QMUISwipeBackActivityManager.init(this);

        super.onCreate();

    }
}
