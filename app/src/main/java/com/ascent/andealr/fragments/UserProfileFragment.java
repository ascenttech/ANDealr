package com.ascent.andealr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascent.andealr.R;
import com.ascent.andealr.imagecaching.ImageLoader;
import com.ascent.andealr.utils.AndroidMultiPartEntity;
import com.ascent.andealr.utils.Config;
import com.ascent.andealr.utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class UserProfileFragment extends Fragment {
    ImageView editprofile;
    RelativeLayout profilelayout, mainlayout;
    LinearLayout profiledet, profileedit;
    String id,updateresult,imagepath;
    Button updateprofile;
    int serverResponseCode = 0;
    ImageLoader imageLoader;
    String firstName, lastName, contactNo, alternateNo, emailID, dateOfBirth, address1, address2, city, state, country, merchantHandle, photo, dealsPushed, dealLikes, dealRedeems = null;
    TextView name_tv, contactNo_tv, emailID_tv, dateOfBirth_tv, address1_tv, address2_tv, city_tv, state_tv, country_tv, merchantHandle_tv;
    EditText fname_et,lname_et, alternateNo_et, dateOfBirth_et, address1_et, address2_et, city_et, state_et, country_et, merchantHandle_et;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_userprofile, container, false);
        editprofile = (ImageView) v.findViewById(R.id.editprofile);
        profiledet = (LinearLayout) v.findViewById(R.id.profile_details_layout);
        profileedit = (LinearLayout) v.findViewById(R.id.edit_profile_layout);
        profilelayout = (RelativeLayout) v.findViewById(R.id.profile_layout_user_profile);
        mainlayout = (RelativeLayout) v.findViewById(R.id.mainlayout_userprofile);

        updateprofile = (Button) v.findViewById(R.id.update_profile_btn);
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functionupdateprofile();
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editprofile.setVisibility(View.INVISIBLE);
                profiledet.setVisibility(View.INVISIBLE);
                profileedit.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                parms.addRule(RelativeLayout.BELOW, R.id.profile_Img_Layout);
                profileedit.setLayoutParams(parms);
                RelativeLayout.MarginLayoutParams params = (RelativeLayout.MarginLayoutParams) profileedit.getLayoutParams();
                params.topMargin = 20;
                profileedit.setLayoutParams(parms);

            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = prefs.getString("Merchant Id", "NO DATA");
        imagepath=prefs.getString("picturepath", "NO DATA");

        name_tv = (TextView) v.findViewById(R.id.merchant_name);
        contactNo_tv = (TextView) v.findViewById(R.id.merchant_mobile_profile);
//        alternateNo_tv=(TextView)v.findViewById(R.id.d_id);
        emailID_tv = (TextView) v.findViewById(R.id.merchant_email_id_profile);
        dateOfBirth_tv = (TextView) v.findViewById(R.id.merchant_DOB_profile);
        address1_tv = (TextView) v.findViewById(R.id.merchant_addr1_profile);
        address2_tv = (TextView) v.findViewById(R.id.merchant_addr2_profile);
        city_tv = (TextView) v.findViewById(R.id.merchant_city_profile);
        state_tv = (TextView) v.findViewById(R.id.merchant_State_profile);
        country_tv = (TextView) v.findViewById(R.id.merchant_country_profile);
        merchantHandle_tv = (TextView) v.findViewById(R.id.merchant_handle_profile);
        imageLoader = new ImageLoader(getActivity().getApplicationContext());


        fname_et=(EditText) v.findViewById(R.id.merchant_firstname_activty_register);
        lname_et=(EditText) v.findViewById(R.id.merchant_lastname_activty_register);
        alternateNo_et=(EditText) v.findViewById(R.id.merchant_alternate);
        dateOfBirth_et=(EditText) v.findViewById(R.id.merchant_DOB);
        address1_et=(EditText) v.findViewById(R.id.merchant_addr1_user_profile);
        address2_et=(EditText) v.findViewById(R.id.merchant_addr2_user_profile);
        city_et=(EditText) v.findViewById(R.id.merchant_city_user_profile);
        state_et=(EditText) v.findViewById(R.id.merchant_State_user_profile);
        country_et=(EditText) v.findViewById(R.id.merchant_country_user_profile);
        merchantHandle_et=(EditText) v.findViewById(R.id.merchant_handle_profile);


        final ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected())
        {
            Toast.makeText(getActivity(), "Wifi", Toast.LENGTH_LONG).show();
            new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/merchantProfile.php?merchantID=" + id);
        }
        else if (mobile.isConnected())
        {
            Toast.makeText(getActivity(), "Mobile 3G ", Toast.LENGTH_LONG).show();
            new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/merchantProfile.php?merchantID=" + id);
        }
        else
        {
            profilelayout.setVisibility(View.INVISIBLE);
            mainlayout.setBackgroundResource(R.drawable.nointernet);
            Toast.makeText(getActivity(), "No Network ", Toast.LENGTH_LONG).show();
        }

        String data = getActivity().getIntent().getStringExtra("key");
        if(data!=null)
        {
            editprofile.setVisibility(View.INVISIBLE);
            profiledet.setVisibility(View.INVISIBLE);
            profileedit.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            parms.addRule(RelativeLayout.BELOW, R.id.counternames);
            profileedit.setLayoutParams(parms);
            RelativeLayout.MarginLayoutParams params = (RelativeLayout.MarginLayoutParams) profileedit.getLayoutParams();
            params.topMargin = 20;
            profileedit.setLayoutParams(parms);
        }

        return v;
    }





    //JSON FOR VIEW PROFILE
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity().getApplicationContext());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
//           dialog.show();
//           dialog.setCancelable(false);
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
                    JSONArray jarray = jsono.getJSONArray("merchantProfile");
                    int cou = jarray.length();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        firstName = object.getString("firstName");
                        lastName = object.getString("lastName");
                        contactNo = object.getString("contactNo");
                        alternateNo = object.getString("alternateNo");
                        emailID = object.getString("emailID");
                        dateOfBirth = object.getString("dateOfBirth");
                        address1 = object.getString("address1");
                        address2 = object.getString("address2");
                        city = object.getString("city");
                        state = object.getString("state");
                        country = object.getString("country");
                        merchantHandle = object.getString("merchantHandle");
                        photo = object.getString("photo");

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
//           dialog.dismiss();
//            dialog.cancel();
            try
            {
                if(city.equalsIgnoreCase("null")){
                }
                else {
                    name_tv.setText(firstName + " " + lastName);
                    emailID_tv.setText(emailID);
                    contactNo_tv.setText(contactNo);
                    dateOfBirth_tv.setText(dateOfBirth);
                    address1_tv.setText(address1);
                    address2_tv.setText(address2);
                    city_tv.setText(city);
                    state_tv.setText(state);
                    country_tv.setText(country);
                    merchantHandle_tv.setText(merchantHandle);
                }

                if(firstName.equals("null")) {
                }
                else
                {
                    fname_et.setText(firstName);
                    lname_et.setText(lastName);
                    dateOfBirth_et.setText(dateOfBirth);
                    alternateNo_et.setText(alternateNo);
                    address1_et.setText(address1);
                    address2_et.setText(address2);
                    city_et.setText(city);
                    state_et.setText(state);
                    country_et.setText(country);
                    merchantHandle_et.setText(merchantHandle);
                }

                ImageView profile = (ImageView) getActivity().findViewById(R.id.profileimg);
                imageLoader.DisplayImage( photo.replaceAll(" ","%20"), profile);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }





    private void functionupdateprofile()
    {
//JSON FOR UPLOAD PIC
        new UploadImageDealer().execute();
//        Toast.makeText(getActivity().getApplicationContext(),"PLEASE FILL ALL THE FIELDS"+Constants.photoURL,Toast.LENGTH_SHORT).show();

    }













    class UploadImageDealer extends AsyncTask<Void, Integer, String>
    {
        long totalSize = 0;
        String status;
        File sourceFile;
        @Override
        protected String doInBackground(Void... params)
        {
            return uploadFile();
        }
        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL_USERPROFILE);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                sourceFile = new File(imagepath);
                // Adding file data to http body
                entity.addPart("image", new FileBody(sourceFile));
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                    Log.d("SAGAR ", "RESPONSE STRING " + responseString);

                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        status = jsonObject.getString("statusMessage");
                        JSONArray jsonArray = jsonObject.getJSONArray("photo");
                        JSONObject jObject = jsonArray.getJSONObject(0);
                        Constants.photoURL = jObject.getString("photoURL");
