package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.utils.AndroidMultiPartEntity;
import com.ascentsmartwaves.andealr.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by ADMIN on 02-04-2015.
 */
public class UploadProfileImageAsyncTask extends AsyncTask<String,Void,Boolean>
{
    Context context;
    public UploadProfileImageAsyncTaskCallback listener;
    long totalSize = 0;
    String status,imagePath;
    File sourceFile;

    public interface UploadProfileImageAsyncTaskCallback
    {
        public void onStart(boolean a);
        public void onResult(boolean b);

    }

    public UploadProfileImageAsyncTask(String imagePath,Context context, UploadProfileImageAsyncTaskCallback listener)
    {
        this.context = context;
        this.listener = listener;
        this.imagePath = imagePath;
        Log.d(Constants.LOG_TAG,Constants.UploadProfileImageAsyncTask);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        listener.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        Log.d(Constants.LOG_TAG,"The requested url is "+urls[0]);

        try
        {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.FILE_UPLOAD_URL_MERCHANTPROFILE+Constants.merchantId);
            Log.d(Constants.LOG_TAG,"url: "+Constants.FILE_UPLOAD_URL_MERCHANTPROFILE+Constants.merchantId);
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num)
                        {

                        }
                    });
            if(imagePath!=null)
            {
                sourceFile = new File(imagePath);
            }
            else
            {
                sourceFile=new File("NO DATA");
            }
            // Adding file data to http body
            entity.addPart("image", new FileBody(sourceFile));
            totalSize = entity.getContentLength();
            httppost.setEntity(entity);
            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
            {
                // Server response
                responseString = EntityUtils.toString(r_entity);
                Log.d(Constants.LOG_TAG, "RESPONSE STRING " + responseString);
                try
                {
                    JSONObject jsonObject = new JSONObject(responseString);
                    status = jsonObject.getString("statusMessage");
                    JSONArray jsonArray = jsonObject.getJSONArray("photo");
                    JSONObject jObject = jsonArray.getJSONObject(0);
                    String res = jObject.getString("updatePhoto");
                }
                catch(Exception e)
                {
                    // do something
                }
                return true;
            }
            else if(statusCode == 201)
            {
                // Server response
                responseString = EntityUtils.toString(r_entity);
                Log.d(Constants.LOG_TAG, "RESPONSE STRING " + responseString);
                try
                {
                    JSONObject jsonObject = new JSONObject(responseString);
                    status = jsonObject.getString("statusMessage");
                    JSONArray jsonArray = jsonObject.getJSONArray("photo");
                    JSONObject jObject = jsonArray.getJSONObject(0);
                    String res = jObject.getString("updatePhoto");
                }
                catch(Exception e)
                {
                    // do something
                }
                return true;
            }
            else
            {
                responseString = "Error occurred! Http Status Code: "+ statusCode;
                Log.d(Constants.LOG_TAG, "responseString " + responseString);
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        listener.onResult(result);
    }
}
