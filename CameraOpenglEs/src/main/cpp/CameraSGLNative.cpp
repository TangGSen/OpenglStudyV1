//
// Created by Administrator on 2017/10/11.
//
#include "CameraSGLNative.h"
#include "ggl.h"
#include "scence.h"

//c++ 11 的nullptr
AAssetManager *aAssetManager = nullptr;
Camera *camera;


unsigned char *loadFile(const char *path, int &fileSize) {
    unsigned char *file = nullptr;
    fileSize = 0;
    //android 读取内部资源的方法
    AAsset *asset = AAssetManager_open(aAssetManager, path, AASSET_MODE_UNKNOWN);
    if (asset == nullptr)
        return nullptr;
    //读取成功
    fileSize = AAsset_getLength(asset);
    //开辟内存 +1 为了file[fileSize] = '\0';
    file = new unsigned char[fileSize + 1];
    AAsset_read(asset, file, fileSize);
    //为了程序的健壮性
    file[fileSize] = '\0';
    //关闭
    AAsset_close(asset);
    return file;
}


 void JNICALL Java_sen_opengl_camera_main_CameraSGLNative_initAssetManager
        (JNIEnv *env, jclass clzss, jobject assetManager) {
    aAssetManager = AAssetManager_fromJava(env, assetManager);
   camera = beforInit(env);

};

JNIEXPORT void JNICALL Java_sen_opengl_camera_main_CameraSGLNative_onSurfaceCreated
        (JNIEnv *env, jclass clzss) {
    init();

};


JNIEXPORT void JNICALL Java_sen_opengl_camera_main_CameraSGLNative_onSurfaceChanged
        (JNIEnv *env, jclass clzss, jint width, jint height) {
    setViewPortSize(width, height);


};

JNIEXPORT void JNICALL Java_sen_opengl_camera_main_CameraSGLNative_onDrawFrame
        (JNIEnv *env, jclass clzss) {
    draw();

};

JNIEXPORT jobject JNICALL Java_sen_opengl_camera_main_CameraSGLNative_getSurfaceTexture
        (JNIEnv *, jclass){
    return camera->getSurfaceTextureObject();
}