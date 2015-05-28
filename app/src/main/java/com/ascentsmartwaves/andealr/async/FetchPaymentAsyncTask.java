package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.PaymentsData;
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
public class FetchPaymentAsyncTask extends AsyncTask<String, Void, Boolean> {

    int length;
    String dealTitle[],success[],timestamp[],orderID[],amount[]=null;
    Context context;
    public FetchPaymentCallback listener;
    HttpEntity entity;
    String data;

    // This means it is a transaction record of he paying us (AndNrby)
    boolean hePaid = false;

    public interface FetchPaymentCallback{

        public void onStart(boolean a);
        public void onResult(boolean b);
    }


    public FetchPaymentAsyncTask(Context context, FetchPaymentCallback listener) {
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

            Log.d(Constants.LOG_TAG," String for Payment fetch "+ urls[0]);

            //------------------>>
            HttpGet httppost = new HttpGet(urls[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {

                entity = response.getEntity();
                data = EntityUtils.toString(entity);

                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("payment");
                length = jsonArray.length();

                for(int i = 0;i<length;i++){
                    JSONObject nestedJSONObject = jsonArray.getJSONObject(i);

                    String orderID=nestedJSONObject.getString("orderID");
                    if(orderID.equalsIgnoreCase("PR12345")){
                        hePaid = true;
                    }
                    else{

                        hePaid = false;
                    }
                    String dealTitle=nestedJSONObject.getString("dealTitle");
                    String amount=nestedJSONObject.getString("amount");
                    String date=nestedJSONObject.getString("date");
                    String time=nestedJSONObject.getString("time");
                    Constants.currentBalance = nestedJSONObject.getString("currentBalance");

                    Log.d(Constants.LOG_TAG,Constants.currentBalance);

                    Constants.paymentsData.add(new PaymentsData(orderID,dealTitle,amount,date,time,hePaid));
                }

                return true;
            }
            if (status == 201){

                return false;
            }
        }
        catch(Exception e){

        }
        return false;
    }

    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);
        listener.onResult(result);

    }

}
