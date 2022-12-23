package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.logo_image);
        textView1 = findViewById(R.id.app_name_text);
        textView2 = findViewById(R.id.discription_text);

        imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_top));
        textView1.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_bottom));
        textView2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_bottom));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3200);
    }
}
