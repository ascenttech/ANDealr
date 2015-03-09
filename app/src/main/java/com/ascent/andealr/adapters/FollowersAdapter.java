package com.ascent.andealr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascent.andealr.R;
import com.ascent.andealr.data.FollowersData;
import com.ascent.andealr.imagecaching.ImageLoader;
import com.ascent.andealr.utils.Constants;

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

        followerHandle.setText(followersData.get(position).getFollowersHandle());
        followerHandle.setTypeface(Constants.customFont1);
        followerName.setText(followersData.get(position).getFollowersName());
        followerName.setTypeface(Constants.customFont1);
        imageLoader.DisplayImage(followersData.get(position).getFollowersProfilePic(), followerProfilePic);

    }

    @Override
    public int getItemCount() {
        return followersData.size();
    }
}
