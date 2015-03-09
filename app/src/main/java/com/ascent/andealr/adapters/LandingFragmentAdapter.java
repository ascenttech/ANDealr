package com.ascent.andealr.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascent.andealr.R;
import com.ascent.andealr.imagecaching.ImageLoader;
import com.ascent.andealr.activities.DealsDetailsActivity;
import com.ascent.andealr.data.LandingFragmentData;
import com.ascent.andealr.utils.Constants;

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
                .inflate(R.layout.landing_fragment_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        cardBackground = (ImageView) holder.view.findViewById(R.id.card_background_image_andealr_fragment);
        dealTittle = (TextView) holder.view.findViewById(R.id.deal_tittle_text_andealr_fragment);
        dealDescription = (TextView) holder.view.findViewById(R.id.deal_description_text_andealr_fragment);

//        cardBackground.setImageResource(imageLoader.DisplayImage(data[position], image));
//        imageLoader.DisplayImage(Constants.landingFragmentData.get(position).getCardBackground(), cardBackground);
        imageLoader.DisplayImage(Constants.landingFragmentData.get(position).getPhotoURL(), cardBackground);
        dealTittle.setText(Constants.landingFragmentData.get(position).getDealTittle());
        dealDescription.setText(Constants.landingFragmentData.get(position).getCity());

        cardBackground.setTag("Card_"+position);
        dealTittle.setTag("Tittle_"+position);
        dealDescription.setTag("Description_"+position);


        cardBackground.setOnClickListener(listener);



//        listDealImage = (ImageView)holder.view.findViewById(R.id.list_item_deal_image_landing_fragment);
//        listDealName = (TextView) holder.view.findViewById(R.id.list_item_deal_landing_fragment);
//        listDealDescription = (TextView) holder.view.findViewById(R.id.list_item_deal_description_landing_fragment);
//
//        listDealName.setText(landingFragmentData.get(position).getDealTittle());
//        listDealName.setTypeface(Constants.customFont);
//        listDealDescription.setText(landingFragmentData.get(position).getDealDescription());
//        listDealDescription.setTypeface(Constants.customFont);
//        listDealImage.setImageResource(landingFragmentData.get(position).getCardBackground());


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

                case R.id.card_background_image_andealr_fragment: i = new Intent(context, DealsDetailsActivity.class);
                    i.putExtra("position",identifier[1]);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
            }

        }
    };

}
