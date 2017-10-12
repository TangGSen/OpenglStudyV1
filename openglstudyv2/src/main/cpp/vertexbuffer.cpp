//
// Created by Administrator on 2017/10/12.
//
#include "vertexbuffer.h"
#include "utils.h"

void VertexBuffer::setSize(int vertexSize) {
    mVertexCount =vertexSize;
    //初始化
    mVertexes = new Vertex[mVertexCount];
    memset(mVertexes,0, sizeof(Vertex)*mVertexCount);
}

void VertexBuffer::setPosition(int index, float x, float y, float z, float w) {
    mVertexes[index].position[0] =x;
    mVertexes[index].position[1] =y;
    mVertexes[index].position[2] =z;
    mVertexes[index].position[3] =w;
}

void VertexBuffer::setColor(int index, float r, float g, float b, float a) {
    mVertexes[index].color[0] =r;
    mVertexes[index].color[1] =g;
    mVertexes[index].color[2] =b;
    mVertexes[index].color[3] =a;
}

void VertexBuffer::setTexcoord(int index, float x, float y) {
    mVertexes[index].texcoord[0] =x;
    mVertexes[index].texcoord[1] =y;
}

void VertexBuffer::setNarmal(int index, float x, float y,float z) {
    mVertexes[index].normal[0] =x;
    mVertexes[index].normal[1] =y;
    mVertexes[index].normal[2] =z;
}

