//
// Created by Administrator on 2017/10/15.
//


#include "SkyBox.h"
void SkyBox::init(const char *imageDir){
    vertexBuffer =new VertexBuffer[6];
    sShader = new SShader[6];
    initFront(imageDir);
    initBack( imageDir);
    initLeft(imageDir);
    initRight(imageDir);
    initTop(imageDir);
    initBottom(imageDir);
};
void SkyBox::initFront(const char*imageDir){
    sShader[0].init("Res/skybox.vs","Res/skybox.fs");
    char temp[256] ;
    memset(temp,0,256);
    strcpy(temp,imageDir);
    strcat(temp,"front.bmp");
    sShader[0].setTexture("U_Texture",temp);
    vertexBuffer[0].setSize(4);
    vertexBuffer[0].setPosition(0,-0.5f,-0.5f,-0.5f);
    vertexBuffer[0].setPosition(1,0.5f,-0.5f,-0.5f);
    vertexBuffer[0].setPosition(2,-0.5f,0.5f,-0.5f);
    vertexBuffer[0].setPosition(3,0.5f,0.5f,-0.5f);

    vertexBuffer[0].setTexcoord(0,0.0,0.0);
    vertexBuffer[0].setTexcoord(1,1.0f,0.0);
    vertexBuffer[0].setTexcoord(2,0.0,1.0f);
    vertexBuffer[0].setTexcoord(3,1.0f,1.0f);
};
void SkyBox::initBack(const char*imageDir){
    sShader[1].init("Res/skybox.vs","Res/skybox.fs");
    char temp[256] ;
    memset(temp,0,256);
    strcpy(temp,imageDir);
    strcat(temp,"back.bmp");
    sShader[1].setTexture("U_Texture",temp);

    vertexBuffer[1].setSize(4);
    vertexBuffer[1].setPosition(0,0.5f,-0.5f,0.5f);
    vertexBuffer[1].setPosition(1,-0.5f,-0.5f,0.5f);
    vertexBuffer[1].setPosition(2,-0.5f,0.5f,0.5f);
    vertexBuffer[1].setPosition(3,0.5f,0.5f,0.5f);

    vertexBuffer[1].setTexcoord(0,0.0,0.0);
    vertexBuffer[1].setTexcoord(1,1.0f,0.0);
    vertexBuffer[1].setTexcoord(2,0.0,1.0f);
    vertexBuffer[1].setTexcoord(3,1.0f,1.0f);
};
void SkyBox::initLeft(const char*imageDir){
    sShader[2].init("Res/skybox.vs","Res/skybox.fs");
    char temp[256] ;
    memset(temp,0,256);
    strcpy(temp,imageDir);
    strcat(temp,"left.bmp");
    sShader[2].setTexture("U_Texture",temp);

    vertexBuffer[2].setSize(4);
    vertexBuffer[2].setPosition(0,-0.5f,-0.5f,0.5f);
    vertexBuffer[2].setPosition(1,-0.5f,-0.5f,-0.5f);
    vertexBuffer[2].setPosition(2,-0.5f,0.5f,0.5f);
    vertexBuffer[2].setPosition(3,-0.5f,0.5f,-0.5f);

    vertexBuffer[2].setTexcoord(0,0.0,0.0);
    vertexBuffer[2].setTexcoord(1,1.0f,0.0);
    vertexBuffer[2].setTexcoord(2,0.0,1.0f);
    vertexBuffer[2].setTexcoord(3,1.0f,1.0f);

};
void SkyBox::initRight(const char*imageDir){
    sShader[3].init("Res/skybox.vs","Res/skybox.fs");
    char temp[256] ;
    memset(temp,0,256);
    strcpy(temp,imageDir);
    strcat(temp,"right.bmp");
    sShader[1].setTexture("U_Texture",temp);

    vertexBuffer[3].setSize(4);
    vertexBuffer[3].setPosition(0,0.5f,-0.5f,-0.5f);
    vertexBuffer[3].setPosition(1,0.5f,-0.5f,0.5f);
    vertexBuffer[3].setPosition(2,0.5f,0.5f,-0.5f);
    vertexBuffer[3].setPosition(3,0.5f,0.5f,0.5f);

    vertexBuffer[3].setTexcoord(0,0.0,0.0);
    vertexBuffer[3].setTexcoord(1,1.0f,0.0);
    vertexBuffer[3].setTexcoord(2,0.0,1.0f);
    vertexBuffer[3].setTexcoord(3,1.0f,1.0f);
};
void SkyBox::initTop(const char*imageDir){
    sShader[4].init("Res/skybox.vs","Res/skybox.fs");
    char temp[256] ;
    memset(temp,0,256);
    strcpy(temp,imageDir);
    strcat(temp,"top.bmp");
    sShader[4].setTexture("U_Texture",temp);

    vertexBuffer[4].setSize(4);
    vertexBuffer[4].setPosition(0,-0.5f,0.5f,-0.5f);
    vertexBuffer[4].setPosition(1,0.5f,0.5f,-0.5f);
    vertexBuffer[4].setPosition(2,-0.5f,0.5f,0.5f);
    vertexBuffer[4].setPosition(3,0.5f,0.5f,0.5f);

    vertexBuffer[4].setTexcoord(0,0.0,0.0);
    vertexBuffer[4].setTexcoord(1,1.0f,0.0);
    vertexBuffer[4].setTexcoord(2,0.0,1.0f);
    vertexBuffer[4].setTexcoord(3,1.0f,1.0f);
};
void SkyBox::initBottom(const char*imageDir){
    sShader[5].init("Res/skybox.vs","Res/skybox.fs");
    char temp[256] ;
    memset(temp,0,256);
    strcpy(temp,imageDir);
    strcat(temp,"bottom.bmp");
    sShader[5].setTexture("U_Texture",temp);

    vertexBuffer[5].setSize(4);
    vertexBuffer[5].setPosition(0,-0.5f,-0.5f,0.5f);
    vertexBuffer[5].setPosition(1,0.5f,-0.5f,0.5f);
    vertexBuffer[5].setPosition(2,-0.5f,-0.5f,-0.5f);
    vertexBuffer[5].setPosition(3,0.5f,-0.5f,-0.5f);

    vertexBuffer[5].setTexcoord(0,0.0,0.0);
    vertexBuffer[5].setTexcoord(1,1.0f,0.0);
    vertexBuffer[5].setTexcoord(2,0.0,1.0f);
    vertexBuffer[5].setTexcoord(3,1.0f,1.0f);
};

void SkyBox::draw(glm::mat4 &ViewMatrix, glm::mat4 &ProjctionMatrix, float x, float y, float z) {
    //需要禁用深度测试
    glDisable(GL_DEPTH_TEST);
    //这样确保，天空盒子是跟摄像机跑的
    mModelMatrix = glm::translate(x,y,z);
    for (int i = 0; i < 6; ++i) {
        vertexBuffer[i].bind();
        sShader[i].bind(glm::value_ptr(mModelMatrix),glm::value_ptr(ViewMatrix),glm::value_ptr(ProjctionMatrix));
        glDrawArrays(GL_TRIANGLE_STRIP,0,4);
        vertexBuffer[i].unBind();
    }
}
