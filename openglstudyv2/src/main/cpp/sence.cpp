//
// Created by Administrator on 2017/10/11.
//

#include "drawAnyS.h"
#include "scence.h"
#include "sen_test.h"
glm::mat4 mViewMatrix;
glm::mat4 mProjectionMatrix;
glm::mat4 mModelMatrix;
//使用封装好的来画三角形
VertexBuffer *vertexBuffer;
SShader *mShader;
void init() {
//    testCMap();
    vertexBuffer = new VertexBuffer;
    vertexBuffer->setSize(3);

    vertexBuffer->setPosition(0, -0.2f, -0.2f, -0.6f);
    vertexBuffer->setPosition(1, 0.2f, -0.2f, -0.6f);
    vertexBuffer->setPosition(2, 0.0f, 0.2f, -0.6f);
    vertexBuffer->setNarmal(0, 0.0f, 1.0f, 0.0f);
    vertexBuffer->setNarmal(1, 0.0f, 1.0f, 0.0f);
    vertexBuffer->setNarmal(2 , 0.0f, 1.0f, 0.0f);

    vertexBuffer->setColor(0, 0.7f, 0.7f, 0.7f, 1.0f);
    vertexBuffer->setColor(1 , 0.7f, 0.7f, 0.7f, 1.0f);
    vertexBuffer->setColor(2 , 0.7f, 0.7f, 0.7f, 1.0f);
    vertexBuffer->setTexcoord(0,0.0f,0.0f);
    vertexBuffer->setTexcoord(1,1.0f,0.0f);
    vertexBuffer->setTexcoord(2,0.0f,1.0f);
    mShader = new SShader;
    mShader->init("Res/ground.vs", "Res/ground.fs");
    mShader->setTexture("U_Texture","Res/testv2.bmp");
    mShader->setTexture("U_Texture2","Res/test.bmp");



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
    glEnable(GL_DEPTH_TEST);//启动深度测试
    glClearColor(0.6f,0.0f,0.6f,1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    ////这里要注意,必须先设置vbo ,后shader 才能获取attribute，否则出错
    vertexBuffer->bind();
    mShader->bind(glm::value_ptr(mModelMatrix), glm::value_ptr(mViewMatrix),
                  glm::value_ptr(mProjectionMatrix));

    glDrawArrays(GL_TRIANGLES, 0, 3);
    vertexBuffer->unBind();
    //良好习惯，当绘制完毕后，将程序置为0 号程序
    glUseProgram(0);
    float timeEnd = getTime();
    LOGE("draw usetime %f",timeEnd-time);
}
