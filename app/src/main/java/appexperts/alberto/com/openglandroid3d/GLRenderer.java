package appexperts.alberto.com.openglandroid3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by alber on 02/03/2016.
 */
public class GLRenderer implements GLSurfaceView.Renderer{
    private static final String TAG = "GLRenderer";
    private final Context context;
    private final GLCube cube = new GLCube();
    private long startTime, fpdStartTime, numFrames;

    public GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) { //like canvas in 2D

        //Define the ilumination
        float lightAmbiient [] = new float [] { 0.2f,0.2f,0.2f,1 };
        float lightDiffuse [] = new float [] { 1,1,1,1 };
        float lightPos [] = new float [] { 1,1,1,1 };
        gl.glEnable(gl.GL_LIGHTING);
        gl.glEnable(gl.GL_LIGHT0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_AMBIENT, lightAmbiient, 0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_DIFFUSE, lightDiffuse,0);
        gl.glLightfv(gl.GL_LIGHT0, gl.GL_POSITION, lightPos,0);

        //materials with what its done..
        float matAmbient [] = new float [] { 1,1,1,1 };
        float matDiffuse [] = new float [] { 1,1,1,1 };
        gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_AMBIENT, matAmbient, 0);
        gl.glMaterialfv(gl.GL_FRONT_AND_BACK, gl.GL_DIFFUSE, matDiffuse,0);

        //Define all the openGL options that we need
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_EQUAL);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Opcional: desactivamos dither para mejorar rendimiento
        // gl.glDisable(GL10.GL_DITHER);

        startTime = System.currentTimeMillis();
        fpdStartTime =startTime;
        numFrames = 0;
        boolean SEE_TRHU = true;


        if(SEE_TRHU){
            gl.glDisable(gl.GL_DEPTH_TEST);
            gl.glEnable(gl.GL_BLEND);
            gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE);
        }

        //Enable the textures
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(gl.GL_TEXTURE_2D);
        // charge the texture of the cube from our image
        GLCube.loadTextures(gl, context, R.drawable.com);

        // ...
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        //Frustrum View
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();
        float radio = (float) width / height;
        GLU.gluPerspective(gl, 45.0f, radio, 1, 100f);

    }

    @Override
    public void onDrawFrame(GL10 gl) { //get  colour black on screen, clean the deep buffer and the colour buffer, we have to do it if not we can have problems coming from the before deep of the last photogram
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); //clear the screen and put it black
        //Positionate the model so we can see it
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -3.0f);

        //Another commands of drawing will com here...

        //Set the rotation angle accrding the time
        long elapsed = System.currentTimeMillis() -startTime;
        gl.glRotatef(elapsed * (30f/1000f),0,1,0 );
        gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);

        //Here we draw the model
        cube.draw(gl);


        // ...

        // Registration of number of FPS

        numFrames++;
        long fpsElapsed = System.currentTimeMillis() - fpdStartTime;
        if (fpdStartTime > 5 * 1000) { // cada 5 segundos
            float fps = (numFrames * 1000.0F) / fpdStartTime;
            Log.d(TAG, "Frames per second: " + fps + " (" + numFrames
                    + " frames in " + fpsElapsed + " ms)");
            fpdStartTime = System.currentTimeMillis();
            numFrames = 0;
        }

    }
}
