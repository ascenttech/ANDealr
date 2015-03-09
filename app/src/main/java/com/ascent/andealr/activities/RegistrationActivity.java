package com.ascent.andealr.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 05-12-2014.
 */
public class RegistrationActivity extends Activity {
    RelativeLayout header;
    EditText email,mobileno,password;
    Button signup;
    String registeration[],field[], merchantid[]=null;
    String possibleEmail;
    TelephonyManager tm;
    String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        header=(RelativeLayout)findViewById(R.id.header_register_activity);
        signup=(Button)findViewById(R.id.signup_btn_register_activity);
        email=(EditText)findViewById(R.id.email_register_activity);
        mobileno=(EditText)findViewById(R.id.mobile_register_activity);
        password=(EditText)findViewById(R.id.password_register_activity);
        autoemail();
        automobile();

        editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                String email_String= String.valueOf(email.getText());
                if(email.length()>0 && mobileno.length()>0 && password.length()>0)
                {
                    if ((email_String.matches(emailPatterns)))
                    {
                        if (mobileno.length() == 10)
                        {
                            if (password.length() >= 6)
                            {
                                new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/merchantRegistration.php?emailID=" + email.getText() + "&contactNo=" + mobileno.getText() + "&pwd=" + password.getText());
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Password should be more then 6 digits", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Mobile No should not be less then 10 digits", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Fill all the details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }





    private void automobile()
    {
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        if (number != null) {
            mobileno.setText(number);
        } else {
        }
    }

    private void autoemail() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts)
        {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail  = account.name;
                email.setText(possibleEmail);
            }
        }
    }




//    @Override
//    public void onBackPressed() {
//        // nothing is written so that it doesnt get go back to the prev page
//    }

    public void cancelbtn(View v)
    {
        Intent i=new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(i);
    }







    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(RegistrationActivity.this);
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
                    JSONArray jarray = jsono.getJSONArray("merchantRegistration");

                    int cou=jarray.length();
                    registeration=new String[cou];
                    field=new String[cou];
                    merchantid =new String[cou];

                    if(jarray != null) {
                        for (int i = 0; i < jarray.length(); i++)
                        {
                            JSONObject object = jarray.getJSONObject(i);
                            registeration[i] = object.getString("registration");
                            if (registeration[i].equals("false")) {
                                field[i] = object.getString("field");
                            }
                            else
                            {
                                merchantid[i] = object.getString("merchantID");
                            }
                        }
                    }


                    return true;
                }
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

                    if (registeration[0] == "true")
                    {   // Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                        editor.putString("DEALER", email.getText().toString());
                        editor.putString("Merchant Id", merchantid[0]);
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), LandingActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        if (field[0].equals("emailID")) {
                            Toast.makeText(getApplicationContext(), "Email ID is already Registered", Toast.LENGTH_LONG).show();
                        } else if (field[0].equals("contactNo")) {
                            Toast.makeText(getApplicationContext(), "Mobile No is already Registered", Toast.LENGTH_LONG).show();
                        }
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
