package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.utils.Constants;

/**
 * Created by ADMIN on 17-02-2015.
 */
public class PrivacyPolicyActivity extends Activity {

    private WebView browser;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG, Constants.PrivacyPolicyActivity);

        setContentView(R.layout.activity_privacy_policy);

        // This is used to find the IDs
        findViews();

        // this is used to set data
        setViews();

        open(v);
    }

    public void findViews(){

        browser = (WebView)findViewById(R.id.webView1);
    }

    public void setViews(){

        browser.setWebViewClient(new MyBrowser());
    }

    public void open(View view){
        String url = "http://andealr.com/PrivacyPolicy.html";
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}