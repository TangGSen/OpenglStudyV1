//
// Created by Administrator on 2017/10/11.
//这个版本还没加入颜色，如果需要加入的话就得修改一些代码，这个在学习过程中保留第一个版本
//

#include "scence.h"
#include "utils.h"

GLuint vbo ,ebo;
GLuint glProgram;
GLint positionLocation, ModelMatrixLocation, ViewMatrixLocation, ProjectionMatrixLocation,colorLocation;
glm::mat4 ModelMatrix,ViewMatrix, ProjectionMatrix;
void init() {
    //这里写的数据都是在cpu
//    float data[] = {
//            -0.2f, -0.2f, -0.6f, 1.0f,
//            0.2f, -0.2f, -0.6f, 1.0f,
//            0.0f, 0.2f, -0.6f, 1.0f
//    };
    //按照我们指定的顺序来画
    unsigned short indexs[]= {0,1,2};

//ModelMatrix = glm::translate(0.0f,0.0f,-0.6f); 跟上面一样效果
    float data[] = {
            -0.2f, -0.2f, 0.0f, 1.0f,
            0.2f, -0.2f, 0.0f, 1.0f,
            0.0f, 0.2f, 0.0f, 1.0f,
    };
    //将数据从cpu传到gpu 的步骤
    /**
     * 通过顶点缓冲对象(Vertex Buffer Objects, VBO)管理这个内存，它会在GPU内存(通常被称为显存)中储存大批顶点。
     * 使用这些缓冲对象的好处是我们可以一次性的发送一大批数据到显卡上，而不是每个顶点发送一次。
     * 就像OpenGL中的其他对象一样，这个缓冲有一个独一无二的ID，所以我们可以使用glGenBuffers函数生成一个缓冲ID：
     *
     * 1代表只需要一个vbo
     */
    glGenBuffers(1, &vbo);
    /**
     * OpenGL有很多缓冲对象类型，GL_ARRAY_BUFFER是其中一个顶点缓冲对象的缓冲类型。OpenGL允许同时绑定多个缓冲，
     * 只要它们是不同的缓冲类型。可以使用glBindBuffer函数把新创建的缓冲绑定到GL_ARRAY_BUFFER上
     */
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    /**
     * 使用的任何缓冲函数(在GL_ARRAY_BUFFER目标上)都会用来配置当前绑定的缓冲(VBO)。
     * 然后调用glBufferData函数，它会把之前定义的顶点数据复制到缓冲的内存中
     *
     *
     * glBufferData是一个用来把用户定的义数据复制到当前绑定缓冲的函数。
     * 它的第一个参数是我们希望把数据复制到上面的缓冲类型：
     * 顶点缓冲对象当前绑定到GL_ARRAY_BUFFER目标上
     * 。第二个参数指定我们希望传递给缓冲的数据的大小(以字节为单位)；用一个简单的sizeof计算出顶点数据就行。
     * 第三个参数是我们希望发送的真实数据（的指针）。
     * 第四个参数指定了我们希望显卡如何管理给定的数据。有三种形式：
     * GL_STATIC_DRAW ：数据不会或几乎不会改变。
     * GL_DYNAMIC_DRAW：数据会被改变很多。
     * GL_STREAM_DRAW ：数据每次绘制时都会改变。
     * 三角形的位置数据不会改变，每次渲染调用时都保持原样，所以它使用的类型最好是GL_STATIC_DRAW。
     * 如果，比如，一个缓冲中的数据将频繁被改变，那么使用的类型就是GL_DYNAMIC_DRAW或GL_STREAM_DRAW。
     * 这样就能确保图形卡把数据放在高速写入的内存部分。
     */
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * sizeof(data), data, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, 0);

    glGenBuffers(1, &ebo);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof( unsigned short) * sizeof(indexs), indexs, GL_STATIC_DRAW);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);


    int fileSize = 0;
    //加载文件和编译shader
    unsigned char *vsShaderCode = loadFile("Res/test.vs", fileSize);
    unsigned char *fsShaderCode = loadFile("Res/test.fs", fileSize);

    GLuint vsShader = complieShader(GL_VERTEX_SHADER, (const char *) vsShaderCode);
    GLuint fsShader = complieShader(GL_FRAGMENT_SHADER, (const char *) fsShaderCode);
    //用完可以删了
    delete vsShaderCode;
    delete fsShaderCode;
    glProgram = createProgram(vsShader, fsShader);
    glDeleteShader(vsShader);
    glDeleteShader(fsShader);

    //获取shader 中的变量
    positionLocation = glGetAttribLocation(glProgram, "position");
    colorLocation = glGetAttribLocation(glProgram, "color");
    ModelMatrixLocation = glGetUniformLocation(glProgram, "ModelMatrix");
    ViewMatrixLocation = glGetUniformLocation(glProgram, "ViewMatrix");
    ProjectionMatrixLocation = glGetUniformLocation(glProgram, "ProjectionMatrix");
    ModelMatrix = glm::translate(0.0f,0.0f,-0.6f);

}

