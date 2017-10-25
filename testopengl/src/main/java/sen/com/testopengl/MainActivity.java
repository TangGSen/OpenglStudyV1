package sen.com.testopengl;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	public final static String TAG="MainActivity";
	public int screenWidth, screenHeight;
	public int previewWidth, previewHeight;
	private CameraView cameraView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createUI();
	}

	void createUI()
	{
		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		cameraView= new CameraView(this,dm.widthPixels,dm.heightPixels);
		FrameLayout root = (FrameLayout) findViewById(R.id.root);
		//'index' indicates the order of the view. 0 means the view will behind all 
		//other views. root.getChildCount() means the top
		root.addView(cameraView,0);		
		Log.v(TAG, "createUI completed");
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			this.finish();
			System.exit(0);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

}
