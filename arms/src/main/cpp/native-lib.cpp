#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_fengdi_baseproject_mvp_ui_activity_TestCMakeActivity_stringFromJNI(JNIEnv *env,
                                                                            jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}