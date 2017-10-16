//
// Created by Administrator on 2017/10/15.
//

#ifndef OPENGLSTUDYV1_SKYBOX_H
#define OPENGLSTUDYV1_SKYBOX_H
#include <vertexbuffer.h>
#include "shader.h"

class SkyBox{
    VertexBuffer *vertexBuffer;
    SShader *sShader;
    glm::mat4 mModelMatrix;
public :
    void init(const char *imageDir);
    void initFront(const char*imageDir);
    void initBack(const char*imageDir);
    void initLeft(const char*imageDir);
    void initRight(const char*imageDir);
    void initTop(const char*imageDir);
    void initBottom(const char*imageDir);
    //x y z 表示当前摄像机的位置,让他永远套在摄像机的头上
    void draw(glm::mat4 &ViewMatrix,glm::mat4 &ProjctionMatrix, float x, float y, float z);
};


#endif //OPENGLSTUDYV1_SKYBOX_H
