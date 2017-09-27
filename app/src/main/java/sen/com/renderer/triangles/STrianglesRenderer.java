package sen.com.renderer.triangles;

import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;
import sen.com.renderer.AbsSPointRenderer;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 通过三角形带，画正方形
 */

public class STrianglesRenderer extends AbsSPointRenderer {
    public float xroate;
    public float yroate;
    public float zroate;
    private final ByteBuffer byteBuffer;
    private final float[] triangles;

    public STrianglesRenderer() {
        //计算点的坐标
        float r = 0.5f;//半径

        /*这段代码需要优化ByteBufferUtils.arry2Byte 应该缓存起来*/
        int index = 0;
        triangles = new float[]{
                -r,r,0,
                -r,-r,0,
                r,r,0,
                r,-r,0,
        };
        byteBuffer = ByteBufferUtils.arry2Byte(triangles);

    }


    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("sensen", "SPointRenderer->onDrawFrame" + Thread.currentThread().getName());
        long starTime = System.currentTimeMillis();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //设置绘图颜色
        gl.glColor4f(1f, 0f, 0f, 1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1f, 0f);
        //旋转x 轴
        gl.glRotatef(xroate, 1f, 0, 0f);
        gl.glRotatef(yroate, 0, 1f, 0f);
        gl.glRotatef(zroate, 0f, 0, 1f);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, byteBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, triangles.length/3);

        long useTime = System.currentTimeMillis() - starTime;
        Log.e("sensen", "onDrawFrame useTime" + useTime);
    }
}
