//
// Created by Administrator on 2017/10/11.
//

#include "scence.h"
#include "utils.h"

GLuint vbo ,ebo;
GLuint glProgram;
GLint positionLocation, ModelMatrixLocation, ViewMatrixLocation,
        ProjectionMatrixLocation,colorLocation,textcoordLocation;
glm::mat4 ModelMatrix,ViewMatrix, ProjectionMatrix;
GLuint texture;
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
            //每一行前4位为position ,后四位为color
            -0.2f, -0.2f, 0.0f, 1.0f,0.8f,0.0f,0.0f,1.0f,0.0f,0.0f,
            0.2f, -0.2f, 0.0f, 1.0f,0.0f,0.8f,0.0f,1.0f,1.0f,0.0f,
            0.0f, 0.2f, 0.0f, 1.0f,0.0f,0.0f,0.8f,1.0f,0.5f,1.0f
    };
    //将数据从cpu传到gpu 的步骤

    glGenBuffers(1, &vbo);
   //vbo
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * sizeof(data), data, GL_STATIC_DRAW);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    //ebo
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
    textcoordLocation = glGetAttribLocation(glProgram, "textcoord");
    ModelMatrixLocation = glGetUniformLocation(glProgram, "ModelMatrix");
    ViewMatrixLocation = glGetUniformLocation(glProgram, "ViewMatrix");
    ProjectionMatrixLocation = glGetUniformLocation(glProgram, "ProjectionMatrix");
    ModelMatrix = glm::translate(0.0f,0.0f,-0.6f);
    texture = crateTexture2dFromBmp("Res/test.bmp");

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

//    glVertexAttribPointer(positionLocation,4,GL_FLOAT,GL_FALSE, sizeof(float)*4,0);
    glVertexAttribPointer(positionLocation,4,GL_FLOAT,GL_FALSE, sizeof(float)*10,0);

    //启动colorLocation
    glEnableVertexAttribArray(colorLocation);
    glVertexAttribPointer(colorLocation, 4, GL_FLOAT, GL_FALSE,sizeof(float)*10,
                          (const void *) (sizeof(float) * 4));
    ////贴图
    glBindTexture(GL_TEXTURE_2D,texture);
    ////对应纹理第一层
    glUniform1i(textcoordLocation,0);
    //启用textcoordLocation 这个插槽
    glEnableVertexAttribArray(textcoordLocation);
    glVertexAttribPointer(textcoordLocation, 4, GL_FLOAT, GL_FALSE,sizeof(float)*10,
                          (const void *) (sizeof(float) * 8));

    //将这个注释
//    glDrawArrays(GL_TRIANGLES,0,3);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ebo);
    glDrawElements(GL_TRIANGLES, 3,GL_UNSIGNED_SHORT,0);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
//    良好习惯
    glBindBuffer(GL_ARRAY_BUFFER,0);


   //良好习惯，当绘制完毕后，将程序置为0 号程序
    glUseProgram(0);
}
