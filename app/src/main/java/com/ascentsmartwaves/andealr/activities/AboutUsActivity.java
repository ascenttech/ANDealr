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
    TextView version,message,contactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG,Constants.AbooutUsActivity);

        setContentView(R.layout.activity_about_us);

        // This is used to find the views
        findViews();

        // this is used to set data to the views
        setViews();


    }

    private void findViews(){

        version=(TextView) findViewById(R.id.version);
        message=(TextView) findViewById(R.id.message);
        contactus=(TextView) findViewById(R.id.contactus);

    }

    private void setViews(){

        contactus.setOnClickListener(listener);

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

                case R.id.contactus: modeOfContact();
                    break;

                default:break;

            }


        }
    };


}
