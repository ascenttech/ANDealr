package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Created by ADMIN on 10-03-2015.
 */
public class RegisterMerchantAsyncTask extends AsyncTask<String, Void, Boolean> {

    Context context;
    public RegisterMerchantCallback listener;

    public interface RegisterMerchantCallback{
        void onStart(boolean a);
        void onResult(boolean b);
    }

    public RegisterMerchantAsyncTask(Context context, RegisterMerchantCallback listener) {
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        Log.d(Constants.LOG_TAG,Constants.RegisterMerchantAsyncTask);

        try {
            //------------------>>
            HttpGet httppost = new HttpGet(urls[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);
            // StatusLine stat = response.getStatusLine();

            int status = response.getStatusLine().getStatusCode();

            if (status == 200)
            {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("merchantRegistration");

                JSONObject jsonNestedObject = jsonArray.getJSONObject(0);
                Constants.merchantId=jsonNestedObject.getString("merchantId");
                Constants.merchantRegisterationStatus=jsonNestedObject.getString("city");
                Log.d("ID:",Constants.merchantId);

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);
        listener.onResult(result);

    }




}
