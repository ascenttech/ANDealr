package com.ascentsmartwaves.andealr.async;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.data.MerchantProfileData;
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

/**
 * Created by ADMIN on 11-03-2015.
 */
public class MerchantProfileAsyncTask extends AsyncTask<String,Void,Boolean>
{
    Context context;
    private MerchantProfileAsyncTaskCallback mListener;

    /** Implement this somewhere to get the result */
    public interface MerchantProfileAsyncTaskCallback {
        void onStart(boolean a);
        void onResult(boolean b);
    }


    public MerchantProfileAsyncTask(Context context,MerchantProfileAsyncTaskCallback listener) {
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

        Log.d(Constants.LOG_TAG,Constants.MerchantProfileAsyncTask);

        try {
            //------------------>>
            HttpGet httppost = new HttpGet(url[0]);
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
                    String firstName = object.getString("firstName");
                    String lastName = object.getString("lastName");
                    String contactNo = object.getString("contactNo");
                    String alternateNo = object.getString("alternateNo");
                    String emailID = object.getString("emailID");
                    String dateOfBirth = object.getString("dateOfBirth");
                    String merchantHandle = object.getString("merchantHandle");
                    String gender=object.getString("gender");
                    String photo = object.getString("photo");
//                    Constants.merchantprofiledata.add(new MerchantProfileData(firstName,lastName,contactNo, alternateNo,emailID,dateOfBirth,merchantHandle,gender,photo));

                return true;
            }
            //------------------>>
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        mListener.onResult(result);
    }
}

