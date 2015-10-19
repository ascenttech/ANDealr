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
 * Created by ADMIN on 04-03-2015.
 */
public class AddDealAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    private AddDealAsyncTaskCallback mListener;

    /** Implement this somewhere to get the result */
    public interface AddDealAsyncTaskCallback {
        void onStart(boolean a);
        void onResult(boolean b);
    }

    public AddDealAsyncTask(Context context,AddDealAsyncTaskCallback listener) {
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

        Log.d(Constants.LOG_TAG,Constants.AddDealAsyncTask);
        Log.d(Constants.LOG_TAG,"The requested url is "+url[0]);

        try {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {

                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                Log.d(Constants.LOG_TAG," The response is "+data);
                JSONObject jsonObject = new JSONObject(data);
                String dealStatus = jsonObject.getString("statusMessage");
                JSONArray jsonArray = jsonObject.getJSONArray("deals");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);
                boolean deal = nestedJsonObject.getBoolean("deal");
                int dealId = nestedJsonObject.getInt("dealID");

                // This is the latest update
                int balance = nestedJsonObject.getInt("balance");
                Constants.balance = balance;

                return true;
            }
            else if(status == 203){

               return false;
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
        Log.d(Constants.LOG_TAG," The value returned is "+result);
        mListener.onResult(result);
    }
}

