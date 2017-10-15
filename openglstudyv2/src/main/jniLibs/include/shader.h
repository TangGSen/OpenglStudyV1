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
//4个分量的结构体
struct UniformVec4{
    GLint mLocation;
    float data[4];
    UniformVec4(){
        mLocation =-1;
        memset(data,0, sizeof(float)*4);
    }
};
class SShader {
public:
    GLuint mProgram;
//    UniformTexture uniformTexture;
    std::map<std::string,UniformTexture *> uniformTextures;
    std::map<std::string,UniformVec4 *> uniformVec4s;
    GLint positionLocation, colorLocation, texcoordLocation, normalLocation;
    GLint projectionMatrixLocation, modelMatrixLocation, viewMatrixLocation;
public:
    void init(const char *vsPath, const char *fsPath);

    void bind(float *M, float *V, float *P);
    //设置贴图
    void setTexture(const char* name , const char* imagePath);

    //设置UniformVec4
    void setUiformVec4(const char* name , float x,float y, float z,float w);

};


#endif //OPENGLSTUDYV1_SHADER_H
