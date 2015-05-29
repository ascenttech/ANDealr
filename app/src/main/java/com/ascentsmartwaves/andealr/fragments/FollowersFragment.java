package com.ascentsmartwaves.andealr.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.adapters.FollowersAdapter;
import com.ascentsmartwaves.andealr.async.FetchFollowersAsyncTask;
import com.ascentsmartwaves.andealr.data.FollowersData;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class FollowersFragment extends Fragment {

    private RecyclerView followersRecyclerView;
    private RecyclerView.Adapter followersAdapter;
    private RecyclerView.LayoutManager followersLayoutManager;

    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_followers,null);
        followersRecyclerView = (RecyclerView) rootView.findViewById(R.id.followers_recycler_view);


        followersLayoutManager = new LinearLayoutManager(rootView.getContext());
        followersRecyclerView.setLayoutManager(followersLayoutManager);



        new FetchFollowersAsyncTask(getActivity().getApplicationContext(),new FetchFollowersAsyncTask.FollowersCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Getting Your Followers");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);

            }
            @Override
            public void onResult(boolean b) {

                if(b){
                    dialog.dismiss();
                    followersAdapter = new FollowersAdapter(Constants.followersData, getActivity());
                    followersRecyclerView.setAdapter(followersAdapter);

                }
                else{
                    dialog.dismiss();
                    AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                    builder.setTitle("Followers");
                    builder.setMessage("No Followers Yet");
                    builder.show();
                }

            }
        }).execute(Constants.fetchFollowersURL+Constants.merchantId);


        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constants.followersData = new ArrayList<FollowersData>();
    }

}
