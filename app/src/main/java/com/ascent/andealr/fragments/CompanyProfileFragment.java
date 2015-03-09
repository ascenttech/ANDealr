package com.ascent.andealr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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


public class CompanyProfileFragment extends Fragment
{
    String id,companyName, contactNo, emailID, address1, address2, city, state, country, locality, photo,pincode, dealsPushed, dealLikes, dealRedeems = null;
    TextView name_tv, contactNo_tv, emailID_tv, address1_tv, address2_tv, city_tv, state_tv, country_tv, locality_tv,pincode_tv, dealsPushed_tv, dealLikes_tv, dealRedeems_tv;
    EditText companyname_et,pincode_et, contactNo_et, locality_et, companyemail_et, address1_et, address2_et, city_et, state_et, country_et, merchantHandle_et;
    String imagepathcompany,updateresult;
    Button update;
    ImageLoader  imageLoader;
    ImageView editcompanyprofile,profile;
    RelativeLayout profilelayout, mainlayout;
    LinearLayout profiledet, profileedit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_companyprofile, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = prefs.getString("Merchant Id", "NO DATA");
        name_tv = (TextView) v.findViewById(R.id.company_name);
        contactNo_tv = (TextView) v.findViewById(R.id.merchant_mobile_profile);
//        alternateNo_tv=(TextView)v.findViewById(R.id.d_id);
        profiledet = (LinearLayout) v.findViewById(R.id.company_profile_details_layout);
        profileedit = (LinearLayout) v.findViewById(R.id.edit_comapnyprofile_layout);
        emailID_tv = (TextView) v.findViewById(R.id.company_email_id_profile);
        address1_tv = (TextView) v.findViewById(R.id.company_addr1_profile);
        editcompanyprofile = (ImageView) v.findViewById(R.id.editcompanyprofile);
        address2_tv = (TextView) v.findViewById(R.id.company_addr2_profile);
        city_tv = (TextView) v.findViewById(R.id.company_city_profile);
        contactNo_tv= (TextView) v.findViewById(R.id.company_mobile_profile);
        state_tv = (TextView) v.findViewById(R.id.company_State_profile);
        country_tv = (TextView) v.findViewById(R.id.company_country_profile);
        locality_tv = (TextView) v.findViewById(R.id.company_locality_profile);
        pincode_tv = (TextView) v.findViewById(R.id.company_pincode_profile);
        dealsPushed_tv = (TextView) v.findViewById(R.id.counterfollowed);
        dealLikes_tv = (TextView) v.findViewById(R.id.counterliked);
        dealRedeems_tv = (TextView) v.findViewById(R.id.counterredeem);
        profile = (ImageView) v.findViewById(R.id.companyimg);
        update=(Button)v.findViewById((R.id.update_companyprofile_btn));


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });




        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadImagecompany().execute();
            }
        });



        editcompanyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editcompanyprofile.setVisibility(View.INVISIBLE);
                profiledet.setVisibility(View.INVISIBLE);
                profileedit.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                parms.addRule(RelativeLayout.BELOW, R.id.counternames);
                profileedit.setLayoutParams(parms);
                RelativeLayout.MarginLayoutParams params = (RelativeLayout.MarginLayoutParams) profileedit.getLayoutParams();
                params.topMargin = 20;
                profileedit.setLayoutParams(parms);

            }
        });



        companyname_et=(EditText) v.findViewById(R.id.merchant_companyname_activty_register);
        companyemail_et=(EditText) v.findViewById(R.id.compny_email);
        contactNo_et=(EditText) v.findViewById(R.id.compny_mobile);
        locality_et=(EditText) v.findViewById(R.id.company_locality);
        address1_et=(EditText) v.findViewById(R.id.merchant_addr1_company_profile);
        address2_et=(EditText) v.findViewById(R.id.merchant_addr2_company_profile);
        city_et=(EditText) v.findViewById(R.id.merchant_city_company_profile);
        state_et=(EditText) v.findViewById(R.id.merchant_State_company_profile);
        country_et=(EditText) v.findViewById(R.id.merchant_country_company_profile);
        pincode_et=(EditText) v.findViewById(R.id.merchant_pincode_company_profile);




        imageLoader = new ImageLoader(getActivity().getApplicationContext());



        final ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected())
        {
            Toast.makeText(getActivity(), "Wifi", Toast.LENGTH_LONG).show();
            new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/json_company.php?merchantID=" + id);
        }
        else if (mobile.isConnected())
        {
            Toast.makeText(getActivity(), "Mobile 3G ", Toast.LENGTH_LONG).show();
            new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/json_company.php?merchantID=" + id);
        }
        else
        {
//            profilelayout.setVisibility(View.INVISIBLE);
//            mainlayout.setBackgroundResource(R.drawable.nointernet);
            Toast.makeText(getActivity(), "No Network ", Toast.LENGTH_LONG).show();
        }

    return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagepathcompany = cursor.getString(columnIndex);
            cursor.close();
            Bitmap icon = BitmapFactory.decodeFile(imagepathcompany);
            profile.setImageBitmap(icon);

        }
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
//            dialog.show();
//            dialog.setCancelable(false);
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
                    JSONArray jarray = jsono.getJSONArray("profile");
                    int cou = jarray.length();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        companyName= object.getString("companyName");
                        contactNo = object.getString("contactNo");
                        emailID = object.getString("emailID");
                        address1 = object.getString("address1");
                        address2 = object.getString("address2");
                        city = object.getString("city");
                        state = object.getString("state");
                        country = object.getString("country");
                        locality = object.getString("locality");
                        pincode= object.getString("pincode");
                        photo = object.getString("companyLogo");
                        dealsPushed = object.getString("dealsPushed");
                        dealLikes = object.getString("dealLikes");
                        dealRedeems = object.getString("dealRedeems");

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
                if(city.equalsIgnoreCase("null")) {
                }
                else {
                    name_tv.setText(companyName);
                    emailID_tv.setText(emailID);
                    contactNo_tv.setText(contactNo);
                    address1_tv.setText(address1);
                    address2_tv.setText(address2);
                    city_tv.setText(city);
                    state_tv.setText(state);
                    country_tv.setText(country);
                    locality_tv.setText(locality);
                    dealsPushed_tv.setText(dealsPushed);
                    dealLikes_tv.setText(dealLikes);
                    dealRedeems_tv.setText(dealRedeems);
                    pincode_tv.setText(pincode);
                }
                imageLoader.DisplayImage( photo.replaceAll(" ","%20"), profile);

                if(city.equals("null")) {
                }
                else{
                    companyname_et.setText(companyName);
                    companyemail_et.setText(emailID);
                    contactNo_et.setText(contactNo);
                    locality_et.setText(locality);
                    address1_et.setText(address1);
                    address2_et.setText(address2);
                    city_et.setText(city);
                    state_et.setText(state);
                    country_et.setText(country);
                    pincode_et.setText(pincode);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (result == false)
                Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
        }
    }










    class UploadImagecompany extends AsyncTask<Void, Integer, String>
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
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL_COMPANYPROFILE);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                if(imagepathcompany!=null)
                {
                    sourceFile = new File(imagepathcompany);
                }
                else
                {
                    sourceFile=new File("NO DATA");
                }
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
                        Constants.CompanyphotoURL = jObject.getString("companyLogo");
