package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.data.LandingFragmentDetail;
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

    public FetchDealDetailsAsyncTask(int position,Context context, FetchDealDetailsCallback listener) {
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

        Log.d(Constants.LOG_TAG,Constants.FetchDealDetailsAsyncTask);

        try {
            HttpGet httpGet = new HttpGet(url[0]);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(httpGet);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            Log.d(Constants.LOG_TAG,""+response);
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                Log.d("LOG", " " + data);
                JSONObject jsono = new JSONObject(data);
                JSONArray jsonArray = jsono.getJSONArray("dealDetails");
                JSONObject nestedJsonObject = jsonArray.getJSONObject(0);


                String dealId = nestedJsonObject.getString("dealID");
                String dealTittle = nestedJsonObject.getString("dealTitle");
                String dealDescription = nestedJsonObject.getString("dealDescription");
                String city = nestedJsonObject.getString("city");
                String startdate=nestedJsonObject.getString("dealStart");
                String enddate=nestedJsonObject.getString("dealEnd");

                String likes = nestedJsonObject.getString("noOfLikes");
                String redeem = nestedJsonObject.getString("noOfRedeems");
                String photoURL = nestedJsonObject.getString("photoURL");

                // dealId is converted into INT
                int id = Integer.parseInt(dealId);

                // new field added
                String reach = nestedJsonObject.getString("shownCount");

                String likesString = String.valueOf(likes);
                String redeemString = String.valueOf(redeem);


                Constants.landingFragmentDetail.add(new LandingFragmentDetail(dealId,dealDescription,likes,redeem,startdate,enddate,photoURL,dealTittle,city,reach));


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
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        listener.onResult(result);
    }
}
