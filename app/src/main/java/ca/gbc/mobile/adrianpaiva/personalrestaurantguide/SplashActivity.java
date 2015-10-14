package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    MediaPlayer splashSound;

    @Override
    protected void onPause() {
        super.onPause();
        splashSound.release();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashSound = MediaPlayer.create(getApplicationContext(), R.raw.splash);
        splashSound.start();



        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}

