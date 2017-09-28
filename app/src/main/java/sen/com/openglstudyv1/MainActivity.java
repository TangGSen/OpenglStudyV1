package sen.com.openglstudyv1;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import sen.com.renderer.poll.SRingRenderer;


public class MainActivity extends AppCompatActivity {

    private SRingRenderer sPointRenderer;
    private final float change=5f;
    private GLSurfaceView sGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("sensen","MainActivity->onCreate"+Thread.currentThread().getName());
        setContentView(R.layout.activity_main);
        sGlSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceView);
        sPointRenderer = new SRingRenderer();
        /**
         * 设置颜色缓存为RGBA，位数都为8
         depth缓存位数为16
         stencil缓存位数为8
         */
//        sGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8,16, 8);//
        sGlSurfaceView.setRenderer(sPointRenderer);

        //RENDERMODE_CONTINUOUSLY 持续渲染，默认
        //RENDERMODE_WHEN_DIRTY 脏渲染，命令渲染
        sGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void chageX(View view){
      sPointRenderer.xroate+=  change;
        sGlSurfaceView.requestRender();
    }
    public void chageY(View view){
        sPointRenderer.yroate+=  change;
        sGlSurfaceView.requestRender();
    }
    public void chageZ(View view){
        sPointRenderer.zroate+=  change;
        sGlSurfaceView.requestRender();
    }









}
