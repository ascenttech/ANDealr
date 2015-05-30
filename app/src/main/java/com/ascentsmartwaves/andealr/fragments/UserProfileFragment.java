package com.ascentsmartwaves.andealr.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.activities.ProfileActivity;
import com.ascentsmartwaves.andealr.async.MerchantProfileAsyncTask;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.AndroidMultiPartEntity;
import com.ascentsmartwaves.andealr.utils.Constants;
import com.ascentsmartwaves.andealr.utils.DatePickerUtil;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

public class UserProfileFragment extends Fragment {
    ImageView editprofile;
    RelativeLayout profilelayout, mainlayout;
    LinearLayout profiledet, profileedit;
    String id,updateresult,imagepath;
    Button updateprofile;
    int serverResponseCode = 0;
    ImageLoader imageLoader;
    RadioButton male,female;
    ImageView profileimg;
    private int pYear;
    private int pMonth;
    private int pDay;
    static final int DATE_DIALOG_ID = 0;
    String firstName, lastName,handle, contactNo,gender, alternateNo, emailID, dateOfBirth, merchantHandle, photo, dealsPushed, dealLikes, dealRedeems = null;
//    address1, address2, city, state, country,
    TextView name_tv, contactNo_tv,gender_tv, emailID_tv, dateOfBirth_tv,dateOfBirth_et,alternateNo_tv;
//    address1_tv, address2_tv, city_tv, state_tv, country_tv;
   ProgressDialog dialog;
    EditText fname_et,lname_et, alternateNo_et, address1_et, address2_et, city_et, state_et, country_et, merchantHandle_et;
    Bitmap icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Log.d(Constants.LOG_TAG,Constants.UserProfileFragment);
        View v = inflater.inflate(R.layout.activity_user_profile, container, false);
        editprofile = (ImageView) v.findViewById(R.id.editprofile);
        profiledet = (LinearLayout) v.findViewById(R.id.profile_details_layout);
        profileedit = (LinearLayout) v.findViewById(R.id.edit_profile_layout);
        profilelayout = (RelativeLayout) v.findViewById(R.id.profile_layout_user_profile);
        mainlayout = (RelativeLayout) v.findViewById(R.id.mainlayout_userprofile);
        male=(RadioButton)v.findViewById(R.id.radioM);
        female=(RadioButton)v.findViewById(R.id.radioF);
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
//      imagepath=prefs.getString("picturepath", "NO DATA");

        name_tv = (TextView) v.findViewById(R.id.merchant_name);
        contactNo_tv = (TextView) v.findViewById(R.id.merchant_mobile_profile);
//        alternateNo_tv=(TextView)v.findViewById(R.id.d_id);
        emailID_tv = (TextView) v.findViewById(R.id.merchant_email_id_profile);
        dateOfBirth_tv = (TextView) v.findViewById(R.id.merchant_DOB_profile);
        gender_tv = (TextView) v.findViewById(R.id.merchant_gender_profile);
        alternateNo_tv= (TextView) v.findViewById(R.id.merchant_alternateno_profile);
        imageLoader = new ImageLoader(getActivity().getApplicationContext());


        fname_et=(EditText) v.findViewById(R.id.merchant_firstname_activty_register);
        lname_et=(EditText) v.findViewById(R.id.merchant_lastname_activty_register);



        alternateNo_et=(EditText) v.findViewById(R.id.merchant_alternate);
        dateOfBirth_et=(TextView) v.findViewById(R.id.merchant_DOB);
        merchantHandle_et=(EditText) v.findViewById(R.id.merchant_handle_profile);


        profileimg=(ImageView) v.findViewById(R.id.profileimg);
        profileimg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });



        dateOfBirth_et.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePicker();
            }
        });

        final ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected())
        {
            retreiveUserInformation();
        }
        else if (mobile.isConnected())
        {
            retreiveUserInformation();
        }
        else
        {
            profilelayout.setVisibility(View.INVISIBLE);
            mainlayout.setBackgroundResource(R.drawable.no_internet);
        }

        return v;
    }


    private void retreiveUserInformation()
    {
        new MerchantProfileAsyncTask(getActivity().getApplicationContext(),new MerchantProfileAsyncTask.MerchantProfileAsyncTaskCallback() {
            @Override
            public void onStart(boolean a)
            {
                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Retrieving Data");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);
            }
            @Override
            public void onResult(boolean b) {
                if (b)
                {
                    dialog.dismiss();
                    showinfo();
                }
                else
                {
                    dialog.dismiss();
                    AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                    builder.setTitle("Profile");
                    builder.setMessage("Cannot Connect to Server");
                    builder.show();
                }

            }
        }).execute(Constants.fetchMerchantProfileURL + Constants.merchantId);

    }


    private void showinfo()
    {


        if (firstName.equals("null"))
        {

        }
        else
        {
            name_tv.setText(firstName + " " + lastName);
            emailID_tv.setText(emailID);
            contactNo_tv.setText(contactNo);
            dateOfBirth_tv.setText(dateOfBirth);
            merchantHandle_et.setHint("& "+merchantHandle);
            alternateNo_tv.setText(alternateNo);
            gender_tv.setText(gender);
            fname_et.setText(firstName);

            lname_et.setText(lastName);
            dateOfBirth_et.setText(dateOfBirth);
            alternateNo_et.setText(alternateNo);
        }
        Log.d("PHOTO",photo.replaceAll(" ","%20"));
        ImageView profile = (ImageView) getActivity().findViewById(R.id.profileimg);
        imageLoader.DisplayImage( photo.replaceAll(" ","%20"), profile);

//        Constants.merchantprofiledata.clear();
    }


    private void showDatePicker() {
        DatePickerUtil date = new DatePickerUtil();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateOfBirth_et.setText( String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(year));
        }
    };



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
            imagepath = cursor.getString(columnIndex);
            cursor.close();
