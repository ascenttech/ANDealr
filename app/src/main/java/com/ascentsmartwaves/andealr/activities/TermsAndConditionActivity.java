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
 * Created by ADMIN on 07-03-2015.
 */
public class TermsAndConditionActivity extends Activity {
    private WebView browser;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG, Constants.TermsAndConditionActivity);

        setContentView(R.layout.activity_privacy_policy);
        browser = (WebView)findViewById(R.id.webView1);
        browser.setWebViewClient(new MyBrowser());
        open(v);
    }



    public void open(View view){
        String url = "http://andealr.com/Terms%20And%20Conditions.html";
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