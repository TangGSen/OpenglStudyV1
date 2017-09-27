package sen.com.openglstudyv1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Author : 唐家森
 * Version: 1.0
 * On     : 2017/9/26 18:57
 * Des    :
 */

public class ByteBufferUtils {
    public static ByteBuffer arry2Byte(float[] arry) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(arry.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.put(arry);
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static ByteBuffer list2Byte(List<Float> lines) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(lines.size()* 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = byteBuffer.asFloatBuffer();
        for (float f : lines) {
            buffer.put(f);
        }
        byteBuffer.position(0);
        return byteBuffer;

    }
}
