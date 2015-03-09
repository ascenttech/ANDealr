package com.ascent.andealr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ascent.andealr.R;
import com.ascent.andealr.activities.LandingActivity;
import com.ascent.andealr.adapters.FollowersAdapter;
import com.ascent.andealr.data.FollowersData;
import com.ascent.andealr.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class FollowersFragment extends Fragment {

    private RecyclerView followersRecyclerView;
    private RecyclerView.Adapter followersAdapter;
    private RecyclerView.LayoutManager followersLayoutManager;
    String firstName[],lastName[],photo[],userHandle[]=null;
    ActionBar  actionBar;
    int cou;
    String id;
    public FollowersFragment(){ }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.followers,null);
        followersRecyclerView = (RecyclerView) rootView.findViewById(R.id.followers_recycler_view);


        followersLayoutManager = new LinearLayoutManager(rootView.getContext());
        followersRecyclerView.setLayoutManager(followersLayoutManager);
        followersRecyclerView.setHasFixedSize(true);

//        Constants.followersData = new ArrayList<FollowersData>();
//        for (int i = 0;i<5;i++){
//
//            Constants.followersData.add(new FollowersData(
//                    "Tittle" + i, "Description" + i, R.drawable.ic_launcher
//            ));
//        }
//
//        followersAdapter = new FollowersAdapter(Constants.followersData,rootView.getContext());
//        followersRecyclerView.setAdapter(followersAdapter);
        SharedPreferences prefs = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = prefs.getString("Merchant Id", "NO DATA");
        new FollowersAsyncTask().execute("http://andnrbytest190215.ascentinc.in/json_followers.php?merchantID="+id);


        return rootView;
    }


    class FollowersAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httpget = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.d("LOG", " " + data);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("followers");
                    cou=jarray.length();

                    // mobile_no=new String[cou];
                    firstName=new String[cou];
                    lastName=new String[cou];
                    photo=new String[cou];
                    userHandle=new String[cou];
                    for (int i = 0; i < jarray.length(); i++)
                    {
                        JSONObject object = jarray.getJSONObject(i);
                        firstName[i]=object.getString("firstName");
                        lastName[i] = object.getString("lastName");
                        photo[i] = object.getString("photo");
                        userHandle[i] = object.getString("userHandle");

                    }

                    return true;
                }
                else if(status == 201)
            {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("LOG", " " + data);


                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("followers");
                int cou = jarray.length();

                // mobile_no=new String[cou];
                firstName = new String[cou];
                for (int i = 0; i < jarray.length(); i++)
                {
                    JSONObject object = jarray.getJSONObject(i);
                    firstName[i] = object.getString("companyFollowers");
                }
            }
            //------------------>>
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
        }

        protected void onPostExecute(Boolean result) {


            if(firstName[0].equals("noFollowers"))
            {
                Toast.makeText(getActivity().getApplicationContext(), "No followers yet", Toast.LENGTH_LONG).show();
            }
            else
            {
                Constants.followersData = new ArrayList<FollowersData>();
                for (int i = 0; i < cou; i++)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "" + cou, Toast.LENGTH_LONG).show();
                    Constants.followersData.add(new FollowersData(
                            firstName[i], userHandle[i],  photo[i]
                    ));
                }
                followersAdapter = new FollowersAdapter(Constants.followersData, getActivity());
                followersRecyclerView.setAdapter(followersAdapter);
            }

            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }


}
