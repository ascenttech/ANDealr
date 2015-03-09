package com.ascent.andealr.async;

import android.content.Context;
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
import org.json.JSONObject;

/**
 * Created by ADMIN on 07-03-2015.
 */
public class FetchDealDetailsAsyncTask extends AsyncTask<String,Void,Boolean> {


    Context context;
    public FetchDealDetailsCallback listener;

    HttpEntity responseEntity;
    String responseString;

    public interface FetchDealDetailsCallback{

        public void onStart(boolean a);
        public void onResult(boolean b);

    }

    public FetchDealDetailsAsyncTask(Context context, FetchDealDetailsCallback listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);
        Log.d("Andealr","Entered the Fetch Deal Details async task");
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

                JSONArray jsonArray = jsonObject.getJSONArray("dealDetails");
                int arrayLength = jsonArray.length();
                Log.d("Andealr", "ARRAY LENGTH" + arrayLength);

                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);

                String dealId = nestedJsonObject.getString("dealID");
                String dealTittle = nestedJsonObject.getString("dealTitle");
                String dealDescription = nestedJsonObject.getString("dealDescription");
                String city = nestedJsonObject.getString("city");

                int likes = nestedJsonObject.getInt("noOfLikes");
                int redeem = nestedJsonObject.getInt("noOfRedeems");
                String photoURL = nestedJsonObject.getString("photoURL");

                // dealId is converted into INT
                int id = Integer.parseInt(dealId);

                Constants.landingFragmentData.get(id).setDealDescription(dealDescription);
                Constants.landingFragmentData.get(id).setLikes(likes);
                Constants.landingFragmentData.get(id).setRedeem(redeem);

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
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        listener.onResult(result);
    }
}
