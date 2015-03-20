package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;

/**
 * Created by ADMIN on 09-02-2015.
 */
public class AboutUsActivity extends Activity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aboutus);
        TextView version=(TextView) findViewById(R.id.version);
        TextView message=(TextView) findViewById(R.id.message);
        TextView contactus=(TextView) findViewById(R.id.contactus);

        contactus.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "shoot@andealr.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, ""));
            }
        });
    }




}
