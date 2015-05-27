package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.UserProfileData;
import com.ascentsmartwaves.andealr.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class UpdateProfileAsyncTask extends AsyncTask<String, Void, Boolean> {
        Context context;

/** Implement this somewhere to get the result */
public interface UpdateProfileAsyncTaskCallback {
    void onStart(boolean a);
    void onResult(boolean b) throws IOException;
}

private UpdateProfileAsyncTaskCallback mListener;


    public UpdateProfileAsyncTask(Context context, UpdateProfileAsyncTaskCallback listener)
    {
        this.context = context;
        mListener = listener; // save callback
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        mListener.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url)
    {
        Log.d(Constants.LOG_TAG, "UPDATE_USER_PROFILE_ASYNC_TASK");
        try {
            //------------------>>
            HttpGet httppost = new HttpGet(url[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);
            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            if (status == 200)
            {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("LOG", " " + data);
                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("merchantProfile");
                for (int i = 0; i < jarray.length(); i++)
                {
                    JSONObject object = jarray.getJSONObject(i);
                    String updateresult = object.getString("merchantProfile");
                }
                return true;
            }
            else if(status == 201)
            {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("LOG", " " + data);
                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("merchantProfile");
                for (int i = 0; i < jarray.length(); i++)
                {
                    JSONObject object = jarray.getJSONObject(i);
                    Constants.field = object.getString("field");
                }
                return false;
            }
            else if(status == 202)
            {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("LOG", " " + data);
                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("merchantProfile");
                for (int i = 0; i < jarray.length(); i++)
                {
                    JSONObject object = jarray.getJSONObject(i);
                    Constants.field = object.getString("field");
                }
                return false;
            }
            //------------------>>
        } catch (Exception e1)
        {
            e1.printStackTrace();
        }
        return false;
    }
    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        try
        {
            mListener.onResult(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}