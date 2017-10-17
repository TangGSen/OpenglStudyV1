//
// Created by Administrator on 2017/10/16.
//

#ifndef OPENGLSTUDYV1_PARTICLESYSTEM_H
#define OPENGLSTUDYV1_PARTICLESYSTEM_H


#include <vertexbuffer.h>
#include <shader.h>

class ParticleSystem {
public:
    VertexBuffer *vertexBuffer;
    glm::mat4 mModelMatrix;
    SShader *mShader;
public:
    //粒子放在世界坐标的
    void init(float x,float y,float z);
    void draw(glm::mat4 viewMatrix, glm::mat4 projectionMatrix);

};


#endif //OPENGLSTUDYV1_PARTICLESYSTEM_H
