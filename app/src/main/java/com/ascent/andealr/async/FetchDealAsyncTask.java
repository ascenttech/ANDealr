package com.ascent.andealr.async;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import com.ascent.andealr.data.LandingFragmentData;
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

/**
 * Created by ADMIN on 04-03-2015.
 */
public class FetchDealAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    HttpEntity responseEntity;
    String responseString;


    /** Implement this somewhere to get the result */
    public interface FetchDealAsyncTaskCallback {
        void onStart(boolean a);
        void onResult(boolean b);
    }

    private FetchDealAsyncTaskCallback mListener;


    public FetchDealAsyncTask(Context context,FetchDealAsyncTaskCallback listener) {
        this.context = context;
        mListener = listener; // save callback

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onStart(true);
    }


    @Override
    protected Boolean doInBackground(String... url) {

        try {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);
                Log.d("Andealr", " " + responseString);

                JSONObject jsonObject = new JSONObject(responseString);
//
//                String dealStatus = jsonObject.getString("statusMessage");
//                Log.d("Andealr", "Deal Status" + dealStatus);

                JSONArray jsonArray = jsonObject.getJSONArray("dealDetails");
                int arrayLength = jsonArray.length();
                Log.d("Andealr", "ARRAY LENGTH" + arrayLength);

                for(int i = 0;i< arrayLength;i++){
                    Log.d("Andealr", "Inside For loop");
                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);

                    String dealId = nestedJsonObject.getString("dealID");
                    String dealTittle = nestedJsonObject.getString("dealTitle");
                    String city = nestedJsonObject.getString("city");
                    int likes = nestedJsonObject.getInt("noOfLikes");
                    int redeem = nestedJsonObject.getInt("noOfRedeems");
                    String photoURL = nestedJsonObject.getString("photoURL");

                    Constants.landingFragmentData.add(new LandingFragmentData(dealId,dealTittle,city,likes,redeem,photoURL));
                    Log.d("Andealr", "SIZE OF LANDING DATA"+ Constants.landingFragmentData.size());
                }
                return true;
            }
            //------------------>>
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        mListener.onResult(result);

    }

}
