package sen.com.openglstudyv2;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/10/11 13:09
 * Des    :
 */

public class CameraSGLSurfaceView extends GLSurfaceView {
    private SCameraRenderer sRenderer;
    public CameraSGLSurfaceView(Context context) {
        this(context,null);
    }

    public CameraSGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void init(CameraV1 mCamera, boolean isPreviewStarted, Activity mainActivity) {
        //设置opengl 的渲染环境版本2，如果需要是3.0的话，那么在c++ 层去做
        setEGLContextClientVersion(2);
        sRenderer = new SCameraRenderer(this);
        sRenderer.init(this, mCamera, isPreviewStarted, mainActivity);
        setRenderer(sRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void deinit() {

    }
}
