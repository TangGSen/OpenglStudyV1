package sen.com.openglstudyv1;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import sen.com.renderer.light.SLightColorCudeRenderer;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private SLightColorCudeRenderer renderer;
    private final float change=5f;
    private GLSurfaceView sGlSurfaceView;
    private FrameLayout layout_change_param;
    private CheckBox enableColorMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("sensen","MainActivity->onCreate"+Thread.currentThread().getName());
        setContentView(R.layout.activity_main);
        sGlSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceView);
        renderer = new SLightColorCudeRenderer();
        /**
         * 设置颜色缓存为RGBA，位数都为8
         depth缓存位数为16
         stencil缓存位数为8
         */
//        sGlSurfaceView.setEGLConfigChooser(8, 8, 8, 8,16, 8);//
        sGlSurfaceView.setRenderer(renderer);

        //RENDERMODE_CONTINUOUSLY 持续渲染，默认
        //RENDERMODE_WHEN_DIRTY 脏渲染，命令渲染
        sGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        layout_change_param = (FrameLayout) findViewById(R.id.layout_change_param);
        layout_change_param.setVisibility(View.GONE);
        //设置全局环境光的RGB
        SeekBar seekBarR = (SeekBar) findViewById(R.id.seekBarR);
        SeekBar seekBarG = (SeekBar) findViewById(R.id.seekBarG);
        SeekBar seekBarB = (SeekBar) findViewById(R.id.seekBarB);
        //设置材料和ambinte diffuse 的rgb
        SeekBar seekBarMeatilR = (SeekBar) findViewById(R.id.seekBarMeatilR);
        SeekBar seekBarMeatilG = (SeekBar) findViewById(R.id.seekBarMeatilG);
        SeekBar seekBarMeatilB = (SeekBar) findViewById(R.id.seekBarMeatilB);

        enableColorMaterial =(CheckBox)findViewById(R.id.enableColorMaterial);

        seekBarR.setOnSeekBarChangeListener(this);
        seekBarG.setOnSeekBarChangeListener(this);
        seekBarB.setOnSeekBarChangeListener(this);
        seekBarMeatilR.setOnSeekBarChangeListener(this);
        seekBarMeatilG.setOnSeekBarChangeListener(this);
        seekBarMeatilB.setOnSeekBarChangeListener(this);
        enableColorMaterial.setOnCheckedChangeListener(this);
    }

    public void chageX(View view){
        renderer.xroate+=  change;
        sGlSurfaceView.requestRender();
    }
    public void chageY(View view){
        renderer.yroate+=  change;
        sGlSurfaceView.requestRender();
    }
    public void chageZ(View view){
        renderer.zroate+=  change;
        sGlSurfaceView.requestRender();
    }


    public void chageParam(View view){
       if( layout_change_param.getVisibility()==View.VISIBLE){
           layout_change_param.setVisibility(View.GONE);
       }else{
          layout_change_param.setVisibility(View.VISIBLE);
       }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int seekId = seekBar.getId();
        switch (seekId){
            case R.id.seekBarR:
                renderer.golbalRProgress =progress/100;
                break;
            case R.id.seekBarG:
                renderer.golbalGProgress =progress/100;
                break;
            case R.id.seekBarB:
                renderer.golbalBProgress =progress/100;
                break;
            case R.id.seekBarMeatilR:
                renderer.meatilRProgress =progress/100;
                break;
            case R.id.seekBarMeatilG:
                renderer.meatilGProgress =progress/100;
                break;
            case R.id.seekBarMeatilB:
                renderer.meatilBProgress =progress/100;
                break;

        }
        sGlSurfaceView.requestRender();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int seekId = buttonView.getId();
        switch (seekId) {
            case R.id.enableColorMaterial:
                renderer.enableColorMaterial = isChecked;
                break;
        }
        sGlSurfaceView.requestRender();
    }
}
