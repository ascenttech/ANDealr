package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ADMIN on 04-03-2015.
 */
public class FetchDealAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    HttpEntity responseEntity;
    String responseString;
    private FetchDealAsyncTaskCallback mListener;


    /** Implement this somewhere to get the result */
    public interface FetchDealAsyncTaskCallback {
        void onStart(boolean a);
        void onResult(boolean b);
    }


    public FetchDealAsyncTask(Context context,FetchDealAsyncTaskCallback listener) {
        this.context = context;
        mListener = listener; // save callback
        Log.d(Constants.LOG_TAG,Constants.FetchDealAsyncTask);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onStart(true);
    }


    @Override
    protected Boolean doInBackground(String... url) {


        Log.d(Constants.LOG_TAG,"The requested url is "+url[0]);

        try {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);

                JSONObject jsonObject = new JSONObject(responseString);

                JSONArray jsonArray = jsonObject.getJSONArray("dealDetails");

                for(int i = 0;i< jsonArray.length();i++){

                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);

                    String dealId = nestedJsonObject.getString("dealID");
                    String dealTittle = nestedJsonObject.getString("dealTitle");
                    String city = nestedJsonObject.getString("city");
                    int likes = nestedJsonObject.getInt("noOfLikes");
                    int redeem = nestedJsonObject.getInt("noOfRedeems");
                    String encashed = nestedJsonObject.getString("noOfEncashes");
                    String photoURL = nestedJsonObject.getString("photoURL");

                    // new field added reach
                    String reach = nestedJsonObject.getString("shownCount");

                    String likesString = String.valueOf(likes);
                    String redeemString = String.valueOf(redeem);

                    Constants.landingFragmentData.add(new LandingFragmentData(dealId,dealTittle,city,likesString,redeemString,photoURL,reach,encashed));
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
