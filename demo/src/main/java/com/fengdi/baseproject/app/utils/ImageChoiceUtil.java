package com.fengdi.baseproject.app.utils;

import android.content.pm.ActivityInfo;

import com.jess.arms.integration.AppManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.SdkVersionUtils;

import java.io.File;

import com.fengdi.baseproject.R;


public class ImageChoiceUtil {

    /**
     * 选择图片或拍照
     *地址：https://github.com/LuckSiege/PictureSelector
     * @param previewImage   是否可以预览
     * @param enableCrop     是否可以裁剪
     * @param aspectRatioX 裁剪的x比列  如16:9 3:2 3:4 1:1 可自定义
     * @param aspectRatioY 裁剪的y比列  如16:9 3:2 3:4 1:1 可自定义
     */
    public static void goToPictureSelector(boolean previewImage,
                                           boolean enableCrop, int aspectRatioX, int aspectRatioY, OnResultCallbackListener<LocalMedia> resultCallbackListener) {

        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(AppManager.getAppManager().getCurrentActivity())
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isUseCustomCamera(false)// 是否使用自定义相机
                .setLanguage(LanguageConfig.CHINESE)// 设置语言，默认中文
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
//                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
//                .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
//                .setRecyclerAnimationMode(animationMode)// 列表动画效果
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
//                .isMaxSelectEnabledMask(cbEnabledMask.isChecked())// 选择数到了最大阀值列表是否启用蒙层效果
                //.isAutomaticTitleRecyclerTop(false)// 连续点击标题栏RecyclerView是否自动回到顶部,默认true
                //.loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
                //.setOutputCameraPath()// 自定义相机输出目录，只针对Android Q以下，例如 Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +  File.separator + "Camera" + File.separator;
                //.setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 设置自定义相机按钮状态
//                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .maxVideoSelectNum(1) // 视频最大选择数量
                //.minVideoSelectNum(1)// 视频最小选择数量
                //.closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 关闭在AndroidQ下获取图片或视频宽高相反自动转换
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
//                .isOriginalImageControl(cb_original.isChecked())// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义视频播放回调控制，用户可以使用自己的视频播放界面
                //.bindCustomPreviewCallback(new MyCustomPreviewInterfaceListener())// 自定义图片预览回调接口
                //.bindCustomCameraInterfaceListener(new MyCustomCameraInterfaceListener())// 提供给用户的一些额外的自定义操作回调
                //.cameraFileName(System.currentTimeMillis() +".jpg")    // 重命名拍照文件名、如果是相册拍照则内部会自动拼上当前时间戳防止重复，注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
                //.renameCompressFile(System.currentTimeMillis() +".jpg")// 重命名压缩文件名、 如果是多张压缩则内部会自动拼上当前时间戳防止重复
                //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 如果是多张裁剪则内部会自动拼上当前时间戳防止重复
//                .selectionMode(cb_choose_mode.isChecked() ?
//                        PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
//                .isSingleDirectReturn(cb_single_back.isChecked())// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
//                .isPreviewImage(cb_preview_img.isChecked())// 是否可预览图片
//                .isPreviewVideo(cb_preview_video.isChecked())// 是否可预览视频
                //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
//                .isEnablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
//                .isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
                //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                //.isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType.PNG_Q
                .isEnableCrop(enableCrop)// 是否裁剪
                //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
                .isCompress(true)// 是否压缩
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
                .withAspectRatio(aspectRatioX, aspectRatioY)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(!cb_hide.isChecked())// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
//                .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                //.setCropDimmedColor(ContextCompat.getColor(getContext(), R.color.app_color_white))// 设置裁剪背景色值
                //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .isOpenClickSound(cb_voice.isChecked())// 是否开启点击声音
//                .selectionData(mAdapter.getData())// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                //.videoMinSecond(10)// 查询多少秒以内的视频
                //.videoMaxSecond(15)// 查询多少秒以内的视频
                //.recordVideoSecond(10)//录制视频秒数 默认60s
                //.isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(false) // 裁剪是否可旋转图片
                //.scaleEnabled(false)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                .forResult(resultCallbackListener);
    }


    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private static String getPath() {
        String path = AppManager.getAppManager().getCurrentActivity().getFilesDir() + "/fengdi/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }



}
