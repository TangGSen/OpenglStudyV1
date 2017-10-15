//
// Created by Administrator on 2017/10/11.
//

#include "drawAnyS.h"
#include "scence.h"
#include "sen_test.h"
#include "model.h"
glm::mat4 mViewMatrix;
glm::mat4 mProjectionMatrix;
glm::mat4 mModelMatrix;
Model * model;
void init() {
    model = new Model;
    model->initModel("Res/Sphere.obj");
    model->setModelPosition(0.0f,0.0f,-5.0f);

}

void setViewPortSize(float width, float height) {
    /**
     * 1.视角
     * 2.宽高比
     * 3.最近看到的距离
     * 4.最远看到的距离
     */
    mProjectionMatrix= glm::perspective(60.0f,width/height,0.1f,1000.0f);
    //其他两个没设置就是单位矩阵
}


//绘制   使用ElementBuffer 来指定顶点顺序来绘制
void draw() {
    float time = getTime();
    glClearColor(0.6f,0.0f,0.6f,1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    model->drawModel(mViewMatrix,mProjectionMatrix);
    //良好习惯，当绘制完毕后，将程序置为0 号程序
    glUseProgram(0);
    float timeEnd = getTime();
    LOGE("draw usetime %f",timeEnd-time);
}
