package sen.com.openglstudyv2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    SGLSurfaceView mSGlSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化AssetManager
        SGLNative.initAssetManager(getAssets());
        mSGlSurfaceView = new SGLSurfaceView(getApplicationContext());
        setContentView(mSGlSurfaceView);


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mSGlSurfaceView != null) {
            mSGlSurfaceView.onPause();
        }
    }
}