void setViewPortSize(float width, float height) {
    /**
     * 1.视角
     * 2.宽高比
     * 3.最近看到的距离
     * 4.最远看到的距离
     */
    ProjectionMatrix = glm::perspective(60.0f,width/height,0.1f,1000.0f);
    //其他两个没设置就是单位矩阵
}

//绘制
void draw_v1() {
    float time = getFrameTime();
    LOGE("time %f",time);
    glClearColor(0.0f,0.0f,0.6f,1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    //选一个程序来执行
    glUseProgram(glProgram);
    //设置mvp 矩阵
   // 给uniform赋值时使用的是glUniformMatrix4fv
//    将矩阵传递到Shader中。它的参数分别为：下标（插槽）位置，矩阵数量，是否进行转置，矩阵
    glUniformMatrix4fv(ModelMatrixLocation,1,GL_FALSE,glm::value_ptr(ModelMatrix));
    glUniformMatrix4fv(ProjectionMatrixLocation,1,GL_FALSE,glm::value_ptr(ProjectionMatrix));
    glUniformMatrix4fv(ViewMatrixLocation,1,GL_FALSE,glm::value_ptr(ViewMatrix));

    glBindBuffer(GL_ARRAY_BUFFER,vbo);
    //启用positionLocation 这个插槽
    glEnableVertexAttribArray(positionLocation);
    /**
     * 1. positionLocation
     * 2.positionLocation 这个插槽中有几个分量，xyzw 4
     * 3.GL_FLOAT 顶点的类型，
     * 4.是否需要映射，（假如不是传来的是float，如果false 不用转换，true就需要）这里就是float类型不需要
     * 5.在内存中两点之间起始内存地址的距离
     * 6从零开始，是指从vbo 什么地方开始取值
     */

    glVertexAttribPointer(positionLocation,4,GL_FLOAT,GL_FALSE, sizeof(float)*4,0);
    glDrawArrays(GL_TRIANGLES,0,3);
//    良好习惯
    glBindBuffer(GL_ARRAY_BUFFER,0);
   //良好习惯，当绘制完毕后，将程序置为0 号程序
    glUseProgram(0);
}

//绘制   使用ElementBuffer 来指定顶点顺序来绘制
void draw() {
    float time = getFrameTime();
    LOGE("time %f",time);
    glClearColor(0.6f,0.0f,0.6f,1.0f);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    //选一个程序来执行
    glUseProgram(glProgram);
    glUniformMatrix4fv(ModelMatrixLocation,1,GL_FALSE,glm::value_ptr(ModelMatrix));
    glUniformMatrix4fv(ProjectionMatrixLocation,1,GL_FALSE,glm::value_ptr(ProjectionMatrix));
    glUniformMatrix4fv(ViewMatrixLocation,1,GL_FALSE,glm::value_ptr(ViewMatrix));

    glBindBuffer(GL_ARRAY_BUFFER,vbo);
    //启用positionLocation 这个插槽
    glEnableVertexAttribArray(positionLocation);

    glVertexAttribPointer(positionLocation,4,GL_FLOAT,GL_FALSE, sizeof(float)*4,0);
    //将这个注释
//    glDrawArrays(GL_TRIANGLES,0,3);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
    glDrawElements(GL_TRIANGLES, 3,GL_UNSIGNED_SHORT,0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
//    良好习惯
    glBindBuffer(GL_ARRAY_BUFFER,0);


    //启动colorLocation
    glEnableVertexAttribArray(colorLocation);
    
   //良好习惯，当绘制完毕后，将程序置为0 号程序
    glUseProgram(0);
}
