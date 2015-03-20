package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.NotificationsData;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 06-01-2015.
 */
public class NotificationsFragmentAdapter extends RecyclerView.Adapter<NotificationsFragmentAdapter.ViewHolder> {


    ArrayList<NotificationsData> notificationsFragmentDatas;
    Context context;
    TextView listDealName,listDealDescription;
    ImageView listDealImage;
    TextView dealName,dealDescription,dealDate,likes,redeem;
    RelativeLayout likesRedeemLayout;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationsFragmentAdapter(ArrayList<NotificationsData> notificationsFragmentDatas, Context context) {
        this.notificationsFragmentDatas = notificationsFragmentDatas;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotificationsFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_fragment_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dealName = (TextView) holder.view.findViewById(R.id.deal_name_text_notifications_fragment);

        if(Constants.notificationsData.get(position).getDealStatus().equalsIgnoreCase("valid")){

            dealName.setText(Constants.notificationsData.get(position).getDealTittle()+" has started from "
                            +Constants.notificationsData.get(position).getStartDate()
                            +" "
                            +Constants.notificationsData.get(position).getStartTime()
            );
        }
        else{
            dealName.setText(Constants.notificationsData.get(position).getDealTittle()+" has expired on "
                            +Constants.notificationsData.get(position).getEndDate()
                            +" "
                            +Constants.notificationsData.get(position).getEndTime()
                            +" with "
                            +Constants.notificationsData.get(position).getLikesCounter()
                            +" likes and "
                            +Constants.notificationsData.get(position).getRedeemCounter()
                            +" redeems"
            );
        }

    }


    @Override
    public int getItemCount() {
        return notificationsFragmentDatas.size();
    }

}