//
// Created by Administrator on 2017/10/16.
//

#include "particlesystem.h"
#include "utils.h"
void ParticleSystem::init(float x,float y,float z){
    mModelMatrix = glm::translate(x,y,z);
    vertexBuffer = new VertexBuffer;
    vertexBuffer->setSize(1);
    vertexBuffer->setColor(0,0.1f,0.4f,0.6f);
    mShader =new SShader;
    mShader->init("Res/particlesystem.vs","Res/particlesystem.fs");
    mShader->setTexture("U_Texture",createProcedureTexture(128));
}

void ParticleSystem::draw(glm::mat4 &viewMatrix, glm::mat4 &projectionMatrix) {
    //不要产生深度测试，因为这里的粒子需要透明的，当然有时需要不透明的，那就启用深度测试吧
    glDisable(GL_DEPTH_TEST);
    //http://www.cppblog.com/wc250en007/archive/2012/07/18/184088.html
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA,GL_ONE);
    vertexBuffer->bind();
    mShader->bind(glm::value_ptr(mModelMatrix),glm::value_ptr(viewMatrix),glm::value_ptr(projectionMatrix));
    glDrawArrays(GL_POINTS,0,vertexBuffer->mVertexCount);
    vertexBuffer->unBind();
    glDisable(GL_BLEND);
}
