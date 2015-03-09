package com.ascent.andealr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ascent.andealr.R;

/**
 * Created by ADMIN on 16-02-2015.
 */
public class SettingsActivity extends ActionBarActivity {

    ActionBar actionBar;
    TextView aboutUs,privacyPolicy,logout,terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        getCustomActionBar();

        setContentView(R.layout.settings_activity);

        aboutUs = (TextView) findViewById(R.id.about_us_static_text);
        privacyPolicy = (TextView) findViewById(R.id.privacy_policy_static_text);
        logout = (TextView) findViewById(R.id.logout_static_text);
        terms=(TextView)findViewById(R.id.terms);

        aboutUs.setOnClickListener(listener);
        privacyPolicy.setOnClickListener(listener);
        logout.setOnClickListener(listener);
        terms.setOnClickListener(listener);
    }


    private void getCustomActionBar(){


        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar, null);
        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
            }
        });

        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i ;

            switch(v.getId()){

                case R.id.about_us_static_text : i = new Intent(SettingsActivity.this,AboutUsActivity.class);
                    startActivity(i);
                    break;
                case R.id.terms:
                    i = new Intent(SettingsActivity.this,Termsncondition.class);
                    startActivity(i);
                    break;
                case R.id.privacy_policy_static_text: i = new Intent(SettingsActivity.this,PrivacyPolicyActivity.class);
                    startActivity(i);
                    break;
                case R.id.logout_static_text :
                    SharedPreferences prefs = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                    Intent i2= new Intent(SettingsActivity.this,LoginOrRegisterActivity.class);
                    startActivity(i2);
                    break;
            }

        }
    };


}
