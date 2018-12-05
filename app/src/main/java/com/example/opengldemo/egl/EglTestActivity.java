package com.example.opengldemo.egl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.opengldemo.R;

public class EglTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.egl_test_layout);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        LinearLayout rootView = (LinearLayout)findViewById(R.id.rootView);
        OpenglView view = new OpenglView(this);
        view.setImageView(imageView);
//        view.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
        rootView.addView(view);

    }
}
