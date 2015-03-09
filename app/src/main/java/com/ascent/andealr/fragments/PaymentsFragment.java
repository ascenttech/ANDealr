package com.ascent.andealr.fragments;


import android.content.Context;
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
import com.ascent.andealr.adapters.FollowersAdapter;
import com.ascent.andealr.adapters.PaymentsAdapter;
import com.ascent.andealr.data.FollowersData;
import com.ascent.andealr.data.PaymentsData;
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
public class PaymentsFragment extends Fragment {

    private RecyclerView paymentsRecyclerView;
    private RecyclerView.Adapter paymentsAdapter;
    private RecyclerView.LayoutManager paymentsLayoutManager;
    int cou;
    String id;
    String dealTitle[],success[],timestamp[],orderID[],amount[]=null;

    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.payment,null);

        paymentsRecyclerView = (RecyclerView) rootView.findViewById(R.id.payments_recycler_view);


        paymentsLayoutManager = new LinearLayoutManager(rootView.getContext());
        paymentsRecyclerView.setLayoutManager(paymentsLayoutManager);
        paymentsRecyclerView.setHasFixedSize(true);

//        Constants.paymentsData = new ArrayList<PaymentsData>();
//        for (int i = 0;i<5;i++){
//
//            Constants.paymentsData.add(new PaymentsData(
//                    "Tittle" + i, "Description" + i, (int)Math.random()
//            ));
//        }
//        paymentsAdapter = new PaymentsAdapter(Constants.paymentsData, rootView.getContext());
//        paymentsRecyclerView.setAdapter(paymentsAdapter);

        SharedPreferences prefs = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = prefs.getString("Merchant Id", "NO DATA");
        new PaymentAsyncTask().execute("http://andnrbytest190215.ascentinc.in/paymentDetails.php?merchantID="+id);
        return rootView;
    }



    class PaymentAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.d("LOG", " " + data);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("payment");
                    cou=jarray.length();



                    orderID=new String[cou];
                    dealTitle=new String[cou];
                    amount=new String[cou];
                    success=new String[cou];
                    timestamp=new String[cou];
                    for (int i = 0; i < jarray.length(); i++)
                    {
                        JSONObject object = jarray.getJSONObject(i);
                        orderID[i]=object.getString("orderID");
                        dealTitle[i]=object.getString("dealTitle");
                        amount[i]=object.getString("amount");
                        success[i]=object.getString("success");
                        timestamp[i]=object.getString("timestamp");
                    }

                    return true;
                }
                else if (status==201)
                {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.d("LOG", " " + data);
                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("payment");
                    cou=jarray.length();

                    orderID=new String[cou];
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        orderID[i] = object.getString("paymentResult");
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


            Constants.followersData = new ArrayList<FollowersData>();
            if(orderID[0].equals("noPayments"))
            {
                Toast.makeText(getActivity().getApplicationContext(), "NO PAYMENT DETAILS YET!!!" , Toast.LENGTH_LONG).show();
            }
            else
            {
                for (int i = 0; i < cou; i++) {
                    Toast.makeText(getActivity().getApplicationContext(), "" + dealTitle[i], Toast.LENGTH_LONG).show();
                    Constants.paymentsData = new ArrayList<PaymentsData>();
                    Constants.paymentsData.add(new PaymentsData(
                            "Order No." + orderID[i], dealTitle[i], amount[i], success[i], timestamp[i]
                    ));

                }
                paymentsAdapter = new PaymentsAdapter(Constants.paymentsData, getActivity());
                paymentsRecyclerView.setAdapter(paymentsAdapter);
            }

            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }

}
