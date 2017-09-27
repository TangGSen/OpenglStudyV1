package sen.com.renderer.triangles;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 15:54
 * Des    ://自定义渲染器,画个三角形
 *         第一个案例
 */

public class SRenderer implements GLSurfaceView.Renderer {
    private float ratio;

    //表层创建时
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置清屏色 纯黑的
//            gl.glClearColor(0.611f, 0.153f, 0.690f, 1);
        gl.glClearColor(1f, 1f, 1f, 1);
        //状态机启用顶点缓存区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        /**
         * 左下角为（0，0，0）
         * X，Y————以像素为单位，指定了视口的左下角（在第一象限内，以（0，0）为原点的）位置。
         * width，height————表示这个视口矩形的宽度和高度，根据窗口的实时变化重绘窗口。
         */

        gl.glViewport(0, 0, width, height);
//            gl.glViewport(0, 0, 500, 500);
        /**
         * 设置矩阵模式：透视矩阵，正交矩阵
         */
        gl.glMatrixMode(GL10.GL_PROJECTION);

        //加载单位矩阵
        gl.glLoadIdentity();

        /**
         * //设置屏截头体(近平面和远平面组成的棱台)
         * glFrustum()函数定义一个平截头体，它计算一个用于实现透视投影的矩阵，
         * 并把它与当前的投影矩阵（一般是单位矩阵）相乘。
         * 也即是该函数构造了一个视景体用来将模型进行投影，来裁剪模型，
         * 决定模型哪些在视景体里面，哪些在视景体的外面，在视景体之外的就不可见
         * 参数解释：
         *left,right指明相对于垂直平面的左右坐标位置
         *bottom,top指明相对于水平剪切面的下上位置
         *nearVal,farVal指明相对于深度剪切面的远近的距离，两个必须为正数
         * <img src="https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/s%3D220/sign=119391addab44aed5d4eb9e6831d876a/472309f790529822e585ab24d7ca7bcb0b46d4a7.jpg" alt="">
         */
        ratio = (float) width/(float) height;
        gl.glFrustumf(-1f,1f,-ratio, ratio,3,7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除颜色缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //加载模型矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        /**
         * eye xyz 放置眼球的位置
         * center xyz 眼球的观察点
         * up xyz 指定眼球的向上向量
         */
        GLU.gluLookAt(gl,0,0,5,0,0,0,0,1,0);
        //画三角形
//            float[] coords = {
//              0f,0.5f,0f,
//              -0.5f,-0.5f,0f,
//              0.5f,-0.5f,0f,
//            };

//            float[] coords = {
//                    0f,0.1f,0f,
//                    -1f,-1f,0f,
//                    1f,-1f,0f,
//            };
        //满屏输出
        float[] coords = {
                0f,ratio,2f,
                -1f,-ratio,2f,
                1f,-ratio,2f,
        };
        //分配字节缓存区间空间，存放顶点坐标数据
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coords.length*4);
        //设置顺序-本地顺序
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(coords);
        //定位指针的位置，从该位置开始读取顶点数据
        byteBuffer.position(0);
        //指定三角形颜色
        gl.glColor4f(1f,0f,0f,1f);
        /**
         * 指定顶点缓冲区
         * size：指定了每个顶点对应的坐标个数，只能是2,3,4中的一个，默认值是4
         type：指定了数组中每个顶点坐标的数据类型，可取常量:GL_BYTE, GL_SHORT,GL_FIXED,GL_FLOAT;
         stride:跨度，指定了连续顶点间的字节排列方式，如果为0，数组中的顶点就会被认为是按照紧凑方式排列的，默认值为0
         pointer:制订了数组中第一个顶点的首地址，默认值为0，对于我们的android，大家可以不用去管什么地址的，一般给一个IntBuffer就可以了。
         */
        gl.glVertexPointer(3, GL10.GL_FLOAT,0,byteBuffer);
        //绘制
        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);

    }
}
