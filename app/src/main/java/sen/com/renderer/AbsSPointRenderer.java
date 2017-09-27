package sen.com.renderer;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 基于有些方法设置相同，抽象出来
 */

public abstract class AbsSPointRenderer implements GLSurfaceView.Renderer{
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  List<Float> points;
    public float ritio;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e("sensen","AbsSPointRenderer->onSurfaceCreated"+Thread.currentThread().getName());
//        gl.glClearColor(0f,0f,0f,1f);
        gl.glClearColor(1f,1f,1f,1f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e("sensen","AbsSPointRenderer->onSurfaceChanged"+Thread.currentThread().getName());
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //为了视口和平截头体不发生拉伸状态,保持宽高比
        ritio = (float)width/(float)height;
        gl.glFrustumf(-ritio, ritio,-1f,1f,3f,7f);
    }

    @Override
    public abstract void onDrawFrame(GL10 gl) ;
}
