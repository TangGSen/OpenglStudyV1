//
// Created by Administrator on 2017/10/20.
//

#include <ggl.h>
#include "camera.h"

//
//GLuint Camera::createOESTextureObject() {
//    GLuint *tex;
//    //生成一个纹理
//    glGenTextures(1, tex);
//    //将此纹理绑定到外部纹理上
//    glBindTexture(GL_TEXTURE_EXTERNAL_OES, tex[0]);
//    //设置纹理过滤参数
//    glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//    glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//    glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//    glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//    //解除纹理绑定
//    glBindTexture(GL_TEXTURE_EXTERNAL_OES, 0);
//
//    return tex[0];
//}
//
//
//
//
//
//


jobject Camera::getSurfaceTextureObject() {
    if (javaSurfaceTextureObj == NULL) {
        return NULL;
    }
    return javaSurfaceTextureObj;
}

void Camera::init() {
    vertexBuffer = new VertexBuffer;
    vertexBuffer->setSize(4);

//    vertexBuffer->setPosition(0,-1.0f, 1.0f, 0.0f, 1.0f);
//    vertexBuffer->setPosition(1,-1.0f, -1.0f, 0.0f, 1.0f);
//    vertexBuffer->setPosition(2,1.0f, -1.0f, 0.0f, 1.0f);
//    vertexBuffer->setPosition(3,1.0f, 1.0f, 0.0f,1.0f);

    vertexBuffer->setPosition(0,-0.5f, 0.5f, 0.0f, 1.0f);
    vertexBuffer->setPosition(1,-0.5f, -0.5f, 0.0f, 1.0f);
    vertexBuffer->setPosition(2,0.5f, -0.5f, 0.0f, 1.0f);
    vertexBuffer->setPosition(3,0.5f, 0.5f, 0.0f,1.0f);

    vertexBuffer->setTexcoord(0,0.0f,1.0f);
    vertexBuffer->setTexcoord(1,1.0f, 1.0f);
    vertexBuffer->setTexcoord(2,1.0f, 0.0f);
    vertexBuffer->setTexcoord(3,0.0f, 0.0f);


    mShader = new SShader;
    mShader->init("Res/camera_model.vs", "Res/camera_model.fs");
}


void Camera::createSurfaceTextureObject(JNIEnv *env) {
    glGenTextures(1, &textureId);
    glBindTexture(GL_TEXTURE_EXTERNAL_OES, textureId);
    glTexParameteri(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    const char *stClassPath = "android/graphics/SurfaceTexture";
    const jclass surfaceTextureClass = env->FindClass(stClassPath);
    if (surfaceTextureClass == 0) {
    }

//    // find the constructor that takes an int
    const jmethodID constructor = env->GetMethodID(surfaceTextureClass, "<init>", "(I)V");
    if (constructor == 0) {
    }

    jobject obj = env->NewObject(surfaceTextureClass, constructor, textureId);
    if (obj == 0) {
    }
    javaSurfaceTextureObj = env->NewGlobalRef(obj);

}
void Camera::drawModel(glm::mat4 &mViewMatrix, glm::mat4 &mProjectionMatrix) {
    glEnable(GL_DEPTH_TEST);
    //由于光照需要摄像机的位置
//    mShader->setUiformVec4("U_CameraPos", x, y, z, 1.0);
    vertexBuffer->bind();
//    glm::mat4 it = glm::inverseTranspose(mModelMatrix);


    mShader->bind(glm::value_ptr(mModelMatrix), glm::value_ptr(mViewMatrix),
                  glm::value_ptr(mProjectionMatrix));
    mShader->setTextureEexternalOes(textureId);
//    mShader->setOESTextureId(textureId);
//    glUniformMatrix4fv(glGetUniformLocation(mShader->mProgram, "IT_ModelMatrix"), 1, GL_FALSE,
//                       glm::value_ptr(it));
    glDrawArrays(GL_TRIANGLE_STRIP, 0, vertexBuffer->mVertexCount);
//    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    vertexBuffer->unBind();
}



