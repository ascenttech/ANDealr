package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;

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

                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray jsonArray = jsonObject.getJSONArray("dealDetails");

                for(int i = 0;i< jsonArray.length();i++){

                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);

                    String dealId = nestedJsonObject.getString("dealID");
                    String dealTittle = nestedJsonObject.getString("dealTitle");
                    String city = nestedJsonObject.getString("city");
                    int likes = nestedJsonObject.getInt("noOfLikes");
                    int redeem = nestedJsonObject.getInt("noOfRedeems");
                    String photoURL = nestedJsonObject.getString("photoURL");

                    String likesString = String.valueOf(likes);
                    String redeemString = String.valueOf(redeem);

                    Constants.landingFragmentData.add(new LandingFragmentData(dealId,dealTittle,city,likesString,redeemString,photoURL));
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
