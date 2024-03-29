package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


//    ArrayList<NotificationsDataPrevious> notificationsFragmentDatas;
    ArrayList<NotificationsData> notificationsFragmentDatas;
    Context context;
    TextView listDealName,listDealDescription;
    ImageView listDealImage;
    TextView dealName,dealDescription,dealDate,likes,redeem,dealTime;
    RelativeLayout likesRedeemLayout;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationsFragmentAdapter(ArrayList<NotificationsData> notificationsFragmentDatas, Context context) {

        Log.d(Constants.LOG_TAG, Constants.NotificationsFragmentAdapter);
        this.notificationsFragmentDatas = notificationsFragmentDatas;
        this.context = context;
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
    public NotificationsFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notifications_fragment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dealName = (TextView) holder.view.findViewById(R.id.deal_name_text_notifications_fragment);
        dealDate = (TextView) holder.view.findViewById(R.id.date);
        dealTime = (TextView) holder.view.findViewById(R.id.time);

        dealName.setText(Constants.notificationsData.get(position).getMessage());
        dealDate.setText(Constants.notificationsData.get(position).getDate());
        dealTime.setText(Constants.notificationsData.get(position).getTime());


    }


//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//
//        dealName = (TextView) holder.view.findViewById(R.id.deal_name_text_notifications_fragment);
//
//        if(Constants.notificationsDataPrevious.get(position).getDealStatus().equalsIgnoreCase("valid")){
//
//            dealName.setText(Constants.notificationsDataPrevious.get(position).getDealTittle()+" has started from "
//                            +Constants.notificationsDataPrevious.get(position).getStartDate()
//                            +" "
//                            +Constants.notificationsDataPrevious.get(position).getStartTime()
//            );
//        }
//        else{
//            dealName.setText(Constants.notificationsDataPrevious.get(position).getDealTittle()+" has expired on "
//                            +Constants.notificationsDataPrevious.get(position).getEndDate()
//                            +" "
//                            +Constants.notificationsDataPrevious.get(position).getEndTime()
//                            +" with "
//                            +Constants.notificationsDataPrevious.get(position).getLikesCounter()
//                            +" likes and "
//                            +Constants.notificationsDataPrevious.get(position).getRedeemCounter()
//                            +" redeems"
//            );
//        }
//
//    }


    @Override
    public int getItemCount() {
        return notificationsFragmentDatas.size();
    }

}