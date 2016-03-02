package appexperts.alberto.com.openglandroid3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by alber on 02/03/2016.
 */
public class GLCube { //that normally needs to come predefined so we only care about the OpenGL, the view and the renderer options...
    private final IntBuffer mVertexBuffer;
    private final IntBuffer mTextureBuffer;


    public GLCube() {

        int one = 65536;
        int half = one/2;
        int vertices [] = {
            //FRONT
                -half,-half, -half, -half, -half, -half,
                -half,-half, -half, -half, -half, -half,
            //BACK
                -half,-half, -half, -half, -half, -half,
                -half,-half, -half, -half, -half, -half,
            //LEFT
                -half,-half, -half, -half, -half, -half,
                -half,-half, -half, -half, -half, -half,
            //RIGHT
                -half,-half, -half, -half, -half, -half,
                -half,-half, -half, -half, -half, -half,
            //TOP
                -half,-half, -half, -half, -half, -half,
                -half,-half, -half, -half, -half, -half,
            //BOTTOM
                -half,-half, -half, -half, -half, -half,
                -half,-half, -half, -half, -half, -half,

        };

        int textCoords [] = {
                //FRONT
                0,one, one, -one, 0, 0, one, 0,
                //BACK
                one,one, one, 0, 0, one, 0 ,0,
                //LEFT
                one,one, one, 0, 0, one, 0 ,0,
                //RIGHT
                one,one, one, 0, 0, one, 0 ,0,
                //TOP
                 one,0, 0, 0, one, one, 0, one,
                //BOTTOM
                0, 0, 0, one, one, 0, one, one,};

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asIntBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asIntBuffer();
        mTextureBuffer.put(textCoords);
        mTextureBuffer.position(0);
    }

    public void draw ( GL10 gl){
        gl.glVertexPointer(3, gl.GL_FIXED, 0, mVertexBuffer);

        gl.glEnable(gl.GL_TEXTURE_2D); //Solution bugs 3623
        gl.glTexCoordPointer(2, gl.GL_FIXED, 0, mTextureBuffer);

        gl.glColor4f(1, 1, 1, 1);
        gl.glNormal3f(0, 0, 1);
        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 0, 4);
        gl.glNormal3f(0, 0, -1);
        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 4, 4);

        gl.glColor4f(1, 1, 1, 1);
        gl.glNormal3f(-1, 0, 0);
        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 8, 4);
        gl.glNormal3f(1, 0, 0);
        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 12, 4);

        gl.glColor4f(1, 1, 1, 1);
        gl.glNormal3f(0, 1, 0);
        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 16, 4);
        gl.glNormal3f(0, -1, 0);
        gl.glDrawArrays(gl.GL_TRIANGLE_STRIP, 20, 4);
    }

    static void loadTextures (GL10 gl, Context context, int resources ){
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resources);
        GLUtils.texImage2D(gl.GL_TEXTURE_2D,0,bmp,0);
        gl.glTexParameterx(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR);
        gl.glTexParameterx(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR);
        bmp.recycle();
    }
}
