package sen.com.bean;

import java.nio.ByteBuffer;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 19:13
 * Des    :
 */

public class PointBean {
    private float size;
    private ByteBuffer byteBuffer;

    public PointBean(float size, ByteBuffer byteBuffer) {
        this.size = size;
        this.byteBuffer = byteBuffer;
    }

    public float getSize() {
        return size;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }
}
