package sen.opengl.camera.main;

import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/10/11 13:19
 * Des    :
 */

public class CameraSGLNative {
    static{
        System.loadLibrary("sgles");
    }
    /**
     * 为了让底层去获取android 资源
     * @param assets
     */
    public static native void initAssetManager(AssetManager assets);
    /**
     * 初始化opengl
     */
    public static native void onSurfaceCreated();

    public static native void onSurfaceChanged(int width, int height);
    public static native void onDrawFrame();
    public static native SurfaceTexture getSurfaceTexture();


}
