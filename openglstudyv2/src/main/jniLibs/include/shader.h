//
// Created by Administrator on 2017/10/12.
//
#include "ggl.h"
#include "utils.h"
#include "vertexbuffer.h"
#ifndef OPENGLSTUDYV1_SHADER_H
#define OPENGLSTUDYV1_SHADER_H


class SShader {
public:
    GLuint mProgram;
    GLint positionLocation, colorLocation, texcoordLocation, normalLocation;
    GLint projectionMatrixLocation, modelMatrixLocation, viewMatrixLocation;
public:
    void init(const char *vsPath, const char *fsPath);

    void bind(float *M, float *V, float *P);
};


#endif //OPENGLSTUDYV1_SHADER_H