//                    if(status.equalsIgnoreCase("Photo uploaded Successful!!!!")){
//
//                        Constants.dealImageUploaded = true;
//                    }
//                    else{
//                        Constants.dealImageUploaded = false;
//                    }
                        Log.d("SAGAR ", "STRING " + Constants.CompanyphotoURL);
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


            companyName = String.valueOf(companyname_et.getText());
            emailID= String.valueOf(companyemail_et.getText());
            contactNo = String.valueOf(contactNo_et.getText());
            locality = String.valueOf(locality_et.getText());
            address1 = String.valueOf(address1_et.getText());
            address2 = String.valueOf(address2_et.getText());
            city = String.valueOf(city_et.getText());
            state = String.valueOf(state_et.getText());
            country = String.valueOf(country_et.getText());
            pincode = String.valueOf(pincode_et.getText());

            companyName = companyName.replaceAll(" ","%20");
            contactNo = contactNo.replaceAll(" ","%20");
            locality = locality.replaceAll(" ","%20");
            address1 = address1.replaceAll(" ","%20");
            address2 = address2.replaceAll(" ","%20");
            city = city.replaceAll(" ","%20");
            state = state.replaceAll(" ","%20");
            emailID = pincode.replaceAll(" ","%20");
            country = country.replaceAll(" ","%20");
            pincode = pincode.replaceAll(" ","%20");

                Toast.makeText(getActivity().getApplicationContext(),""+Constants.CompanyphotoURL,Toast.LENGTH_SHORT).show();
               new UpdateAsyncTask().execute("http://andnrbytest190215.ascentinc.in/companyProfile.php?merchantID="+id+"&companyName="+companyName+"&emailID="+emailID+"&contactNo="+contactNo+"&companyLogo="+Constants.CompanyphotoURL+"&address1="+address1+"&address2="+address2+"&locality="+locality+"&city="+city+"&state="+state+"&country="+country+"&latitude=198&longitude=320&pincode="+pincode);


        }


    }



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
                    JSONArray jarray = jsono.getJSONArray("companyProfile");
                    for (int i = 0; i < jarray.length(); i++)
                    {
                        JSONObject object = jarray.getJSONObject(i);
                        updateresult = object.getString("profileUpdate");
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
