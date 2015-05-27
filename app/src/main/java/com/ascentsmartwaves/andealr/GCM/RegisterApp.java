package com.ascentsmartwaves.andealr.GCM;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ascentsmartwaves.andealr.utils.Constants;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import java.io.IOException;

public class RegisterApp extends AsyncTask<String,Void,Boolean>{

    Context context;
    public RegisterAppCallback listener;
    private static final String TAG = "GCMRelated";
    Context ctx;
    GoogleCloudMessaging gcm;
    String SENDER_ID = "887332065605";
    String regid = null;
    private int appVersion;

    public RegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion,RegisterAppCallback listener)
    {
        this.ctx = ctx;
        this.gcm = gcm;
        this.appVersion = appVersion;
        this.listener=listener;
    }

    public interface RegisterAppCallback{

        public void onStart(boolean status);
        public void onResult(boolean result);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onStart(true);
    }

    @Override
    protected Boolean doInBackground(String... url) {



        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);
            }
            regid = gcm.register(SENDER_ID);
            Log.d(Constants.LOG_TAG,"Registration id "+ regid);
            Constants.GCMRegID =regid;
            msg = "Device registered, registration ID=" + regid;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.


            // For this demo: we don't need to send it because the device
            // will send upstream messages to a server that echo back the
            // message using the 'from' address in the message.

            // Persist the regID - no need to register again.

        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
            // If there is an error, don't just keep trying to register.
            // Require the user to click a button again, or perform
            // exponential back-off.
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        listener.onResult(result);
    }
}




