package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.FollowersData;
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
public class FetchFollowersAsyncTask extends AsyncTask<String, Void, Boolean> {

    String firstName[],lastName[],photo[],userHandle[]=null;
    int cou;
    Context context;
    public FollowersCallback listener;


    public interface FollowersCallback{

        public void onStart(boolean a);
        public void onResult(boolean b);
    }

    public FetchFollowersAsyncTask(Context context, FollowersCallback listener) {
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


        Log.d(Constants.LOG_TAG, Constants.FetchFollowersAsyncTask);
        Log.d(Constants.LOG_TAG,"The requested url is "+urls[0]);
        try {

            //------------------>>
            HttpGet httpget = new HttpGet(urls[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpget);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                Log.d(Constants.LOG_TAG," The response is "+data);
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("followers");

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject object = jsonArray.getJSONObject(i);
                    String firstName=object.getString("firstName");
                    String lastName = object.getString("lastName");
                    String photo = object.getString("photo");
                    String userHandle = object.getString("userHandle");
                    String fullName = firstName + lastName;

                    Constants.followersData.add(new FollowersData(fullName,userHandle,photo));

                }

                return true;
            }
            else if(status == 201)
            {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);
        Log.d(Constants.LOG_TAG," The result is "+result);
        listener.onResult(result);

    }
}



