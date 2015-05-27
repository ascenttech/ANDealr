package com.ascentsmartwaves.andealr.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.async.FetchProfileAsyncTask;
import com.ascentsmartwaves.andealr.async.UpdateProfileAsyncTask;
import com.ascentsmartwaves.andealr.async.UploadProfileImageAsyncTask;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


public class ProfileActivity extends ActionBarActivity
{
    TextView companyName_tv,emailID_tv,contactNo_tv,locality_tv,pincode_tv, dealLiked_tv,dealAdded_tv,dealsRedeemed_tv,followers_tv;
    EditText companyName_et, contactNo_et,locality_et,pincode_et,handle_et;
    String companyName,emailId,contactNo,locality,pincode,dealLikes,dealRedeemd,dealAdded,handle,handle1,imageURL;
    String imagepathcompany,followers;
    Button update;
    ImageLoader  imageLoader;
    ImageView editcompanyprofile, profileImg;
    RelativeLayout profilelayout, mainlayout;
    LinearLayout profiledet, profileedit;
    ProgressDialog dialog;
    Bitmap icon;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Constants.userProfileData.clear();
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViews();
        profileImg.setImageResource(R.drawable.templogo);


        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
        profileImg.setOnClickListener(listener);
        update.setOnClickListener(listener);
        editcompanyprofile.setOnClickListener(listener);
    }

    private void findViews()
    {
        companyName_tv = (TextView) findViewById(R.id.company_name_textview_profile);
        contactNo_tv = (TextView) findViewById(R.id.merchant_mobile_profile);
        emailID_tv = (TextView) findViewById(R.id.company_emailId_textview_profile);
        contactNo_tv= (TextView) findViewById(R.id.company_mobile_textview_profile);
        locality_tv = (TextView) findViewById(R.id.company_locality_textview_profile);
        pincode_tv = (TextView) findViewById(R.id.company_pincode_textview_profile);
        dealsRedeemed_tv = (TextView) findViewById(R.id.counterredeem);
        dealLiked_tv = (TextView) findViewById(R.id.counterliked);
        dealAdded_tv = (TextView) findViewById(R.id.counterAdded);
        followers_tv = (TextView) findViewById(R.id.counterfollowers);


        companyName_et=(EditText) findViewById(R.id.merchant_companyname_edittext_profile);
        contactNo_et=(EditText) findViewById(R.id.compny_mobile_edittext_profile);
        locality_et=(EditText) findViewById(R.id.company_locality_edittext_profile);
        pincode_et=(EditText) findViewById(R.id.merchant_pincode_edittext_profile);
        handle_et=(EditText) findViewById(R.id.merchant_handle_profile);


        profiledet = (LinearLayout) findViewById(R.id.company_profile_details_layout);
        profileedit = (LinearLayout) findViewById(R.id.edit_companyprofile_layout);


        profilelayout = (RelativeLayout) findViewById(R.id.display_profile_layout_company_profile);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayoutcompany);


        editcompanyprofile = (ImageView) findViewById(R.id.editcompanyprofile);
        profileImg = (ImageView) findViewById(R.id.companyimg);


        update=(Button) findViewById((R.id.update_companyprofile_btn));


        imageLoader = new ImageLoader(getApplicationContext());
    }

    View.OnClickListener listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent i ;
            switch (v.getId())
            {
                case R.id.companyimg:
                    i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 0);
                break;

                case R.id.update_companyprofile_btn:
                    try
                    {
                        updateCompanyProfile();
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    break;

                case R.id.editcompanyprofile:
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
                    break;
            }

        }
    };




    private void fetchCompanyData()
    {
        new FetchProfileAsyncTask(getApplicationContext(),new FetchProfileAsyncTask.FetchProfileAsyncTaskCallback() {
            @Override
            public void onStart(boolean a)
            {
                dialog = new ProgressDialog(ProfileActivity.this);
                dialog.setTitle("Receiving Data");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);
                Log.d(Constants.LOG_TAG,Constants.fetchMerchantProfileURL + Constants.merchantId);
            }
            @Override
            public void onResult(boolean b) throws URISyntaxException {
                dialog.dismiss();
                if (b)
                {
                    showData();
                }
                else
                {
                    AlertDialog builder = new AlertDialog.Builder(ProfileActivity.this).create();
                    builder.setTitle("Profile");
                    builder.setMessage("Cannot connect to the server");
                    builder.show();
                }
            }
        }).execute(Constants.fetchMerchantProfileURL + Constants.merchantId);
    }


    public void showData() throws URISyntaxException {
        handle=Constants.userProfileData.get(0).getMerchantHandle();
        handle1=Constants.userProfileData.get(0).getMerchantHandle();
        imageURL=Constants.userProfileData.get(0).getCompanyLogo();
        emailId=Constants.userProfileData.get(0).getEmailID();
        contactNo=Constants.userProfileData.get(0).getContactNo();
        companyName=Constants.userProfileData.get(0).getCompanyName();
        locality=Constants.userProfileData.get(0).getLocality();
        pincode=Constants.userProfileData.get(0).getPincode();
        dealLikes=Constants.userProfileData.get(0).getDealLikes();
        dealAdded=Constants.userProfileData.get(0).getDealsPushed();
        dealRedeemd=Constants.userProfileData.get(0).getDealRedeems();
        followers=Constants.userProfileData.get(0).getFollowers();

        if(!handle.equals("null"))
        {
            handle_et.setHint("& "+handle);
        }
        if(!emailId.equals("null"))
        {
            emailID_tv.setText(emailId);
        }
        if(!contactNo.equals("null"))
        {
            if(!contactNo.equals("0"))
            {
                contactNo_tv.setText(contactNo);
                contactNo_et.setText(contactNo);
            }
        }
        if(!companyName.equals("null"))
        {
            companyName_tv.setText(companyName);
            companyName_et.setText(companyName);
        }
        if(!locality.equals("null"))
        {
            locality_tv.setText(locality);
            locality_et.setText(locality);
        }
        if(!pincode.equals("null"))
        {
            if(!contactNo.equals("0"))
            {
                pincode_tv.setText(pincode);
                pincode_et.setText(pincode);
            }
        }
        if(!dealLikes.equals("null"))
        {
            dealLiked_tv.setText(dealLikes);
        }
        if(!dealAdded.equals("null"))
        {
            dealAdded_tv.setText(dealAdded);
        }
        if(!dealRedeemd.equals("null"))
        {
            dealsRedeemed_tv.setText(dealRedeemd);
        }
        if(!followers.equals("null"))
        {
            followers_tv.setText(followers);
        }
        if(!imageURL.equals("null"))
        {
            URI uri = new URI(imageURL.replace(" ", "%20"));
            imageLoader.DisplayImage(uri.toString(),profileImg);
        }

        Constants.userProfileData.clear();
    }

    private void updateCompanyProfile() throws UnsupportedEncodingException
    {
        String handle_hint;
        companyName=companyName_et.getText().toString();
        contactNo=contactNo_et.getText().toString();
        locality=locality_et.getText().toString();
        pincode=pincode_et.getText().toString();
        handle = handle_et.getText().toString();
        handle_hint= handle_et.getHint().toString();

        if(handle.length()==0)
        {
            handle=handle1;
        }
        if(!handle.equals("null"))
        {
            if (handle.length() > 0)
            {
                handle = URLEncoder.encode(handle, "UTF-8");
                Log.d(Constants.LOG_TAG, "handle check" + handle);
                if (companyName.length() > 0)
                {
                    companyName = URLEncoder.encode(companyName, "UTF-8");
                    if (contactNo.length() > 0)
                    {
                        if(contactNo.length() == 10)
                        {
                            contactNo = URLEncoder.encode(contactNo, "UTF-8");
                            if (locality.length() > 0)
                            {
                                locality = URLEncoder.encode(locality, "UTF-8");
                                if (pincode.length() > 0)
                                {
                                    if(pincode.length() == 6)
                                    {
                                        pincode = URLEncoder.encode(pincode, "UTF-8");
                                        updateProfile();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Invalid pincode", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Pincode should not be blank", Toast.LENGTH_SHORT).show();
                                }
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "Locality should not be blank", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid mobile number", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Contact no should not be blank", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Company should not be blank", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Handle should not be blank", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Handle should not be blank", Toast.LENGTH_SHORT).show();
        }


    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    uploadImage();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    imagepathcompany=null;
                    dialog.dismiss();
                    break;
            }
        }
    };

    public void uploadImage()
    {
        new UploadProfileImageAsyncTask(imagepathcompany,getApplicationContext(),new UploadProfileImageAsyncTask.UploadProfileImageAsyncTaskCallback()
        {
            @Override
            public void onStart(boolean a)
            {
                dialog = new ProgressDialog(ProfileActivity.this);
                dialog.setTitle("Uploading Company Logo ");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);
            }
            @Override
            public void onResult(boolean b)
            {
                if (b)
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Logo Uploaded",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog.dismiss();
                }
            }
        }).execute();
    }


    public void updateProfile()
    {
        Log.d(Constants.LOG_TAG,"INSIDE THE UPDATE PROFILE FUNCTION");
        String finalurl=Constants.updateMerchantProfileURL+Constants.merchantId+"&emailID="+emailId+"&handle="+handle+"&companyName="+companyName+"&contactNo="+contactNo+"&locality="+locality+"&pincode="+pincode;
        Log.d(Constants.LOG_TAG,"FINAL URL: "+finalurl);
        new UpdateProfileAsyncTask(getApplicationContext(),new UpdateProfileAsyncTask.UpdateProfileAsyncTaskCallback()
        {
            @Override
            public void onStart(boolean a)
            {
                dialog = new ProgressDialog(ProfileActivity.this);
                dialog.setTitle("Updating Profile ");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);
            }
            @Override
            public void onResult(boolean b)
            {
                if (b)
                {
                    dialog.dismiss();
                    Intent i=new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(i);
                }
                else
                {
                    dialog.dismiss();
                    if(Constants.field.equals("handle"))
                    {
                        Toast.makeText(getApplicationContext(), "Handle is already taken!!", Toast.LENGTH_SHORT).show();
                    }
                    else if(Constants.field.equals("pincode invalid"))
                    {
                        Toast.makeText(getApplicationContext(), "Invalid pincode, please enter a valid pincode", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).execute(finalurl);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagepathcompany = cursor.getString(columnIndex);
            cursor.close();
            profileImg.setImageBitmap(getScaledBitmap(imagepathcompany, 200, 200));
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setMessage("Upload this logo?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show();

        }
    }


    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
        Constants.landingFragmentData.clear();
        Intent i=new Intent(getApplicationContext(),LandingActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Constants.landingFragmentData.clear();
                Intent i=new Intent(getApplicationContext(),LandingActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private Bitmap getScaledBitmap(String picturePath, int width, int height)
    {
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
        if (height > reqHeight || width > reqWidth)
        {
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
}
