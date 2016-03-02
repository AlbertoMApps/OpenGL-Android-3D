package appexperts.alberto.com.openglandroid3d;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by alber on 02/03/2016.
 */
public class GLView extends GLSurfaceView{

    private final GLRenderer renderer; //it works as a renderer of this view

    public GLView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        renderer = new GLRenderer(getContext());
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

}
