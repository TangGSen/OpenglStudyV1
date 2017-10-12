package sen.com.renderer.light;

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
 * Des    : 环境光
 */

public class SLightColorCudeRenderer extends AbsSPointRenderer {
    private final ByteBuffer indexBuffer;
    private final ByteBuffer colorBuffer;
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  int pointsSize;
    private final byte[] index;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //启动光照，但是没指定光的rgb 值，物体是黑色或灰色
        gl.glEnable(GL10.GL_LIGHTING);

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

    public SLightColorCudeRenderer(){
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
//        index = new byte[]{
//                4,5,1,1,0,4, //left
//                4,5,7,7,6,4,//back
//                4,0,3,3,6,4,//up
//                5,1,2,2,7,5,//down
//                6,2,3,3,7,6,//right
//                0,1,3,3,2,0 //fornt
//        };

        //顶点的索引顺序
         index = new byte[]{
                0, 1, 2, 2, 1, 3,//front
                4, 5, 6, 6, 5, 7,//back
                0, 1, 4, 4, 1, 5,//left
                2, 3, 6, 6, 3, 7,//right
                4, 0, 2, 4, 2, 6,//top
                5, 1, 3, 5, 3, 7//bottom
        };



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




        colorBuffer = ByteBufferUtils.arry2Byte(colors);
        byteBuffer = ByteBufferUtils.arry2Byte(points);
        indexBuffer = ByteBufferUtils.arry2ByteBuffer(index);
        pointsSize = points.length;
    }



    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("sensen","SPointRenderer->onDrawFrame"+Thread.currentThread().getName());
        long starTime = System.currentTimeMillis();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT| GL10.GL_DEPTH_BUFFER_BIT);
        //设置绘图颜色
        gl.glColor4f(1f,0f,0f,1f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0f,0f,5f,0f,0f,0f,0f,1f,0f);
        //旋转x 轴
        gl.glRotatef(xroate,1f,0,0f);
        gl.glRotatef(yroate,0,1f,0f);
        gl.glRotatef(zroate,0f,0,1f);

        float[] parme ={1.0f*golbalRProgress,1.0f*golbalGProgress,1.0f*golbalBProgress,1f};
        //设置全局环境光
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT,ByteBufferUtils.arry2FloatBuffer(parme));

        float[] parmeMeatils ={1.0f*meatilRProgress,1.0f*meatilGProgress,1.0f*meatilBProgress,1f};
        //设置全局环境光
        gl.glLightfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT_AND_DIFFUSE ,ByteBufferUtils.arry2FloatBuffer(parmeMeatils));

        //设置颜色追踪
        if (enableColorMaterial){
            gl.glEnable(GL10.GL_COLOR_MATERIAL);
        }else{
            gl.glDisable(GL10.GL_COLOR_MATERIAL);
        }

        gl.glColorPointer(4, GL10.GL_FLOAT,0,colorBuffer);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,byteBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES,index.length, GL10.GL_UNSIGNED_BYTE,indexBuffer);
        long useTime = System.currentTimeMillis()-starTime;
        Log.e("sensen","onDrawFrame useTime"+useTime);
    }
}
