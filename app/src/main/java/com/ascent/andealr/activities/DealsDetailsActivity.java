package com.ascent.andealr.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascent.andealr.R;
import com.ascent.andealr.async.FetchDealDetailsAsyncTask;
import com.ascent.andealr.imagecaching.ImageLoader;
import com.ascent.andealr.utils.Constants;

/**
 * Created by ADMIN on 18-02-2015.
 */
public class DealsDetailsActivity extends ActionBarActivity{

    ActionBar actionBar;
    Intent i ;
    int position;
    ImageView cardBackground;
    ImageLoader imageLoader;
    LinearLayout textStrip;
    TextView dealTittle,dealDescription,likesCounter,redeemCounter,longDescription;
    RelativeLayout likesRedeemStrip;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        getCustomActionBar();
        imageLoader = new ImageLoader(getApplicationContext());

        i = getIntent();
        position = Integer.parseInt(i.getStringExtra("position"));

        new FetchDealDetailsAsyncTask(getApplicationContext(),new FetchDealDetailsAsyncTask.FetchDealDetailsCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(DealsDetailsActivity.this);
                dialog.setTitle("Getting Deal");
                dialog.setMessage("Getting The Required Deal");
                dialog.show();
                dialog.setCancelable(false);


            }
            @Override
            public void onResult(boolean b) {

                dialog.dismiss();
            }
        }).execute(Constants.fetchDealDetailsURL);


        setContentView(R.layout.deals_details_activity);


        cardBackground= (ImageView) findViewById(R.id.details_background_andealr_details_activity);
        imageLoader.DisplayImage(Constants.landingFragmentData.get(position).getPhotoURL(), cardBackground);

        textStrip = (LinearLayout) findViewById(R.id.tittle_bottom_strip_andealr_details_activity);

        dealTittle = (TextView) textStrip.findViewById(R.id.deal_tittle_text_andealr_fragment);
        dealTittle.setText(Constants.landingFragmentData.get(position).getDealTittle());

        dealDescription = (TextView) textStrip.findViewById(R.id.deal_description_text_andealr_fragment);
        dealDescription.setText(Constants.landingFragmentData.get(position).getCity());

        likesRedeemStrip = (RelativeLayout) findViewById(R.id.details_bottom_strip_andealr_details_activity);
        likesCounter = (TextView) likesRedeemStrip.findViewById(R.id.likes_counter_text_include);

//        likesCounter.setText(Constants.landingFragmentData.get(position).getLikes());

        redeemCounter = (TextView) likesRedeemStrip.findViewById(R.id.redeem_counter_text_include);
//        redeemCounter.setText(Constants.landingFragmentData.get(position).getRedeem());

        longDescription = (TextView) findViewById(R.id.deals_details_text_andnrby_fragment_details_activity);
        longDescription.setText(Constants.landingFragmentData.get(position).getDealDescription());




//        cardBackground.setImageResource(Constants.landingFragmentData.get(position).());


//        imageLoader.DisplayImage(Constants.landingFragmentData.get(position).getCardBackground(), cardBackground);
    }

    private void getCustomActionBar() {

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
}
