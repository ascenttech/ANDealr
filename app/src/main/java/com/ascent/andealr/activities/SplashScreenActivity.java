package com.ascent.andealr.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ascent.andealr.R;
import com.ascent.andealr.data.LandingFragmentData;
import com.ascent.andealr.data.NotificationsData;
import com.ascent.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 09-01-2015.
 */
public class SplashScreenActivity extends Activity {
    LinearLayout screen;
    Handler handler = new Handler();
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();
        Constants.notificationsData = new ArrayList<NotificationsData>();
        screen = (LinearLayout) findViewById(R.id.main);

        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... params) {
            try {

                for(int i=0;i<=1;i++)
                {
                    ImageView iv = (ImageView) findViewById(R.id.logo_splashscreen);
                    iv.setImageResource(R.drawable.andealrlogo);
                    iv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));

                }
                (new Thread(){
                    @Override
                    public void run(){
                        for( i=0; i<230; i++){
                            handler.post(new Runnable(){
                                public void run(){
                                    screen.setBackgroundColor(Color.argb(255, i, i, i));
                                }
                            });
                            // next will pause the thread for some time
                            try{ sleep(10); }
                            catch(Exception r)
                            { break; }
                        }
                    }
                }).start();
                Thread.sleep(3000);
            }
            catch (Exception e ){

                //do nothingd
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent i=new Intent(getApplicationContext(),LoginOrRegisterActivity.class);
            startActivity(i);


        }
    }

}
