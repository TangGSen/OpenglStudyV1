//
// Created by Administrator on 2017/10/12.
//

#include <utils.h>
#include "ground.h"
/*void Ground::init() {
    Vertex vertex[1600]; //表示地形有1600个顶点
    for (int z= 0; z< 20; ++z) {
        float zStart = 100.f- z*10.0f;
        for (int x = 0; x < 20; ++x) {
            int offset =(x+z*20)*4;
            float xStart = x*10.0f-100f;
            vertex[offset].position[0] =xStart;
            vertex[offset].position[1] =-1.0f;
            vertex[offset].position[2] =zStart;
            vertex[offset].position[3] =1.0f;

            vertex[offset+1].position[0] =xStart+10.0f;
            vertex[offset+1].position[1] =-1.0f;
            vertex[offset+1].position[2] =zStart;
            vertex[offset+1].position[3] =1.0f;

            vertex[offset+2].position[0] =xStart;
            vertex[offset+2].position[1] =-1.0f;
            vertex[offset+2].position[2] =zStart-10.0f;
            vertex[offset+2].position[3] =1.0f;


            vertex[offset+2].position[0] =xStart+10.0f;
            vertex[offset+2].position[1] =-1.0f;
            vertex[offset+2].position[2] =zStart-10.0f;
            vertex[offset+2].position[3] =1.0f;

        }
    }
    vbo = createBufferObj(GL_ARRAY_BUFFER, sizeof(Vertex)* sizeof(vertex),GL_STATIC_DRAW,vertex);
}*/

void Ground::init() {
    vertexBuffer = new VertexBuffer;
    vertexBuffer->setSize(1600);
    for (int z= 0; z< 20; ++z) {
        float zStart = 100.f- z*10.0f;
        for (int x = 0; x < 20; ++x) {
            int offset =(x+z*20)*4;
            float xStart = x*10.0f-100f;
            vertexBuffer->setPosition(offset,xStart,-1.0f,zStart);
            vertexBuffer->setPosition(offset+1,xStart+10.0f,-1.0f,zStart);
            vertexBuffer->setPosition(offset+2,xStart,-1.0f,zStart);
            vertexBuffer->setPosition(offset+3,xStart+10.0f,-1.0f,zStart-10.0f);
            vertexBuffer->setNarmal(offset,0.0f,1.0f,0.0f);
            vertexBuffer->setNarmal(offset+1,0.0f,1.0f,0.0f);
            vertexBuffer->setNarmal(offset+2,0.0f,1.0f,0.0f);
            vertexBuffer->setNarmal(offset+3,0.0f,1.0f,0.0f);

            if((z%2)^(x%2)){
                vertexBuffer->setColor(offset,0.3f,0.3f,0.3f,1.0f);
                vertexBuffer->setColor(offset+1,0.3f,0.3f,0.3f,1.0f);
                vertexBuffer->setColor(offset+2,0.3f,0.3f,0.3f,1.0f);
                vertexBuffer->setColor(offset+3,0.3f,0.3f,0.3f,1.0f);
            }else{
                vertexBuffer->setColor(offset,0.7f,0.7f,0.7f,1.0f);
                vertexBuffer->setColor(offset+1,0.7f,0.7f,0.7f,1.0f);
                vertexBuffer->setColor(offset+2,0.7f,0.7f,0.7f,1.0f);
                vertexBuffer->setColor(offset+3,0.7f,0.7f,0.7f,1.0f);
            }
        }
    }
    vbo = createBufferObj(GL_ARRAY_BUFFER, sizeof(Vertex)*vertexBuffer->mVertexCount ,GL_STATIC_DRAW,vertexBuffer->mVertexes);
    //加载shader
    int fileSize = 0;
    unsigned char* groundShader = loadFile("Res/ground.vs",fileSize);
    GLuint groundVS = complieShader(GL_VERTEX_SHADER, (const char *) groundShader);
    delete groundShader;
    groundShader = loadFile("Res/ground.fs",fileSize);
    GLuint groundFS = complieShader(GL_FRAGMENT_SHADER, (const char *) groundShader);
    delete groundShader;
    mProgram = createProgram(groundVS,groundFS);
    glDeleteShader(groundVS);
    glDeleteShader(groundFS);

    //从shader 读取属性
    positionLocation = glGetAttribLocation(mProgram,"poistion");
    colorLocation = glGetAttribLocation(mProgram,"color");
    normalLocation = glGetAttribLocation(mProgram,"normal");
    ProjectionMatrixLocation = glGetUniformLocation(mProgram,"ProjectionMatrix");
    ViewMatrixLocation = glGetUniformLocation(mProgram,"ViewMatrix");
    ModelMatrixLocation = glGetUniformLocation(mProgram,"ModelMatrix");
}