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


public class CheckProfileValidityAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    HttpEntity responseEntity;
    String responseString;
    private ProfileValidAsyncTaskCallback listener;

    public interface ProfileValidAsyncTaskCallback{

        public void onStart(boolean a);
        public void onResult(boolean b);
    }


    public CheckProfileValidityAsyncTask(Context context, ProfileValidAsyncTaskCallback listener) {
        this.context = context;
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);

    }

    @Override
    protected Boolean doInBackground(String... url)
    {
        Log.d(Constants.LOG_TAG,Constants.CheckProfileValidityAsyncTask);

        try
        {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);
            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG,"THIS IS THE RESPONSE: "+response);
            if (status == 200)
            {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);
                Log.d("Andealr", " " + responseString);
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray jsonArray = jsonObject.getJSONArray("profileDetails");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);
                Constants.accountStatus = nestedJsonObject.getString("profile");
                return true;
            }
            else if (status == 201)
            {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);
                Log.d("Andealr", " " + responseString);
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray jsonArray = jsonObject.getJSONArray("profileDetails");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);
                Constants.accountStatus = nestedJsonObject.getString("profile");
                return true;
            }
            else if (status == 202)
            {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);
                Log.d("Andealr", " " + responseString);
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray jsonArray = jsonObject.getJSONArray("profileDetails");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);
                Constants.accountStatus = nestedJsonObject.getString("profile");
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        listener.onResult(result);
    }
}
