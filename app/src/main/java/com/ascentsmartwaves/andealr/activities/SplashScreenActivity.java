package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.FollowersData;
import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.data.LandingFragmentDetail;
import com.ascentsmartwaves.andealr.data.NotificationsData;
import com.ascentsmartwaves.andealr.data.NotificationsDataPrevious;
import com.ascentsmartwaves.andealr.data.PaymentsData;
import com.ascentsmartwaves.andealr.data.UserProfileData;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 09-01-2015.
 */
public class SplashScreenActivity extends Activity {
    LinearLayout screen;
    Handler handler = new Handler();
    int i;
    String shortcutstatus;
    SharedPreferences.Editor shorcut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG, Constants.SplashScreenActivity);

        setContentView(R.layout.activity_splash_screen);
        SharedPreferences prefs =getSharedPreferences("Shortcut", Context.MODE_PRIVATE);
        shortcutstatus = prefs.getString("created", "FALSE");

        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();
        Constants.landingFragmentDetail = new ArrayList<LandingFragmentDetail>();
        Constants.notificationsDataPrevious = new ArrayList<NotificationsDataPrevious>();
        Constants.userProfileData = new ArrayList<UserProfileData>();
        Constants.followersData = new ArrayList<FollowersData>();
        Constants.paymentsData = new ArrayList<PaymentsData>();
        Constants.notificationsData = new ArrayList<NotificationsData>();
        screen = (LinearLayout) findViewById(R.id.main);
        shorcut = getSharedPreferences("Shortcut", MODE_PRIVATE).edit();

//        if(shortcutstatus.equals("FALSE"))
//        {
//            shorcut.putString("created", "TRUE");
//            shorcut.commit();
//            createShortCut();
//        }
//        else
//        {
//
//        }


        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }
        else
        {
            new FetchData().execute();
        }




    }

//    public void createShortCut(){
//        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        shortcutintent.putExtra("duplicate", false);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ANDealr");
//        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.andealr_logo);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashScreenActivity.class));
//        sendBroadcast(shortcutintent);
//    }

    private class FetchData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {

                for(int i=0;i<=1;i++)
                {
                    ImageView iv = (ImageView) findViewById(R.id.logo_splashscreen);
                    iv.setImageResource(R.drawable.splashlogo);
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
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Intent i=new Intent(getApplicationContext(),LoginOrRegisterActivity.class);
            startActivity(i);
        }
    }

}
