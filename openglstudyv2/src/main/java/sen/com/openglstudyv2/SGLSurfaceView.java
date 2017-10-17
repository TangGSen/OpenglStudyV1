package sen.com.openglstudyv2;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/10/11 13:09
 * Des    :
 */

public class SGLSurfaceView extends GLSurfaceView {
    private SRenderer sRenderer;
    public SGLSurfaceView(Context context) {
        this(context,null);
    }

    public SGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置opengl 的渲染环境版本2，如果需要是3.0的话，那么在c++ 层去做
        setEGLContextClientVersion(2);
        sRenderer = new SRenderer();
        setRenderer(sRenderer);
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
