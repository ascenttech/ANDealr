package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.activities.DealDetailsActivity;
import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 23-12-2014.
 */
public class LandingFragmentAdapter extends RecyclerView.Adapter<LandingFragmentAdapter.ViewHolder> {


    ArrayList<LandingFragmentData> landingFragmentData;
    Context context;
    TextView dealTittle,dealDescription;
    ImageView cardBackground;
    ImageLoader imageLoader;
    RelativeLayout likesRedeemLayout;
    TextView likesCounter,redeemCounter,reachCounter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LandingFragmentAdapter(ArrayList<LandingFragmentData> landingFragmentData, Context context) {
        this.landingFragmentData = landingFragmentData;
        this.context = context;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LandingFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_landing_fragment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        cardBackground = (ImageView) holder.view.findViewById(R.id.card_background_image_andealr_fragment);
        dealTittle = (TextView) holder.view.findViewById(R.id.deal_tittle_text_andealr_fragment);
        dealDescription = (TextView) holder.view.findViewById(R.id.deal_description_text_andealr_fragment);

        likesRedeemLayout = (RelativeLayout) holder.view.findViewById(R.id.likes_redeem_included_landing_fragment);
        likesCounter = (TextView) likesRedeemLayout.findViewById(R.id.likes_counter_text_include);
        redeemCounter = (TextView) likesRedeemLayout.findViewById(R.id.redeem_counter_text_include);
        reachCounter = (TextView) likesRedeemLayout.findViewById(R.id.reach_counter_text_include);




        imageLoader.DisplayImage(Constants.landingFragmentData.get(position).getPhotoURL(), cardBackground);
        dealTittle.setText(Constants.landingFragmentData.get(position).getDealTittle());
        dealDescription.setText(Constants.landingFragmentData.get(position).getCity());
        likesCounter.setText(Constants.landingFragmentData.get(position).getLikes());
        redeemCounter.setText(Constants.landingFragmentData.get(position).getRedeem());
        reachCounter.setText(Constants.landingFragmentData.get(position).getReach());



        cardBackground.setTag("Card_"+position);
        dealTittle.setTag("Tittle_"+position);
        dealDescription.setTag("Description_"+position);


        cardBackground.setOnClickListener(listener);



    }

    @Override
    public int getItemCount() {
        return Constants.landingFragmentData.size();
    }

    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String tag = (String) v.getTag();
            String identifier [] = tag.split("_");

            Intent i;

            switch (v.getId()){

                case R.id.card_background_image_andealr_fragment: i = new Intent(context, DealDetailsActivity.class);
                    i.putExtra("position",identifier[1]);
                    i.putExtra("deal id",Constants.landingFragmentData.get(Integer.parseInt(identifier[1])).getDealId());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
            }

        }
    };

}
