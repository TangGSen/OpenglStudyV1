package sen.com.renderer.lines;

import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;
import sen.com.renderer.AbsSPointRenderer;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 画线
 */

public class SLinesRendererV1 extends AbsSPointRenderer {
    public float xroate;
    public float yroate;
    public float zroate;
    private final ByteBuffer byteBuffer;
    private final List<Float> lines;

    public SLinesRendererV1() {
        //计算点的坐标
        float r = 0.5f;//半径
        float x = 0f, y = 0f, z = 0f;
        int rSize = 6;//6个螺旋
        float psize = 0.8f;
        float pstep = 0.05f;
        float zstep = 0.01f;//每个螺旋z轴坐标向后移动
        /*这段代码需要优化ByteBufferUtils.arry2Byte 应该缓存起来*/
        int index = 0;
        lines = new ArrayList<>();
        for (float alpha = 0f; alpha < Math.PI * rSize; alpha = (float) (alpha + Math.PI / 32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            //先添加原点
            lines.add(0f);
            lines.add(0f);
            lines.add(0f);
            //x,y,z
            lines.add(x);
            lines.add(y);
            lines.add(z);

        }
        byteBuffer = ByteBufferUtils.list2Byte(lines);

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
        gl.glDrawArrays(GL10.GL_LINES, 0, lines.size() / 3);

        long useTime = System.currentTimeMillis() - starTime;
        Log.e("sensen", "onDrawFrame useTime" + useTime);
    }
}
