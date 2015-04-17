package com.sevenheaven.flipview;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    ImageView tv;
    Camera camera;
    Matrix matrix;

    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;

        matrix = new Matrix();


        camera = new Camera();
        camera.save();
        camera.setLocation(0, 0, -40);
        camera.rotate(0, 45, 0);

        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(300, 0);

        tv = new ImageView(this){
            @Override
            public void onDraw(Canvas canvas){
                canvas.setMatrix(matrix);
                super.onDraw(canvas);
            }
        };

        tv.setImageResource(R.mipmap.ic_launcher);

        setContentView(tv);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                float cx = event.getX();
                float cy = event.getY();

                float degree = 90 * (cx / (float) width);

                camera.save();
                camera.setLocation(0, 0, -40);
                camera.rotate(0, degree, 0);

                camera.getMatrix(matrix);

                camera.restore();

                float pivotX = tv.getWidth() >> 1;

                matrix.preTranslate(-pivotX, -tv.getHeight() >> 1);
                matrix.postTranslate(pivotX + (pivotX * (degree / 90.0F)), tv.getHeight() >> 1);

                tv.invalidate();

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
