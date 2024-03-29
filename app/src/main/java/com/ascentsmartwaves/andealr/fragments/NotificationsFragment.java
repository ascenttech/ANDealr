package com.ascentsmartwaves.andealr.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.adapters.NotificationsFragmentAdapter;
import com.ascentsmartwaves.andealr.async.FetchNotificationsAsyncTask;
import com.ascentsmartwaves.andealr.data.NotificationsData;
import com.ascentsmartwaves.andealr.data.NotificationsDataPrevious;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 06-01-2015.
 */
public class NotificationsFragment extends Fragment {

    private RecyclerView notificationsFragmentRecyclerView;
    private RecyclerView.Adapter notificationsFragmentAdapter;
    private RecyclerView.LayoutManager notificationsFragmentLayoutManager;
    ProgressDialog dialog;
    SharedPreferences preferences;
    String id;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(Constants.LOG_TAG,Constants.NotificationsFragment);

        View rootView = inflater.inflate(R.layout.fragment_notifications,null);
        Constants.notificationsData = new ArrayList<NotificationsData>();

        notificationsFragmentRecyclerView = (RecyclerView) rootView.findViewById(R.id.notifications_recycler_view);

        notificationsFragmentLayoutManager = new LinearLayoutManager(getActivity());
        notificationsFragmentRecyclerView.setLayoutManager(notificationsFragmentLayoutManager);
        notificationsFragmentRecyclerView.setHasFixedSize(true);

        preferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = preferences.getString("Merchant Id", "NO DATA");

//        String finalURL = Constants.fetchNotificationsURL + id;
        // This is the latest URL where we send the Dealr id to obtain his notifications
        // NOw all the notifications are fetched from the server
        String finalURL = Constants.fetchNotificationsForDealerURL + id;

        new FetchNotificationsAsyncTask(getActivity().getApplicationContext(),new FetchNotificationsAsyncTask.FetchNotificationsCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Getting your Notifications");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);

            }

            @Override
            public void onResult(boolean b) {

                dialog.dismiss();
                if (b) {
//                    notificationsFragmentAdapter = new NotificationsFragmentAdapter(Constants.notificationsDataPrevious,getActivity().getApplicationContext());
//                    notificationsFragmentRecyclerView.setAdapter(notificationsFragmentAdapter);
                    notificationsFragmentAdapter = new NotificationsFragmentAdapter(Constants.notificationsData,getActivity().getApplicationContext());
                    notificationsFragmentRecyclerView.setAdapter(notificationsFragmentAdapter);

                }
                else
                {

                    AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                    builder.setTitle("Notifications");
                    builder.setMessage("No Notifications Found");
                    builder.show();
                }
            }
        }).execute(finalURL);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constants.notificationsDataPrevious = new ArrayList<NotificationsDataPrevious>();
    }

}
