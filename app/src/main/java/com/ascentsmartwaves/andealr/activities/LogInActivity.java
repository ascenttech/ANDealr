package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.R;
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


public class LogInActivity extends Activity {

    EditText username,password;
    String user_id[],merchantid[],pwd[],deals_delivered[],device_id[]=null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username=(EditText) findViewById(R.id.username_login_activity);
        password=(EditText)findViewById(R.id.password_login_activity);
        editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();
    }

        public void LogIN(View v)
        {
            String uname= String.valueOf(username.getText());

              if(username.length()<=0 || password.length()<=0 )
              {
                  Toast.makeText(getApplicationContext(), "Username or Password should not be empty", Toast.LENGTH_LONG).show();
              }
            else
              {
                  new JSONAsyncTask().execute("http://integration.andealr.com/apps/v1.0/andealr/json/merchantLogin.php?userName=" + uname.replaceAll(" ","%20") + "&pwd=" + password.getText());

              }

        }






    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LogInActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
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
                    JSONArray jarray = jsono.getJSONArray("merchantLogin");
                    int cou=jarray.length();
                    user_id=new String[cou];
                    merchantid=new String[cou];
                    // mobile_no=new String[cou];
                    pwd=new String[cou];
                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        user_id[i]=object.getString("login");
                        if(user_id[0]=="true")
                        {
                            merchantid[i]=object.getString("merchantID");
                        }


                    }

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

            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();

            try {


                if(user_id[0]=="true")
                {
                    Constants.merchantId = merchantid[0];
                    Log.d("Andealr",Constants.merchantId);
                    editor.putString("DEALER", username.getText().toString());
                    editor.putString("Merchant Id", merchantid[0]);
                    editor.commit();
                    editor.commit();
                    Intent i=new Intent(getApplicationContext(),LandingActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_LONG).show();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}
