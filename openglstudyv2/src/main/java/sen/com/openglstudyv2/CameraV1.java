package sen.com.openglstudyv2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class CameraV1 {
    private Activity mActivity;
    private int mCameraId;
    private Camera mCamera;
    private int mWidth;
    private int mHeigth;
    String TAG = "sggllog";
    private int screen;
    private final static int SCREEN_PORTRAIT = 0;
    private final static int SCREEN_LANDSCAPE_LEFT = 90;
    private final static int SCREEN_LANDSCAPE_RIGHT = 270;
    private boolean takePicture;
    private String rootPicPath;

    public CameraV1(Activity activity,int width,int heigth) {
        mActivity = activity;
        this.mWidth = width;
        this.mHeigth = heigth;
    }

    public void setTakePicturePath(String path){
        this.rootPicPath = path;
    }

    public boolean openCamera(int screenWidth, int screenHeight, int cameraId) {
        try {
            mCameraId = cameraId;
            mCamera = Camera.open(mCameraId);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.set("orientation", "portrait");
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            setPreviewOrientation(parameters);
            setPreviewSize( parameters);
            setPictureSize(parameters);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void setPictureSize(Camera.Parameters parameters) {
        List<Camera.Size> supportedPictureSize = parameters.getSupportedPictureSizes();
        Iterator<Camera.Size> iterator = supportedPictureSize.iterator();
        parameters.setPictureSize(supportedPictureSize.get(0).width,supportedPictureSize.get(0).height);
        while (iterator.hasNext()) {
            Camera.Size next = iterator.next();
            Log.e(TAG, "PictureSize支持 " + next.width + "x" + next.height);

        }
    }

    private void setPreviewSize(Camera.Parameters parameters) {
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size size = supportedPreviewSizes.get(0);
        Log.e(TAG, "支持 " + size.width + "x" + size.height);
        int m = Math.abs(size.height * size.width - mHeigth * mWidth);
        supportedPreviewSizes.remove(0);
        Iterator<Camera.Size> iterator = supportedPreviewSizes.iterator();
        while (iterator.hasNext()) {
            Camera.Size next = iterator.next();
            Log.e(TAG, "PreviewSize支持 " + next.width + "x" + next.height);
            int n = Math.abs(next.height * next.width - mHeigth *mWidth);
            if (n < m) {
                m = n;
                size = next;
            }
        }
        mHeigth =size.height;
        mWidth =size.width;
        parameters.setPreviewSize(mWidth,mHeigth);
        Log.e(TAG, "预览分辨率 width:" + size.width + " height:" + size.height);
    }

    private void setPreviewOrientation(Camera.Parameters parameters) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, info);
        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        screen = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                screen = SCREEN_PORTRAIT;
                break;
            case Surface.ROTATION_90: // 横屏 左边是头部(home键在右边)
                screen = SCREEN_LANDSCAPE_LEFT;
                break;
            case Surface.ROTATION_180:
                screen = 180;
                break;
            case Surface.ROTATION_270:// 横屏 头部在右边
                screen = SCREEN_LANDSCAPE_RIGHT;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + screen) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - screen + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
    }



    public void startPreview() {
        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    public void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }
    //设置Camera surfaceTexture
    public void setPreviewTexture(SurfaceTexture surfaceTexture) {
        if (mCamera != null) {
            try {
                mCamera.setPreviewTexture(surfaceTexture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public void requestCameraFocus() {
        if (mCamera != null) {
            Log.e("sggllog", "requestCameraFocus");
            mCamera.autoFocus(null);
        }
    }


    public void takePhoto() {
        if (mCamera == null || takePicture) {
            return;
        }
        takePicture = true;
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(final byte[] data, Camera camera) {
                camera.startPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(rootPicPath,System.currentTimeMillis()+".png");
                        try {
                            // 获取当前旋转角度, 并旋转图片
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            bitmap = rotateBitmapByDegree(bitmap, 90);
                            BufferedOutputStream bos = new BufferedOutputStream(
                                    new FileOutputStream(file));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                            bitmap.recycle();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        takePicture =false;
                    }
                }).start();
            }
        });
    }

    // 旋转图片
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
}
