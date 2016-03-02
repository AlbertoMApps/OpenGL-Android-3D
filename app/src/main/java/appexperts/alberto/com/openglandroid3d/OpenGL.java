package appexperts.alberto.com.openglandroid3d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGL extends AppCompatActivity {
    GLView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GLView(this);
        setContentView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }
}
