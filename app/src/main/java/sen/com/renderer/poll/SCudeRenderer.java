package sen.com.renderer.poll;

import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv1.ByteBufferUtils;
import sen.com.renderer.AbsSPointRenderer;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 画三角形组成正方体
 */

public class SCudeRenderer extends AbsSPointRenderer {
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  int pointsSize;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
    }

    public SCudeRenderer(){


        //计算球的坐标
        float y0,y1,x0,x1,z0,z1;
        float beta = 0;

        List<Float> pollsPoints = new ArrayList<>();
        float Rinner = 0.2f ;//内环半径
        float Rring = 0.3f ;//环半径
        int count = 40 ;
        float alphaStep = (float) (2 * Math.PI / count) ;
        float alpha = 0 ;

        int count0 = 40 ;
        float betaStep = (float) (2 * Math.PI) / count0;
        for(int i = 0 ; i < count ; i ++){
            alpha = i * alphaStep ;
            for(int j = 0 ; j <= count0 ; j ++){
                beta = j * betaStep ;
                x0 = (float) (Math.cos(alpha) * (Rinner + Rring * (1 + Math.cos(beta))));
                y0 = (float) (Math.sin(alpha) * (Rinner + Rring * (1 + Math.cos(beta))));
                z0 = (float) (- Rring * Math.sin(beta));
                x1 = (float) (Math.cos(alpha + alphaStep) * (Rinner + Rring * (1 + Math.cos(beta))));
                y1 = (float) (Math.sin(alpha + alphaStep) * (Rinner + Rring * (1 + Math.cos(beta))));
                z1 = (float) (- Rring * Math.sin(beta));
                pollsPoints.add(x0);
                pollsPoints.add(y0);
                pollsPoints.add(z0);
                pollsPoints.add(x1);
                pollsPoints.add(y1);
                pollsPoints.add(z1);
            }
        }
        byteBuffer = ByteBufferUtils.list2Byte(pollsPoints);
        pointsSize = pollsPoints.size();
        pollsPoints.clear();
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
        gl.glDrawArrays(GL10.GL_LINE_STRIP,0,pointsSize/3);
        long useTime = System.currentTimeMillis()-starTime;
        Log.e("sensen","onDrawFrame useTime"+useTime);
    }
}
