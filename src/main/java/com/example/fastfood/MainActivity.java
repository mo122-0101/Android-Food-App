package com.example.fastfood;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //  some variables
    Animation topAnimation, bottomAnimation;
    ImageView image;
    TextView logo, slogan;


    //  final variables
    public static final int SPLASH_SCREEN = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

//      animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//      hooks
        image = findViewById(R.id.image_logo);
        logo = findViewById(R.id.text_logo);
        slogan = findViewById(R.id.slogan);
//      assign animations to my views
        image.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

//      method to go to next activity after 5 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class) ;
                Pair pairs [] = new Pair[3];
                pairs [0] = new Pair<View, String>(image, "imageTransition");
                pairs [1] = new Pair<View, String>(logo, "logoTransition");
                pairs [2] = new Pair<View, String>(slogan, "sloganTransition");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());
            }
        }, SPLASH_SCREEN);

    }
}
