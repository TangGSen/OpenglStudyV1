package sen.com.renderer.poll;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/10/3 10:12
 * Des    :
 */

import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;
import sen.com.renderer.AbsSPointRenderer;

/**
 * 颜色立方体
 */
public class LColorCudeRenderer extends AbsSPointRenderer {

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //清平色
        gl.glClearColor(0f, 0f, 0f, 1f);
        //启用顶点缓冲区数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //颜色缓冲区
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    /**
     * 2.
     */
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视口
        gl.glViewport(0, 0, width, height);
        ritio = (float) width / (float) height;
        //投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //加载单位矩阵
        gl.glLoadIdentity();
        //设置平截头体
        gl.glFrustumf(-ritio, ritio, -1, 1, 3f, 7f);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glColor4f(0f, 0f, 1f, 1f);

        //模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        //旋转
        gl.glRotatef(xroate, 1, 0, 0);
        gl.glRotatef(yroate, 0, 1, 0);

        float r = 0.4f;
        //定义点坐标
        float[] coords = {
                -r, r, r, //front left up
                -r, -r, r,//front let bottom
                r, r, r,  //front right up
                r, -r, r, //front right bottom

                -r, r, -r, //back left up
                -r, -r, -r,//back let bottom
                r, r, -r,  //back right up
                r, -r, -r, //back right bottom
        };

        //顶点的索引顺序
        byte[] indices = {
                0, 1, 2, 2, 1, 3,//front
                4, 5, 6, 6, 5, 7,//back
                0, 1, 4, 4, 1, 5,//left
                2, 3, 6, 6, 3, 7,//right
                4, 0, 2, 4, 2, 6,//top
                5, 1, 3, 5, 3, 7//bottom
        };

        //颜色
//        float[] colors = {
//                0f, 1f, 1f, 1f,//青色
//                0, 1, 0, 1,
//                1, 1, 1, 1,//白色
//                1, 1, 0, 1,//黄色
//                0, 0, 1, 1,//4
//                0, 0, 0, 1,//5
//                1, 0, 1, 1,//6
//                1, 0, 0, 1//7
//        };

        float[] colors ={
                0.2f, 0.3f, 0.2f, 1f,
                0.3f, 0.4f, 0.3f, 1f,
                0.4f, 0.5f, 0.5f, 1f,
                0.5f, 0.6f, 0.6f, 1f,
                0.6f, 0.7f, 0.7f, 1f,
                0.7f, 0.8f, 0.8f, 1f,
                0.8f, 0.9f, 0.9f, 1f,
                0.9f, 1f, 1f, 1f,
        };

        gl.glColorPointer(4, GL10.GL_FLOAT, 0, ByteBufferUtils.arry2Byte(colors));
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ByteBufferUtils.arry2Byte(coords));
        //使用顶点索引方式绘制
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, ByteBufferUtils.arry2ByteBuffer(indices));
    }
}
