package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
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
public class FetchProfileAsyncTask extends AsyncTask<String, Void, Boolean> {


    Context context;
    public FetchProfileCallback listener;
    ImageLoader imageLoader;
    String fname, lname, photo;


    public interface FetchProfileCallback {

        public void onStart(boolean a);
        public void onResult(boolean b);

    }


    public FetchProfileAsyncTask(Context context, FetchProfileCallback listener) {
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
        try {

            //------------------>>
            HttpGet httppost = new HttpGet(urls[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("LOG", " " + data);


                JSONObject jsono = new JSONObject(data);
                JSONArray jarray = jsono.getJSONArray("merchantProfile");
                    JSONObject object = jarray.getJSONObject(0);
                    Constants.fname = object.getString("firstName");
                    Constants.lname = object.getString("lastName");
                    Constants.photolandingimg = object.getString("photo");

                return true;
            }
            //------------------>>
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