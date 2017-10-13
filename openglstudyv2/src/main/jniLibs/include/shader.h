//
// Created by Administrator on 2017/10/12.
//
#include "ggl.h"
#include "utils.h"
#include "vertexbuffer.h"
#ifndef OPENGLSTUDYV1_SHADER_H
#define OPENGLSTUDYV1_SHADER_H

//贴图结构体
struct UniformTexture{
    GLint mLocation;
    GLuint mTexture;
    UniformTexture(){
        mLocation =-1;
        mTexture = 0;
    }
};
class SShader {
public:
    GLuint mProgram;
    UniformTexture uniformTexture;
    GLint positionLocation, colorLocation, texcoordLocation, normalLocation;
    GLint projectionMatrixLocation, modelMatrixLocation, viewMatrixLocation;
public:
    void init(const char *vsPath, const char *fsPath);

    void bind(float *M, float *V, float *P);
    //设置贴图
    void setTexture(const char* name , const char* imagePath);
};


#endif //OPENGLSTUDYV1_SHADER_H
