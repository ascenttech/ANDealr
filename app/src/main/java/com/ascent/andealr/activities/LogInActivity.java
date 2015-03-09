package com.ascent.andealr.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascent.andealr.R;

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
import java.io.InputStream;


public class LogInActivity extends Activity {

    EditText username,password;
    String user_id[],merchantid[],pwd[],deals_delivered[],device_id[]=null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username=(EditText) findViewById(R.id.username_login_activity);
        password=(EditText)findViewById(R.id.password_login_activity);
        editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();
    }

        public void LogIN(View v)
        {


              if(username.length()<=0 || password.length()<=0 )
              {
                  Toast.makeText(getApplicationContext(), "Username or Password should not be empty", Toast.LENGTH_LONG).show();
              }
            else
              {
                  new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/merchantLogin.php?userName=" + username.getText() + "&pwd=" + password.getText());

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

            //TextView userid=(TextView)findViewById(R.id.u_id);
            // TextView emailid=(TextView)findViewById(R.id.e_id);
            //TextView deviceid=(TextView)findViewById(R.id.d_id);
            //TextView dealsdelivered=(TextView)findViewById(R.id.deals_delevered);
            //TextView mobileno=(TextView)findViewById(R.id.m_no);
            //TextView password=(TextView)findViewById(R.id.pwd);
            //cat.setText("Category ID:"+cat_id[0]);
            //movie.setText("Movie Id:"+movie_id[0]);

            try {


                if(user_id[0]=="true")
                {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
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

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon;
//        }
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
}
