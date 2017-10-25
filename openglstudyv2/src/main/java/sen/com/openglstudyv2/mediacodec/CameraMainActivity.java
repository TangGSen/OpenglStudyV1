package sen.com.openglstudyv2.mediacodec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import sen.com.openglstudyv2.R;


public class CameraMainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private VideoPusher livePusher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_mediacodec);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //开始时就预览摄像头
        livePusher = new VideoPusher(surfaceView.getHolder());

    }

    public void startLive(View view){
        Toast.makeText(this,"startLive",Toast.LENGTH_SHORT).show();
    }
    public void stopLive(View view){
        Toast.makeText(this,"stopLive",Toast.LENGTH_SHORT).show();
    }
    //切换摄像头
    public void switchCamera(View view){
        Toast.makeText(this,"switchCamera",Toast.LENGTH_SHORT).show();
        livePusher.switchCamera();
    }
}
