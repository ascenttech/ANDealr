package com.ascentsmartwaves.andealr.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.async.FetchDealDetailsAsyncTask;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;

/**
 * Created by ADMIN on 18-02-2015.
 */
public class DealsDetailsActivity extends ActionBarActivity{

    ActionBar actionBar;
    Intent i ;
    int position,id;
    ImageView cardBackground;
    ImageLoader imageLoader;
    LinearLayout textStrip;
    TextView dealTittle,dealDescription,likesCounter,redeemCounter,longDescription,start,end,dealCity,reachCounter;
    RelativeLayout likesRedeemStrip;
    ProgressDialog dialog;
    String finalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Deal Details");

        imageLoader = new ImageLoader(getApplicationContext());
        Constants.landingFragmentDetail.clear();
        i = getIntent();
        position = Integer.parseInt(i.getStringExtra("position"));
        id = Integer.parseInt(i.getStringExtra("deal id"));
        finalUrl=Constants.fetchDealDetailsURL+id;
        new FetchDealDetailsAsyncTask(position,getApplicationContext(),new FetchDealDetailsAsyncTask.FetchDealDetailsCallback() {
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

                cardBackground= (ImageView) findViewById(R.id.details_background_andealr_details_activity);
                imageLoader.DisplayImage(Constants.landingFragmentDetail.get(0).getPhotoURL(), cardBackground);

                textStrip = (LinearLayout) findViewById(R.id.tittle_bottom_strip_andealr_details_activity);

                dealTittle = (TextView) textStrip.findViewById(R.id.deal_tittle_text_andealr_fragment);
                dealTittle.setText(Constants.landingFragmentDetail.get(0).getDealTittle());


                dealDescription = (TextView) textStrip.findViewById(R.id.deal_description_text_andealr_fragment);
                dealDescription.setText(Constants.landingFragmentDetail.get(0).getDealCity());


                likesRedeemStrip = (RelativeLayout) findViewById(R.id.details_bottom_strip_andealr_details_activity);
                likesCounter = (TextView) likesRedeemStrip.findViewById(R.id.likes_counter_text_include);

                start= (TextView) findViewById(R.id.start_static_text);
                start.setText(Constants.landingFragmentDetail.get(0).getDealStart());

                end= (TextView) findViewById(R.id.end_static_text);
                end.setText(Constants.landingFragmentDetail.get(0).getDealEnd());

                likesCounter.setText(Constants.landingFragmentDetail.get(0).getLikes());

                redeemCounter = (TextView) likesRedeemStrip.findViewById(R.id.redeem_counter_text_include);
                redeemCounter.setText(Constants.landingFragmentDetail.get(0).getRedeem());

                reachCounter = (TextView) likesRedeemStrip.findViewById(R.id.reach_counter_text_include);
                reachCounter.setText(Constants.landingFragmentDetail.get(0).getReach());

                longDescription = (TextView) findViewById(R.id.deals_details_text_andnrby_fragment_details_activity);
                longDescription.setText(Constants.landingFragmentDetail.get(0).getDealDescription());





            }
        }).execute(finalUrl);


        setContentView(R.layout.activity_deals_details);





    }

}
