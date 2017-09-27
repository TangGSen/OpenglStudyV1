package sen.com.renderer.cut;

import android.opengl.GLU;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;
import sen.com.renderer.AbsSPointRenderer;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:54
 * Des    : 裁剪
 */

public class SCutRenderer extends AbsSPointRenderer {
    private int width;
    private int height;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        this.width = width;
        this.height =height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除颜色缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //加载模型矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        //旋转x 轴
        gl.glRotatef(xroate,1f,0,0f);
        gl.glRotatef(yroate,0,1f,0f);
        gl.glRotatef(zroate,0f,0,1f);
        /**
         * eye xyz 放置眼球的位置
         * center xyz 眼球的观察点
         * up xyz 指定眼球的向上向量
         */
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        //设置剪裁
        gl.glEnable(GL10.GL_SCISSOR_TEST);
        //满屏输出
        float[] coords = {
                -ritio, 1f, 2f,
                -ritio, -1, 2f,
                ritio, 1, 2f,
                ritio, -1, 2f
        };

        float[][] colors = {
                {0.2f, 0.2f, 0.2f, 1f},
                {0.3f, 0.3f, 0.3f, 1f},
                {0.4f, 0.4f, 0.4f, 1f},
                {0.5f, 0.5f, 0.5f, 1f},
                {0.6f, 0.6f, 0.6f, 1f},
                {0.7f, 0.7f, 0.7f, 1f},
                {0.2f, 0.2f, 0.2f, 1f},
                {0.3f, 0.3f, 0.3f, 1f},
                {0.4f, 0.4f, 0.4f, 1f},
                {0.5f, 0.5f, 0.5f, 1f},
                {0.6f, 0.6f, 0.6f, 1f},
                {0.7f, 0.7f, 0.7f, 1f},
                {0.2f, 0.2f, 0.2f, 1f},
                {0.3f, 0.3f, 0.3f, 1f},
                {0.4f, 0.4f, 0.4f, 1f},
                {0.5f, 0.5f, 0.5f, 1f},
                {0.6f, 0.6f, 0.6f, 1f},
                {0.7f, 0.7f, 0.7f, 1f}
        };
        ByteBuffer byteBuffer = ByteBufferUtils.arry2Byte(coords);
        int step = 20;//递减20像素
        //指定三角形颜色
        for (int i = 0; i < colors.length; i++) {
            gl.glScissor(i*step,i*step,width-(i*step*2),height-(i*step*2));
            gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, byteBuffer);
            //绘制
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.length / 3);
        }


    }
}
