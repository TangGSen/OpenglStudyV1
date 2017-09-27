package sen.com.renderer.lines;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 绘制点，螺旋线,线带，创建的点越密，越平滑
 *          依次连线，但是不闭合
 */

public class SPointRendererV5 implements GLSurfaceView.Renderer{
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  List<Float> points;

    public SPointRendererV5(){
        //计算点的坐标
        float r = 0.5f;//半径
        points = new ArrayList<>();
        float x=0f,y=0f,z=1f;
        int rSize = 6;//6个螺旋
        float zstep =0.01f;//每个螺旋z轴坐标向后移动
        for (float alpha = 0f;alpha<Math.PI *rSize;alpha= (float) (alpha+Math.PI/32)){
            x = (float) (r*Math.cos(alpha));
            y = (float) (r*Math.sin(alpha));
            z = z -zstep;
            points.add(x);
            points.add(y);
            points.add(z);
        }
        byteBuffer = ByteBufferUtils.list2Byte(points);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e("sensen","SPointRenderer->onSurfaceCreated"+Thread.currentThread().getName());
        gl.glClearColor(0f,0f,0f,1f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e("sensen","SPointRenderer->onSurfaceChanged"+Thread.currentThread().getName());
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //为了视口和平截头体不发生拉伸状态,保持宽高比
        float ritio = (float)width/(float)height;
        gl.glFrustumf(-ritio,ritio,-1f,1f,3f,7f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("sensen","SPointRenderer->onDrawFrame"+Thread.currentThread().getName());
        long starTime = System.currentTimeMillis();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //设置绘图颜色
        gl.glColor4f(1f,1f,1f,1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0f,0f,5f,0f,0f,0f,0f,1f,0f);
        //旋转x 轴
        gl.glRotatef(xroate,1f,0,0f);
        gl.glRotatef(yroate,0,1f,0f);
        gl.glRotatef(zroate,0f,0,1f);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0,byteBuffer);
        gl.glDrawArrays(GL10.GL_LINE_STRIP,0,points.size()/3);
        long useTime = System.currentTimeMillis()-starTime;
        Log.e("sensen","onDrawFrame useTime"+useTime);
    }
}
