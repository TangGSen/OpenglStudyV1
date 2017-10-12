package sen.com.openglstudyv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SGLSurfaceView mSGlSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化AssetManager
        SGLNative.initAssetManager(getAssets());
        mSGlSurfaceView = new SGLSurfaceView(getApplicationContext());
        setContentView(mSGlSurfaceView);

    }
}
