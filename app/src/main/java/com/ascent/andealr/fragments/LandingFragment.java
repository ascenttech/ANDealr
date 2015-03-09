package com.ascent.andealr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ascent.andealr.activities.AddDealActivity;
import com.ascent.andealr.R;
import com.ascent.andealr.adapters.LandingFragmentAdapter;
import com.ascent.andealr.async.FetchDealAsyncTask;
import com.ascent.andealr.data.LandingFragmentData;
import com.ascent.andealr.utils.Constants;
import java.util.ArrayList;
import java.util.Collections;

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
    //    ProgressDialog dialog;
    String finalUrl = "http://andnrbytest190215.ascentinc.in/merchantDeals.php?merchantID=1";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.landing_fragment,null);
        landingFragmentRecyclerView = (RecyclerView) rootView.findViewById(R.id.landing_recycler_view);

        landingFragmentLayoutManager = new LinearLayoutManager(getActivity());
        landingFragmentRecyclerView.setLayoutManager(landingFragmentLayoutManager);
        landingFragmentRecyclerView.setHasFixedSize(true);

        new FetchDealAsyncTask(getActivity().getApplicationContext(),new FetchDealAsyncTask.FetchDealAsyncTaskCallback() {
            @Override
            public void onStart(boolean a) {

                Toast.makeText(getActivity().getApplicationContext(),"GEtting deals for you",5000).show();
//
//                dialog = new ProgressDialog(getActivity());
//                dialog.setTitle("Getting your deals");
//                dialog.setMessage("Loading,Please Wait");
//                dialog.show();
//                dialog.setCancelable(false);
            }

            @Override
            public void onResult(boolean b) {

                if (b) {
                    Toast.makeText(getActivity().getApplicationContext(),"YOur Deals",5000).show();
                    landingFragmentAdapter = new LandingFragmentAdapter(Constants.landingFragmentData,getActivity().getApplicationContext());
                    landingFragmentRecyclerView.setAdapter(landingFragmentAdapter);
//                    dialog.dismiss();
                }
                else{
//                    dialog.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(),"Try Again Later",5000).show();
                }
            }
        }).execute(finalUrl);

        return rootView;
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//
//    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.addItemBtn :
                    Intent i = new Intent(getActivity(),AddDealActivity.class);
                    startActivityForResult(i,10);
                    landingFragmentAdapter = new LandingFragmentAdapter(Constants.landingFragmentData,getActivity().getApplicationContext());
                    landingFragmentRecyclerView.setAdapter(landingFragmentAdapter);
                    break;

            }
        }
    } ;

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 10){
//
//            dealName = data.getStringExtra("Name");
//            dealDescription = data.getStringExtra("Description");
//            startYear = data.getIntExtra("StartYear",0);
//            startMonth = data.getIntExtra("StartMonth",0);
//            startDay = data.getIntExtra("StartDay",0);
//            startHour = data.getIntExtra("StartHour",0);
//            startMinutes = data.getIntExtra("StartMinutes",0);
//            endYear = data.getIntExtra("EndYear",0);
//            endMonth = data.getIntExtra("EndMonth",0);
//            endDay = data.getIntExtra("EndDay",0);
//            endHour = data.getIntExtra("EndHour",0);
//            endMinutes = data.getIntExtra("EndMinutes",0);
//
//
//            Constants.landingFragmentData.add(new LandingFragmentData(
//                    dealName,dealName,dealDescription
//            ));
//
//
//            Collections.reverse(Constants.landingFragmentData);
//
//        }
//    }
}