//                    if(status.equalsIgnoreCase("Photo uploaded Successful!!!!")){
//
//                        Constants.dealImageUploaded = true;
//                    }
//                    else{
//                        Constants.dealImageUploaded = false;
//                    }
                        Log.d("SAGAR ", "STRING " + Constants.photoURL);
                    }
                    catch(Exception e)
                    {
                        // do something
                    }
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }
            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result)
        {

            firstName = String.valueOf(fname_et.getText());
            lastName = String.valueOf(lname_et.getText());
            alternateNo = String.valueOf(alternateNo_et.getText());
            dateOfBirth = String.valueOf(dateOfBirth_et.getText());
            address1 = String.valueOf(address1_et.getText());
            address2 = String.valueOf(address2_et.getText());
            city = String.valueOf(city_et.getText());
            state = String.valueOf(state_et.getText());
            country = String.valueOf(country_et.getText());
            merchantHandle = String.valueOf(merchantHandle_et.getText());


            firstName = firstName.replaceAll(" ","%20");
            lastName = lastName.replaceAll(" ","%20");
            alternateNo = alternateNo.replaceAll(" ","%20");
            dateOfBirth = dateOfBirth.replaceAll(" ","%20");
            address1 = address1.replaceAll(" ","%20");
            address2 = address2.replaceAll(" ","%20");
            city = city.replaceAll(" ","%20");
            state = state.replaceAll(" ","%20");
            country = country.replaceAll(" ","%20");
            merchantHandle = merchantHandle.replaceAll(" ","%20");


            if ((fname_et.length() > 0 && lname_et.length() > 0) && (alternateNo_et.length() > 0 && dateOfBirth_et.length() > 0) && (address1_et.length() > 0 && country_et.length() > 0) && (city_et.length() > 0 && state_et.length() > 0))
            {
                new UpdateAsyncTask().execute("http://andnrbytest190215.ascentinc.in/updateMerchant.php?merchantID=" + id + "&firstName=" + firstName + "&lastName=" + lastName + "&alternateNo=" + alternateNo + "&dateOfBirth=" + dateOfBirth + "&address1=" + address1 + "&address2=" + address2 + "&city=" + city + "&state=" + state + "&country=" + country + "&merchantHandle=" + merchantHandle + "&photo=" + Constants.photoURL.replaceAll(" ","%20"));
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "PLEASE FILL ALL THE FIELDS", Toast.LENGTH_SHORT).show();
            }

        }

    }















    //JSON FOR UPDATE PROFILE
    class UpdateAsyncTask extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity().getApplicationContext());
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
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
                    JSONArray jarray = jsono.getJSONArray("merchantProfile");
                    for (int i = 0; i < jarray.length(); i++)
                    {
                        JSONObject object = jarray.getJSONObject(i);
                        updateresult = object.getString("merchantProfile");
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
            try
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }
}