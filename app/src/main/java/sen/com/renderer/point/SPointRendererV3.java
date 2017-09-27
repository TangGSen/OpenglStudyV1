package sen.com.renderer.point;

import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

import sen.com.renderer.AbsSPointRenderer;
import sen.com.openglstudyv1.ByteBufferUtils;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:57
 * Des    : 绘制点，螺旋线点，onDrawFrame，
 *          每次绘制一个点，这样才能设置每个点的大小，但是这样刷新的耗时平均在20-30ms ,得优化
 *
 */

public class SPointRendererV3 extends AbsSPointRenderer {
    public float xroate;
    public float yroate;
    public float zroate;

    public SPointRendererV3(){

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

        //计算点的坐标
        float r = 0.5f;//半径
        float x=0f,y=0f,z=1f;
        int rSize = 6;//6个螺旋
        float psize =0.5f;
        float pstep = 0.05f;
        float zstep =0.01f;//每个螺旋z轴坐标向后移动
        /*这段代码需要优化ByteBufferUtils.arry2Byte 应该缓存起来*/
        for (float alpha = 0f;alpha<Math.PI *rSize;alpha= (float) (alpha+Math.PI/32)){
            x = (float) (r*Math.cos(alpha));
            y = (float) (r*Math.sin(alpha));
            z = z -zstep;
            gl.glPointSize(psize+=pstep);
            gl.glVertexPointer(3,GL10.GL_FLOAT,0, ByteBufferUtils.arry2Byte(new float[]{x,y,z}));
            //每次只画一个点
            gl.glDrawArrays(GL10.GL_POINTS,0,1);
        }
        long useTime = System.currentTimeMillis()-starTime;
        Log.e("sensen","onDrawFrame useTime"+useTime);
    }
}
