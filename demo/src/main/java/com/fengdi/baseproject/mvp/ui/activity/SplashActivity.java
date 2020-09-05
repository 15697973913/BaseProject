package com.fengdi.baseproject.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fengdi.baseproject.R;
import com.fengdi.baseproject.mvp.contract.Constants;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsDataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.bannerGuide)
    BGABanner bannerGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        boolean mIsNotFirst = (boolean) ArmsDataHelper.getBooleanSF(this, Constants.APP_IS_FIRST, true);
        if (mIsNotFirst) {//首次
            BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
            // 设置数据源
            bannerGuide.setData(localImageSize, ImageView.ScaleType.FIT_XY,
                    R.mipmap.gui1,
                    R.mipmap.gui2,
                    R.mipmap.gui3,
                    R.mipmap.gui4,
                    R.mipmap.gui5,
                    R.mipmap.gui6
            );
        } else {//非首次
            new Handler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    AppManager.getAppManager().startActivity(LoginActivity.class);
                    finish();
                }
            }, 2000);
            bannerGuide.setBackgroundResource(R.mipmap.bg_welcome);

        }

        initEvent();
    }

    private void initEvent() {
        bannerGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == bannerGuide.getItemCount() - 1) {
                    new Handler().postAtTime(new Runnable() {
                        @Override
                        public void run() {
                            AppManager.getAppManager().startActivity(LoginActivity.class);
                            finish();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}