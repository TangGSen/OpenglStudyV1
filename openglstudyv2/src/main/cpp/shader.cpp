//
// Created by Administrator on 2017/10/12.
//
#include "shader.h"

void SShader::init(const char *vsPath, const char *fsPath) {
    int fileSize = 0;
    unsigned char *shaderCode = loadFile(vsPath, fileSize);
    GLuint vsShader = complieShader(GL_VERTEX_SHADER, (const char *) shaderCode);
    delete shaderCode;
    if (vsShader == 0)
        return;

    shaderCode = loadFile(fsPath, fileSize);
    GLuint fsShader = complieShader(GL_FRAGMENT_SHADER, (const char *) shaderCode);
    delete shaderCode;
    if (fsShader == 0)
        return;
    mProgram = createProgram(vsShader,fsShader);
    glDeleteShader(vsShader);
    glDeleteShader(fsShader);

    if (mProgram==0)
        return;
    //从shader 读取属性
    positionLocation = glGetAttribLocation(mProgram,"poistion");
    colorLocation = glGetAttribLocation(mProgram,"color");
    normalLocation = glGetAttribLocation(mProgram,"normal");
    projectionMatrixLocation = glGetUniformLocation(mProgram,"ProjectionMatrix");
    viewMatrixLocation = glGetUniformLocation(mProgram,"ViewMatrix");
    modelMatrixLocation = glGetUniformLocation(mProgram,"ModelMatrix");

}

void SShader::bind(float *M, float *V, float *P) {
    glUseProgram(mProgram);
    glUniformMatrix4fv(modelMatrixLocation,1,GL_FALSE,M);
    glUniformMatrix4fv(viewMatrixLocation,1,GL_FALSE,V);
    glUniformMatrix4fv(projectionMatrixLocation,1,GL_FALSE,P);

    //启动
    glEnableVertexAttribArray(positionLocation);
    glVertexAttribPointer(positionLocation,4,GL_FLOAT,GL_FALSE, sizeof(Vertex),0);

    glEnableVertexAttribArray(colorLocation);
    glVertexAttribPointer(colorLocation, 4, GL_FLOAT, GL_FALSE, sizeof(Vertex),
                          (const void *) (sizeof(float) * 4));

    glEnableVertexAttribArray(texcoordLocation);
    glVertexAttribPointer(texcoordLocation, 4, GL_FLOAT, GL_FALSE, sizeof(Vertex),
                          (const void *) (sizeof(float) * 8));

    glEnableVertexAttribArray(normalLocation);
    glVertexAttribPointer(normalLocation, 4, GL_FLOAT, GL_FALSE, sizeof(Vertex),
                          (const void *) (sizeof(float) * 12));

}
