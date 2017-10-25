package sen.com.openglstudyv2;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import sen.com.openglstudyv2.view.CameraButtonView;
import sen.com.openglstudyv2.view.CameraSGLSurfaceView;

public class CameraMainActivity extends AppCompatActivity implements View.OnClickListener {
    CameraSGLSurfaceView mSGlSurfaceView;
    private int mCameraId;
    private CameraV1 mCamera;
    private CameraButtonView takePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化AssetManager
        CameraSGLNative.initAssetManager(getAssets());
        setContentView(R.layout.activity_camera_opengl);

        mSGlSurfaceView = (CameraSGLSurfaceView) findViewById(R.id.camera_glview);

        mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        DisplayMetrics dm = new DisplayMetrics();
        mCamera = new CameraV1(this,1080,1920);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
        mCamera.setTakePicturePath(path);
        if (!mCamera.openCamera(dm.widthPixels, dm.heightPixels, mCameraId)) {
            return;
        }
        mSGlSurfaceView.init(mCamera, false, this);
        takePicture = (CameraButtonView) findViewById(R.id.takePicture);
        takePicture.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() ==MotionEvent.ACTION_DOWN){
            if (mCamera!=null){
                mCamera.requestCameraFocus();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takePicture:
                if (mCamera != null)
                    mCamera.takePhoto();
                break;
        }

    }
}


