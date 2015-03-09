package com.ascent.andealr.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ADMIN on 04-03-2015.
 */
public class AddDealAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;

    /** Implement this somewhere to get the result */
    public interface AddDealAsyncTaskCallback {
        void onStart(boolean a);
        void onResult(boolean b);
    }

    private AddDealAsyncTaskCallback mListener;


    public AddDealAsyncTask(Context context,AddDealAsyncTaskCallback listener) {
        this.context = context;
        mListener = listener; // save callback

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onStart(true);
        Log.d("Andealr","exiting pre fetch");
    }

    @Override
    protected Boolean doInBackground(String... url) {
//        Log.d("Andealr","entering doInBackground");
        try {

//            Log.d("Andealr","Inside Try");
            HttpGet httpGet = new HttpGet(url[0]);
//            Log.d("Andealr","inside HTTPGET "+httpGet);

            HttpClient httpClient = new DefaultHttpClient();
//            Log.d("Andealr","inside HTTPClient "+httpClient);

            HttpResponse response = httpClient.execute(httpGet);
//            Log.d("Andealr","inside HTTPResponse "+response);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
//            Log.d("Andealr","inside status "+ status);

            if (status == 200) {

//                Log.d("Andealr","inside if of status");
                HttpEntity entity = response.getEntity();
//                Log.d("Andealr","inside HttpEntity " + entity);

                String data = EntityUtils.toString(entity);
//                Log.d("Andealr","inside data "+ data);

                JSONObject jsonObject = new JSONObject(data);
//                Log.d("Andealr","inside JSONObject " + jsonObject);

                String dealStatus = jsonObject.getString("statusMessage");
//                Log.d("Andealr","inside dealStatus "+dealStatus);

                JSONArray jsonArray = jsonObject.getJSONArray("deals");
//                Log.d("Andealr","inside JSONArray "+ jsonArray);

                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);
//                Log.d("Andealr","inside JSONNestedObject "+ nestedJsonObject);

                boolean deal = nestedJsonObject.getBoolean("deal");
//                Log.d("Andealr","inside deal " + deal);

                int dealId = nestedJsonObject.getInt("dealID");
//                Log.d("Andealr","inside dealId "+dealId );

                return true;
            }
            else if(status == 203){

//                Log.d("Andealr","Inside 203");

                HttpEntity entity = response.getEntity();
//                Log.d("Andealr","inside HttpEntity " + entity);

                String data = EntityUtils.toString(entity);
//                Log.d("Andealr","inside data "+ data);

                JSONObject jsonObject = new JSONObject(data);
//                Log.d("Andealr","inside JSONObject " + jsonObject);

                String dealStatus = jsonObject.getString("statusMessage");
//                Log.d("Andealr","inside dealStatus "+dealStatus);

                JSONArray jsonArray = jsonObject.getJSONArray("deals");
//                Log.d("Andealr","inside JSONArray "+ jsonArray);

                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);
//                Log.d("Andealr","inside JSONNestedObject "+ nestedJsonObject);


                String error = nestedJsonObject.getString("error");
//                Log.d("Andealr","inside error "+ error);

            }
            //------------------>>
        }
        catch (Exception e) {
            Log.d("Andealr","inside Parse Exception");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d("Andealr","inside on Post execute");
        mListener.onResult(result);
    }
}

