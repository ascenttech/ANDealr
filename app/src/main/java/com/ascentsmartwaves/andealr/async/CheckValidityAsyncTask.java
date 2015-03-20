package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by ADMIN on 07-03-2015.
 */
public class CheckValidityAsyncTask extends AsyncTask<String,Void,Boolean>{

    Context context;
    HttpEntity responseEntity;
    String responseString;

    public interface CheckValidityCallback{

        public void onStart(boolean a);
        public void onResult(boolean b);
    }

    private CheckValidityCallback listener;

    public CheckValidityAsyncTask(Context context, CheckValidityCallback listener) {
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);

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
                JSONArray jsonArray = jsonObject.getJSONArray("redeemStatus");

                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);

                String dealStatus = nestedJsonObject.getString("deal");

                if(dealStatus.equalsIgnoreCase("valid")){
                    Constants.dealValidity = true;
                }
                else{
                    Constants.dealValidity = false;
                }
            }
            return true;

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