package sen.com.renderer.poll;

import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;
import sen.com.renderer.AbsSPointRenderer;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 组成正方体
 */

public class SCudeRenderer extends AbsSPointRenderer {
    private final ByteBuffer indexBuffer;
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  int pointsSize;
    private final byte[] index;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e("sensen","AbsSPointRenderer->onSurfaceChanged"+Thread.currentThread().getName());
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //为了视口和平截头体不发生拉伸状态,保持宽高比
        ritio = (float)width/(float)height;
        //正交投影
        gl.glOrthof(-ritio, ritio,-1f,1f,3f,7f);
    }

    public SCudeRenderer(){
        float r = 0.3f;
        float[] points = {
                -r,r,r,// front left up
                -r,-r ,r, // frontleft bottom
                r,r,r, //front fight up
                r,-r,r,//front right botoom

                -r,r,-r,//back,left,up
                -r,-r,-r,//back,left bottom
                r,r,-r,//back,right up
                r,-r,-r//back right bottom

        };

        //顶点索引顺序
        index = new byte[]{
                0,1,5,4,0,
                0,4,6,2,
                6,7,3,
                7,5,1,
                0,2,3,1
        };



        byteBuffer = ByteBufferUtils.arry2Byte(points);
        indexBuffer = ByteBufferUtils.arry2ByteBuffer(index);
        pointsSize = points.length;
    }



    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("sensen","SPointRenderer->onDrawFrame"+Thread.currentThread().getName());
        long starTime = System.currentTimeMillis();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //设置绘图颜色
        gl.glColor4f(1f,0f,0f,1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0f,0f,5f,0f,0f,0f,0f,1f,0f);
        //旋转x 轴
        gl.glRotatef(xroate,1f,0,0f);
        gl.glRotatef(yroate,0,1f,0f);
        gl.glRotatef(zroate,0f,0,1f);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,byteBuffer);
        gl.glDrawElements(GL10.GL_LINE_STRIP,index.length, GL10.GL_UNSIGNED_BYTE,indexBuffer);
        long useTime = System.currentTimeMillis()-starTime;
        Log.e("sensen","onDrawFrame useTime"+useTime);
    }
}
