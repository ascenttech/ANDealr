package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.utils.Constants;

/**
 * Created by ADMIN on 09-02-2015.
 */
public class AboutUsActivity extends Activity {

    ActionBar actionBar;
    TextView contactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG,Constants.AboutUsActivity);

        setContentView(R.layout.activity_about_us);

        // This is used to find the views
        findViews();

        // this is used to set data to the views
        setViews();


    }

    private void findViews(){

        contactUs =(TextView) findViewById(R.id.contact_us_text_about_us_activity);

    }

    private void setViews(){

        contactUs.setOnClickListener(listener);

    }

    public void modeOfContact(){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "shoot@andealr.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, ""));

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                case R.id.contact_us_text_about_us_activity: modeOfContact();
                    break;

                default:break;

            }


        }
    };


}
