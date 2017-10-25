package sen.com.openglstudyv2.mediacodec;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;


/**
 * Created by Administrator on 2017/8/13.
 */

public class VideoPusher implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private byte[] callbackBuffer;
    private boolean isPushing;
    private int width = 640;
    private int height = 480;

    public VideoPusher(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;
        mSurfaceHolder.addCallback(this);

    }



    public void release() {
        stopCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //surface 初始化完成，开始摄像头预览
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//
//        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//        }

        //使用旧的api

        startCamera();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //打开摄像头
    private void startCamera() {
        try {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            Camera.Parameters parameters = mCamera.getParameters();
            //设置相机预览格式，NV21 是YUV格式不过是YVU
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPreviewSize(640,480);
            //帧频
//            parameters.setPreviewFpsRange(mVideoParmas.getBitrate()-1,mVideoParmas.getBitrate());
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mSurfaceHolder);

            mCamera.setPreviewCallbackWithBuffer(this);
            int bitsPerPixel = ImageFormat.getBitsPerPixel(ImageFormat.NV21);
            callbackBuffer = new byte[width*height*bitsPerPixel/8];
            mCamera.addCallbackBuffer(callbackBuffer);

            //获取摄像头预览数据
            mCamera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //稀放摄像头
    private void stopCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.startPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void switchCamera() {
//        if (mVideoParmas.getCarameId() == Camera.CameraInfo.CAMERA_FACING_BACK) {
//            mVideoParmas.setCarameId(Camera.CameraInfo.CAMERA_FACING_FRONT);
//        } else {
//            mVideoParmas.setCarameId(Camera.CameraInfo.CAMERA_FACING_BACK);
//        }
        stopCamera();
        startCamera();

    }

    //多久回调一次跟帧频有关
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
//        先addCallbackBuffer，然后再处理帧数据，否则会降低帧率。
//也不要在这里处理耗时的，否则会降低帧率
        if(mCamera !=null){
            mCamera.addCallbackBuffer(callbackBuffer);
        }
        if (isPushing){
            //传到native 编码
        }


    }
}
