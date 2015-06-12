package com.ascentsmartwaves.andealr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.activities.AddDealActivity;
import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.adapters.LandingFragmentAdapter;
import com.ascentsmartwaves.andealr.async.FetchDealAsyncTask;
import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.utils.Constants;
import java.util.ArrayList;

/**
 * Created by ADMIN on 22-12-2014.
 */
public class LandingFragment extends Fragment {

    private RecyclerView landingFragmentRecyclerView;
    private RecyclerView.Adapter landingFragmentAdapter;
    private RecyclerView.LayoutManager landingFragmentLayoutManager;
    private Button add;
    int i,j;
    Fragment fragment;
    String dealName,dealDescription;
    private int startMonth,startDay,startYear,startHour,startMinutes,endMonth,endDay,endYear,endHour,endMinutes;
    private int month,day,year,hour,minutes;

    SharedPreferences prefs;
    String id;
    ProgressDialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.d(Constants.LOG_TAG,Constants.LandingFragment);

        View rootView = inflater.inflate(R.layout.fragment_landing,null);
        landingFragmentRecyclerView = (RecyclerView) rootView.findViewById(R.id.landing_recycler_view);

        landingFragmentLayoutManager = new LinearLayoutManager(getActivity());
        landingFragmentRecyclerView.setLayoutManager(landingFragmentLayoutManager);
        landingFragmentRecyclerView.setHasFixedSize(true);


        prefs = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = prefs.getString("Merchant Id", "NO DATA");

        String finalUrl = Constants.fetchDealURL + id;

        new FetchDealAsyncTask(getActivity().getApplicationContext(),new FetchDealAsyncTask.FetchDealAsyncTaskCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Getting your deals");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);
            }

            @Override
            public void onResult(boolean b) {
                dialog.dismiss();
                if (b) {

                    landingFragmentAdapter = new LandingFragmentAdapter(Constants.landingFragmentData,getActivity().getApplicationContext());
                    landingFragmentRecyclerView.setAdapter(landingFragmentAdapter);
                }
                else{

                    Toast.makeText(getActivity().getApplicationContext(),"No Deals Added Yet",5000).show();
                }
            }
        }).execute(finalUrl);

        return rootView;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.addItemBtn :
                    Log.d(Constants.LOG_TAG,"Calling the Click Listener for plus button");
                    Intent i = new Intent(getActivity(),AddDealActivity.class);
                    startActivityForResult(i,10);
                    landingFragmentAdapter = new LandingFragmentAdapter(Constants.landingFragmentData,getActivity().getApplicationContext());
                    landingFragmentRecyclerView.setAdapter(landingFragmentAdapter);
                    break;

            }
        }
    } ;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();
    }



}
