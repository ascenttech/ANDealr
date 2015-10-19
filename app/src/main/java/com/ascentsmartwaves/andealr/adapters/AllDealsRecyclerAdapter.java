package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.AllDealsData;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.activities.DealDetailsActivity;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 23-12-2014.
 */
public class AllDealsRecyclerAdapter extends RecyclerView.Adapter<AllDealsRecyclerAdapter.ViewHolder> {


    ArrayList<AllDealsData> allDealsData;
    Context context;
    TextView dealTittle,dealDescription;
    ImageView cardBackground;
    ImageLoader imageLoader;
    RelativeLayout likesRedeemLayout;
    TextView likesCounter,redeemCounter,reachCounter,encashedCounter;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AllDealsRecyclerAdapter(ArrayList<AllDealsData> allDealsData, Context context) {

        Log.d(Constants.LOG_TAG, Constants.AllDealsRecyclerAdapter);

        this.allDealsData = allDealsData;
        this.context = context;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }



    // Create new views (invoked by the layout manager)
    @Override
    public AllDealsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_deals, parent, false);
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
        encashedCounter = (TextView) likesRedeemLayout.findViewById(R.id.encashed_counter_text_include);



        imageLoader.DisplayImage(Constants.allDealsData.get(position).getPhotoURL(), cardBackground);
        dealTittle.setText(Constants.allDealsData.get(position).getDealTittle());
        dealDescription.setText(Constants.allDealsData.get(position).getCity());
        likesCounter.setText(Constants.allDealsData.get(position).getLikes());
        redeemCounter.setText(Constants.allDealsData.get(position).getRedeem());
        reachCounter.setText(Constants.allDealsData.get(position).getReach());
        encashedCounter.setText(Constants.allDealsData.get(position).getEncashed());


        cardBackground.setTag("Card_"+position);
        dealTittle.setTag("Tittle_"+position);
        dealDescription.setTag("Description_"+position);


        cardBackground.setOnClickListener(listener);



    }

    @Override
    public int getItemCount() {
        return Constants.allDealsData.size();
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
                    i.putExtra("deal id",Constants.allDealsData.get(Integer.parseInt(identifier[1])).getDealId());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
            }

        }
    };

}
