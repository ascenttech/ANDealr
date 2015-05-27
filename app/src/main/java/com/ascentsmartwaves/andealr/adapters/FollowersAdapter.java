package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.FollowersData;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    ArrayList<FollowersData> followersData;
    Context context;
    TextView followerName,followerHandle;
    ImageView followerProfilePic;
    ImageLoader imageLoader;
    String handle;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FollowersAdapter(ArrayList<FollowersData> followersData, Context context) {
        this.followersData = followersData;
        this.context = context;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    @Override
    public FollowersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view



        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followers_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FollowersAdapter.ViewHolder holder, int position) {

        followerProfilePic = (ImageView)holder.view.findViewById(R.id.profile_pic_followers_activity);
        followerName = (TextView) holder.view.findViewById(R.id.follower_name_followers_activity);
        followerHandle = (TextView) holder.view.findViewById(R.id.follower_handle_followers_activity);

        handle=followersData.get(position).getFollowersHandle();
        if(handle.equals("Handle"))
        {
            followerHandle.setText("");
        }
        else
        {
            followerHandle.setText(handle);
        }
        followerName.setText(followersData.get(position).getFollowersName());
        imageLoader.DisplayImage(followersData.get(position).getFollowersProfilePic(), followerProfilePic);

    }

    @Override
    public int getItemCount() {
        return followersData.size();
    }
}
