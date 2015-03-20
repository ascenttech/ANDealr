package com.ascentsmartwaves.andealr.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.activities.ProfileActivity;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.AndroidMultiPartEntity;
import com.ascentsmartwaves.andealr.utils.Config;
import com.ascentsmartwaves.andealr.utils.Constants;
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
    String companyName, contactNo, emailID, address1, address2, city, state, country, locality, photo,pincode, dealsPushed, dealLikes, dealRedeems = null;
    TextView name_tv, contactNo_tv, emailID_tv,locality_tv,pincode_tv, dealsPushed_tv, dealLikes_tv, dealRedeems_tv;
//    , address1_tv, address2_tv, city_tv, state_tv, country_tv,
    EditText companyname_et,pincode_et, contactNo_et, locality_et, companyemail_et, merchantHandle_et;
//    , address1_et, address2_et, city_et, state_et, country_et
    String imagepathcompany,updateresult;
    Button update;
    ImageLoader  imageLoader;
    ImageView editcompanyprofile,profile;
    RelativeLayout profilelayout, mainlayout;
    LinearLayout profiledet, profileedit;
    ProgressDialog dialog;
    Bitmap icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_companyprofile, container, false);

        name_tv = (TextView) v.findViewById(R.id.company_name);
        contactNo_tv = (TextView) v.findViewById(R.id.merchant_mobile_profile);
        profiledet = (LinearLayout) v.findViewById(R.id.company_profile_details_layout);
        profileedit = (LinearLayout) v.findViewById(R.id.edit_comapnyprofile_layout);
        emailID_tv = (TextView) v.findViewById(R.id.company_email_id_profile);
        profilelayout = (RelativeLayout) v.findViewById(R.id.profile_layout_company_profile);
        mainlayout = (RelativeLayout) v.findViewById(R.id.mainlayoutcompany);
        editcompanyprofile = (ImageView) v.findViewById(R.id.editcompanyprofile);
        contactNo_tv= (TextView) v.findViewById(R.id.company_mobile_profile);
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
                updatecompanyprofile();

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
                params.leftMargin = 20;
                params.rightMargin = 20;
                profileedit.setLayoutParams(parms);

            }
        });

        companyname_et=(EditText) v.findViewById(R.id.merchant_companyname_activty_register);
        companyemail_et=(EditText) v.findViewById(R.id.compny_email);
        contactNo_et=(EditText) v.findViewById(R.id.compny_mobile);
        locality_et=(EditText) v.findViewById(R.id.company_locality);
        pincode_et=(EditText) v.findViewById(R.id.merchant_pincode_company_profile);




        imageLoader = new ImageLoader(getActivity().getApplicationContext());



        final ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected())
        {
            fetchCompanyData();
        }
        else if (mobile.isConnected())
        {
            fetchCompanyData();
        }
        else
        {
            profilelayout.setVisibility(View.INVISIBLE);
            mainlayout.setBackgroundResource(R.drawable.no_internet);
        }



    return v;
    }

    private void fetchCompanyData()
    {
        new JSONAsyncTask().execute("http://integration.andealr.com/apps/v1.0/andealr/json/json_company.php?merchantID="+Constants.merchantId);
    }

    private void updatecompanyprofile()
    {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Uploading Logo");
        dialog.setMessage("Loading... please wait");
        dialog.show();
        dialog.setCancelable(false);
     if(imagepathcompany==null)
     {
         companyName = String.valueOf(companyname_et.getText());
         emailID= String.valueOf(companyemail_et.getText());
         contactNo = String.valueOf(contactNo_et.getText());
         locality = String.valueOf(locality_et.getText());
         pincode = String.valueOf(pincode_et.getText());

         companyName = companyName.replaceAll(" ","%20");
         contactNo = contactNo.replaceAll(" ","%20");
         locality = locality.replaceAll(" ","%20");
         emailID = emailID.replaceAll(" ","%20");
         pincode = pincode.replaceAll(" ","%20");
                 if(Constants.CompanyphotoURL==null)
                 {
                     Constants.CompanyphotoURL=photo;
                 }
                 if(companyName.length()>0 && emailID.length()>0)
                 {
                     if(contactNo.length()>0)
                     {
                         if(locality.length()>0 && pincode.length()>0) {
                             if (pincode.length()== 6)
                             {
                                 new UpdateAsyncTask().execute("http://integration.andealr.com/apps/v1.0/andealr/json/companyProfile.php?merchantID=" + Constants.merchantId + "&companyName=" + companyName + "&emailID=" + emailID + "&contactNo=" + contactNo + "&companyLogo=" + Constants.CompanyphotoURL + "&address1=&address2=&locality=" + locality + "&city=&state=&country=&latitude=198&longitude=320&pincode=" + pincode);
                             }
                             else
                             {
                                 Toast.makeText(getActivity().getApplicationContext(), "Pincode should not be less than 6 digits", Toast.LENGTH_LONG).show();
                                 dialog.dismiss();
                             }
                         }
                         else
                         {
                             Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                             dialog.dismiss();
                         }
                     }
                     else
                     {
                         Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                         dialog.dismiss();
                     }

                 }
                 else
                 {
                     Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                     dialog.dismiss();
                 }

             }
                else
             {
                 new UploadImagecompany().execute();
             }
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

//                icon = BitmapFactory.decodeFile(imagepathcompany);
                profile.setImageBitmap(getScaledBitmap(imagepathcompany, 200, 200));



        }
    }



    private Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }



    //JSON FOR VIEW PROFILE
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity().getApplicationContext());
            dialog.setMessage("Loading... please wait");
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
                if(companyName.equalsIgnoreCase("null")) {
                }
                else {
                    name_tv.setText(companyName);
                    emailID_tv.setText(emailID);
                    contactNo_tv.setText(contactNo);
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
//                    address1_et.setText(address1);
//                    address2_et.setText(address2);
//                    city_et.setText(city);
//                    state_et.setText(state);
//                    country_et.setText(country);
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




    @Override
    public void onDestroy() {
        super.onStop();
        if(icon!=null)
        {
            icon=null;
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

            dialog.dismiss();
            companyName = String.valueOf(companyname_et.getText());
            emailID= String.valueOf(companyemail_et.getText());
            contactNo = String.valueOf(contactNo_et.getText());
            locality = String.valueOf(locality_et.getText());
            pincode = String.valueOf(pincode_et.getText());

            companyName = companyName.replaceAll(" ","%20");
            contactNo = contactNo.replaceAll(" ","%20");
            locality = locality.replaceAll(" ","%20");
            address1 = address1.replaceAll(" ","%20");
            address2 = address2.replaceAll(" ","%20");
            city = city.replaceAll(" ","%20");
            state = state.replaceAll(" ","%20");
            emailID = emailID.replaceAll(" ","%20");
            country = country.replaceAll(" ","%20");
            pincode = pincode.replaceAll(" ","%20");

            if(Constants.CompanyphotoURL==null)
            {
                Constants.CompanyphotoURL=photo;
            }

            if(companyName.length()>0 && emailID.length()>0)
            {
                     if(contactNo.length()>0)
                    {
                        if(locality.length()>0 && pincode.length()>0)
                        {
                            dialog.dismiss();
                            new UpdateAsyncTask().execute("http://integration.andealr.com/apps/v1.0/andealr/json/companyProfile.php?merchantID=" + Constants.merchantId + "&companyName=" + companyName + "&emailID=" + emailID + "&contactNo=" + contactNo + "&companyLogo=" + Constants.CompanyphotoURL.replaceAll(" ", "%20") + "&address1=&address2=&locality=" + locality + "&city=&state=&country=&latitude=198&longitude=320&pincode=" + pincode);
                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                        }
                     }
                     else
                     {
                         Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                     }

            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
            }

        }


    }



    class UpdateAsyncTask extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Updating ");
            dialog.setMessage("Loading... please wait");
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
            dialog.dismiss();
            try
            {

                Toast.makeText(getActivity().getApplicationContext(),"PROFILE UPDATED",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
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
