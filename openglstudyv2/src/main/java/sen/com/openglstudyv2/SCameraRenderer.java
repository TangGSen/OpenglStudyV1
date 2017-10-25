package sen.com.openglstudyv2;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sen.com.openglstudyv2.view.CameraSGLSurfaceView;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/10/11 13:06
 * Des    :
 */

public class SCameraRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    GLSurfaceView mGLSurfaceView;
    private Context mContext;
    private boolean bIsPreviewStarted;
    private CameraV1 mCamera;
    private SurfaceTexture mSurfaceTexture;
    private boolean takePicture;

    public SCameraRenderer(CameraSGLSurfaceView cameraSGLSurfaceView) {
        mGLSurfaceView = cameraSGLSurfaceView;

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mSurfaceTexture = CameraSGLNative.getSurfaceTexture();
        if(mSurfaceTexture!=null){
            mSurfaceTexture.setOnFrameAvailableListener(this);
        }
        mCamera.setPreviewTexture(mSurfaceTexture);
        mCamera.startPreview();

        CameraSGLNative.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        CameraSGLNative.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (mSurfaceTexture != null) {
            mSurfaceTexture.updateTexImage();

//            mSurfaceTexture.getTransformMatrix(transformMatrix);
        }
        CameraSGLNative.onDrawFrame();
    }

    public void init(CameraSGLSurfaceView cameraSGLSurfaceView, CameraV1 camera, boolean isPreviewStarted, Activity context) {
        mContext = context;
        mGLSurfaceView = cameraSGLSurfaceView;
        mCamera = camera;
        bIsPreviewStarted = isPreviewStarted;
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mGLSurfaceView.requestRender();

    }





}
