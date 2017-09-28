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
 * Des    : 画三角形组成球体
 */

public class SPollRenderer extends AbsSPointRenderer {
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  int pointsSize;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
    }

    public SPollRenderer(){


        //计算球的坐标
        float R = 0.8f;
        int statck = 100;//水平层数
        float stackStep = (float) (Math.PI/statck);//单位角度值
        int slice =100;//每层分12份
        float sliceStep = (float) (Math.PI/slice);//水平圆递增的角度
        float r0,r1,y0,y1,x0,x1,z0,z1;
        float alpha0,alpha1;
        float beta = 0;

        List<Float> pollsPoints = new ArrayList<>();
        //外层循环
        for (int i=0;i<statck;i++){
            //由于对称，下部分和上部分都是-90到90度之间值
            alpha0 = (float) (-Math.PI/2+i*stackStep);
            alpha1 = (float) (-Math.PI/2+(i+1)*stackStep);
            //每一层y 的值是固定的
            y0 = (float) (R* Math.sin(alpha0));
            y1 = (float) (R* Math.sin(alpha1));
            r0= (float) (R*Math.cos(alpha0));
            r1= (float) (R*Math.cos(alpha1));

            //循环每层圆
            for (int j = 0;j<slice*2;j++){
                beta = j*sliceStep;
                x0= (float) (r0*Math.cos(beta));
                x1= (float) (r1*Math.cos(beta));
                z0 = -(float) (r0*Math.sin(beta));
                z1 = -(float) (r1*Math.sin(beta));

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
