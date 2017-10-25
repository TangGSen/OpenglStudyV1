package sen.com.openglstudyv2;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class CameraMainActivity extends AppCompatActivity {
    CameraSGLSurfaceView mSGlSurfaceView;
    private int mCameraId;
    private CameraV1 mCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化AssetManager
        CameraSGLNative.initAssetManager(getAssets());
        mSGlSurfaceView = new CameraSGLSurfaceView(getApplicationContext());
        setContentView(mSGlSurfaceView);

        mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        DisplayMetrics dm = new DisplayMetrics();
        mCamera = new CameraV1(this,720,1280);
        if (!mCamera.openCamera(dm.widthPixels, dm.heightPixels, mCameraId)) {
            return;
        }
        mSGlSurfaceView.init(mCamera, false, this);

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mSGlSurfaceView != null) {
            mSGlSurfaceView.onPause();
            mSGlSurfaceView.deinit();
            mSGlSurfaceView = null;
        }

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.releaseCamera();
            mCamera = null;
        }
    }
}