//            icon = BitmapFactory.decodeFile(imagepath);
            profileimg.setImageBitmap(getScaledBitmap(imagepath, 200, 200));
            new UploadImageDealer().execute();

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







    private void functionupdateprofile()
    {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Uploading Profile Picture");
        dialog.setMessage("Loading... please wait");
        dialog.show();
        dialog.setCancelable(false);
        if(imagepath==null)
        {

                firstName = String.valueOf(fname_et.getText());
                lastName = String.valueOf(lname_et.getText());
                alternateNo = String.valueOf(alternateNo_et.getText());
                dateOfBirth = String.valueOf(dateOfBirth_et.getText());
                merchantHandle = String.valueOf(merchantHandle_et.getText());
                if(merchantHandle.equals("") || (merchantHandle.equals(null)))
                {
                    merchantHandle = handle;
                }

                firstName = firstName.replaceAll(" ","%20");
                lastName = lastName.replaceAll(" ","%20");
                alternateNo = alternateNo.replaceAll(" ","%20");
                dateOfBirth = dateOfBirth.replaceAll(" ","%20");

                try {
                    merchantHandle =  URLEncoder.encode(merchantHandle, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                gender="male";
                if(male.isChecked())
                {
                    gender="male";
                }
                else
                {
                    gender="female";
                }
                if(Constants.CompanyLogoURL ==null)
                {
                    Constants.CompanyLogoURL =photo;
                }

                if(firstName.length()>0 && lastName.length()>0)
                {
                    if (alternateNo.length() > 0)
                    {
                        if (dateOfBirth.length() > 0)
                        {
                            new UpdateAsyncTask().execute("http://integration.andealr.com/apps/v1.0/andealr/test/updateMerchant.php?merchantID=" + Constants.merchantId + "&firstName=" + firstName + "&lastName=" + lastName + "&alternateNo=" + alternateNo + "&dateOfBirth=" + dateOfBirth + "&address1=&address2=&city=&state=&country=&merchantHandle=" + merchantHandle + "&photo=" + Constants.CompanyLogoURL.replaceAll(" ", "%20") + "&gender="+gender);
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            else
            {
                new UploadImageDealer().execute();
            }

    }









    @Override
    public void onDestroy()
    {
        super.onStop();
        if(icon!=null)
        {
            icon.recycle();
        }
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
            HttpPost httppost = new HttpPost(Constants.FILE_UPLOAD_URL_MERCHANTPROFILE);
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
            dialog.dismiss();
            firstName = String.valueOf(fname_et.getText());
            lastName = String.valueOf(lname_et.getText());
            alternateNo = String.valueOf(alternateNo_et.getText());
            dateOfBirth = String.valueOf(dateOfBirth_et.getText());
            merchantHandle = String.valueOf(merchantHandle_et.getText());

            if(merchantHandle.equals("") || (merchantHandle.equals(null)))
            {
                merchantHandle = handle;
            }

            firstName = firstName.replaceAll(" ","%20");
            lastName = lastName.replaceAll(" ","%20");
            alternateNo = alternateNo.replaceAll(" ","%20");
            dateOfBirth = dateOfBirth.replaceAll(" ","%20");
            try {
                merchantHandle =  URLEncoder.encode(merchantHandle, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(male.isChecked())
            {
            gender="male";
            }
            else
            {
            gender="female";
            }

            if(gender==null)
            {
                gender="male";
            }
            if(Constants.CompanyLogoURL ==null)
            {
                Constants.CompanyLogoURL =photo;
            }
            if(firstName.length()>0 && lastName.length()>0)
            {
                if (alternateNo.length() > 0)
                {
                    if (dateOfBirth.length() > 0)
                    {
                        new UpdateAsyncTask().execute("http://integration.andealr.com/apps/v1.0/andealr/test/updateMerchant.php?merchantID=" + Constants.merchantId + "&firstName=" + firstName + "&lastName=" + lastName + "&alternateNo=" + alternateNo + "&dateOfBirth=" + dateOfBirth + "&address1=&address2=&city=&state=&country=&merchantHandle=" + merchantHandle + "&photo=" + Constants.photoURL.replaceAll(" ", "%20") + "&gender="+gender);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getActivity(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
            }
        }
    }




    //JSON FOR UPDATE PROFILE
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