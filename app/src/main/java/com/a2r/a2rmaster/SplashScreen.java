package com.a2r.a2rmaster;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.a2r.a2rmaster.Activity.Home;
import com.a2r.a2rmaster.Activity.LoginActivity;
import com.a2r.a2rmaster.Util.Constants;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progressBar1;
    int progressStatus = 0;
    Handler handler = new Handler();
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        user_id = SplashScreen.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar1.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressStatus == 100) {
                    //Intent i = new Intent(SplashScreen.this, MainActivity.class);

                    if (user_id == null || user_id.trim().length() < 0) {

                                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                //finish();
                    }
                    else {
                        Intent i = new Intent(SplashScreen.this, Home.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                    }
                }
            }
        }).start();
    }
}
