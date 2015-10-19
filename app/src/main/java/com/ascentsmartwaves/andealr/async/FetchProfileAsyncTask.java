package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.MerchantProfileData;
import com.ascentsmartwaves.andealr.data.UserProfileData;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;

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
import java.net.URISyntaxException;

/**
 * Created by ADMIN on 10-03-2015.
 */
public class FetchProfileAsyncTask extends AsyncTask<String, Void, Boolean> {
    Context context;

    /** Implement this somewhere to get the result */
    public interface FetchProfileAsyncTaskCallback {
        void onStart(boolean a);
        void onResult(boolean b) throws IOException, URISyntaxException;
    }

    private FetchProfileAsyncTaskCallback mListener;


    public FetchProfileAsyncTask(Context context, FetchProfileAsyncTaskCallback listener) {
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

        Log.d(Constants.LOG_TAG,Constants.FetchProfileAsyncTask);
        Log.d(Constants.LOG_TAG,"The requested url is "+url[0]);

        try {
            //------------------>>
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpGet);
            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                Log.d(Constants.LOG_TAG," The response is "+data);
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("profile");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);



                String companyName= nestedJsonObject.getString("companyName");
                String emailID = nestedJsonObject.getString("emailID");
                String contactNo = nestedJsonObject.getString("contactNo");
                String companyLogo = nestedJsonObject.getString("companyLogo");
                Constants.companyLogo=nestedJsonObject.getString("companyLogo");
                String locality= nestedJsonObject.getString("locality");
                String dealsPushed = nestedJsonObject.getString("dealsPushed");
                String dealLikes = nestedJsonObject.getString("dealLikes");
                String dealRedeems = nestedJsonObject.getString("dealRedeems");
                String merchantHandle=nestedJsonObject.getString("handle");
                Constants.handle=nestedJsonObject.getString("handle");
                String pincode=nestedJsonObject.getString("pincode");
                String followers=nestedJsonObject.getString("noOfFollowers");

                Constants.userProfileData.add(new UserProfileData(companyName,emailID,contactNo,companyLogo,locality,dealsPushed,dealLikes,dealRedeems,merchantHandle,pincode,followers));

                return true;
            }
            //------------------>>
        } catch (Exception e1) {
            e1.printStackTrace();}


        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        try {
            mListener.onResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}