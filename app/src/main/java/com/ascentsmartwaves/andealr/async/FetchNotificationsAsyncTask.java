package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.NotificationsData;
import com.ascentsmartwaves.andealr.data.NotificationsDataPrevious;
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
public class FetchNotificationsAsyncTask extends AsyncTask<String,Void,Boolean> {

    Context context;
    private FetchNotificationsCallback listener;
    HttpEntity responseEntity;
    String responseString;


    public interface FetchNotificationsCallback{

        public void onStart(boolean a);
        public void onResult(boolean b);
    }


    public FetchNotificationsAsyncTask(Context context, FetchNotificationsCallback listener) {
        this.context = context;
        this.listener = listener;
        Log.d(Constants.LOG_TAG,Constants.FetchNotificationsAsyncTask);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {


        Log.d(Constants.LOG_TAG,"The requested url is "+url[0]);

        try {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);
                Log.d(Constants.LOG_TAG, " Response String for Fetch Notifications " + responseString);

                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray jsonArray = jsonObject.getJSONArray("notification");

                for(int i = 0;i<jsonArray.length();i++){

                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);
                    int notificationId = nestedJsonObject.getInt("notificationID");
                    int merchantId = nestedJsonObject.getInt("merchantID");
                    String message = nestedJsonObject.getString("message");
                    String date = nestedJsonObject.getString("date");
                    String time = nestedJsonObject.getString("time");

                    Constants.notificationsData.add(new NotificationsData(notificationId,merchantId,message,date,time));

                }

                return true;

            }
            else if(status == 201){

                return false;
            }

//            if (status == 200) {
//                responseEntity = response.getEntity();
//                responseString = EntityUtils.toString(responseEntity);
//                Log.d("Andealr", " " + responseString);
//
//                JSONObject jsonObject = new JSONObject(responseString);
//                JSONArray jsonArray = jsonObject.getJSONArray("dealDetails");
//                int length = jsonArray.length();
//
//                for(int i = 0;i<length;i++){
//
//                    JSONObject nestedJsonObject = jsonArray.getJSONObject(i);
//                    String dealId = nestedJsonObject.getString("dealID");
//                    String dealTittle = nestedJsonObject.getString("dealTitle");
//                    String startDate = nestedJsonObject.getString("startDate");
//                    String startTime = nestedJsonObject.getString("startTime");
//                    String endDate = nestedJsonObject.getString("endDate");
//                    String endTime = nestedJsonObject.getString("endTime");
//                    String dealStatus = nestedJsonObject.getString("dealStatus");
//                    int likes = nestedJsonObject.getInt("noOfLikes");
//                    int redeem = nestedJsonObject.getInt("noOfRedeems");
//
//                    Constants.notificationsDataPrevious.add(new NotificationsDataPrevious(dealId,dealTittle,startDate, startTime,endDate,endTime,dealStatus,likes,redeem));
//
//                }
//
//            }
            return false;

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
