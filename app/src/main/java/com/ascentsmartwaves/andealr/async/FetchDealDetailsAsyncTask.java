package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import com.ascentsmartwaves.andealr.utils.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class FetchDealDetailsAsyncTask extends AsyncTask<String,Void,Boolean> {
    Context context;
    public FetchDealDetailsCallback listener;
    int position;
    HttpEntity responseEntity;
    String responseString;
    public interface FetchDealDetailsCallback{
        public void onStart(boolean a);
        public void onResult(boolean b);
    }

    public FetchDealDetailsAsyncTask(int position, Context context, FetchDealDetailsCallback listener) {
        this.position = position;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);

    }


    @Override
    protected Boolean doInBackground(String... url) {

        try {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                responseEntity = response.getEntity();
                responseString = EntityUtils.toString(responseEntity);

                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray jsonArray = jsonObject.getJSONArray("dealDetails");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);

                String dealId = nestedJsonObject.getString("dealID");
                String dealTittle = nestedJsonObject.getString("dealTitle");
                String dealDescription = nestedJsonObject.getString("dealDescription");
                String city = nestedJsonObject.getString("city");
                String startdate=nestedJsonObject.getString("dealStart");
                String enddate=nestedJsonObject.getString("dealEnd");

                int likes = nestedJsonObject.getInt("noOfLikes");
                int redeem = nestedJsonObject.getInt("noOfRedeems");
                String photoURL = nestedJsonObject.getString("photoURL");

                // dealId is converted into INT
                int id = Integer.parseInt(dealId);

                String likesString = String.valueOf(likes);
                String redeemString = String.valueOf(redeem);

                Constants.landingFragmentData.get(position).setDealDescription(dealDescription);
                Constants.landingFragmentData.get(position).setLikes(likesString);
                Constants.landingFragmentData.get(position).setRedeem(redeemString);
                Constants.landingFragmentData.get(position).setDealStart(startdate);
                Constants.landingFragmentData.get(position).setDealEnd(enddate);





                return true;
            }
            //------------------>>
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
