package sen.com.renderer.triangles;

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
 * Des    : 画三角形组成椎体，同时也画底部，作为封闭
 *         这个在V4,基础上添加深度测试，ps 可是效果好像出乎意料
 */

public class STrianglesFanRendererV4 extends AbsSPointRenderer {
    private  ByteBuffer colorBuffer;
    private  ByteBuffer buttomBuffer;
    public float xroate;
    public float yroate;
    public float zroate;
    private  ByteBuffer byteBuffer;
    private  List<Float> points;
    private  int pointsSize;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        //启动颜色缓冲区
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    }

    public STrianglesFanRendererV4(){
        //计算点的坐标
        float r = 0.5f;//半径
        points = new ArrayList<>();
        //顶点
        points.add(0f);
        points.add(0f);
        points.add(0.5f);

        float x=0f,y=0f,z=-0.5f;
        List<Float> colorList = new ArrayList<>();
        //顶点 rgba
        colorList.add(1f);
        colorList.add(0f);
        colorList.add(0f);
        colorList.add(1f);

        boolean falg = false;
        for (float alpha = 0f;alpha< Math.PI *2;alpha= (float) (alpha+Math.PI/6)){
            x = (float) (r*Math.cos(alpha));
            y = (float) (r*Math.sin(alpha));
            points.add(x);
            points.add(y);
            points.add(z);

            if (falg =!falg){
                colorList.add(1f);
                colorList.add(0f);
                colorList.add(1f);
                colorList.add(1f);
            }else{
                colorList.add(1f);
                colorList.add(1f);
                colorList.add(0f);
                colorList.add(1f);
            }
        }
        //补一个颜色回来，为了跟底部错开一个颜色
        if (falg =!falg){
            colorList.add(1f);
            colorList.add(0f);
            colorList.add(1f);
            colorList.add(1f);
        }else{
            colorList.add(1f);
            colorList.add(1f);
            colorList.add(0f);
            colorList.add(1f);
        }
        colorBuffer =  ByteBufferUtils.list2Byte(colorList);

        byteBuffer = ByteBufferUtils.list2Byte(points);
        pointsSize = points.size();
//        底部
        //将第三个数设置跟底部，顶部的点z,跟底部的z 相同即可
        points.set(2,-0.5f);
        buttomBuffer = ByteBufferUtils.list2Byte(points);
        points.clear();
        colorList.clear();
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
        colorBuffer.position(0);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,byteBuffer);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,pointsSize/3);
        //底部
        //rgba 那么从第四个开始，就跟椎体错开一个颜色了
        colorBuffer.position(4);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,buttomBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,pointsSize/3);
        long useTime = System.currentTimeMillis()-starTime;
        Log.e("sensen","onDrawFrame useTime"+useTime);
    }
}
