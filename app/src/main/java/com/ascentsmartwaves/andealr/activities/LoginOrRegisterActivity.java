package com.ascentsmartwaves.andealr.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.GCM.RegisterApp;
import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.async.RegisterMerchant;
import com.ascentsmartwaves.andealr.utils.Constants;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


public class LoginOrRegisterActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener
{
    private static final int RC_SIGN_IN = 0;
    private static final int PROFILE_PIC_SIZE = 400;
    private boolean mIntentInProgress;
    private ConnectionResult mConnectionResult;
    private SignInButton googleSignIn;
    private UiLifecycleHelper uiHelper;
    LoginButton authButton;
    ProgressDialog dialog;
    String fbemail,fblink,fbid,fbfname,fblname,fbgender,fbimgurl;
    String googleName,googlePhotoUrl,googlePlusProfile,googleemail,googlefname,googlelname,googledisplayname,googleGender;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String TAG = "GCMRelated";
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    String btntext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.LOG_TAG,"REGISTER LOGIN STARTED");

        setContentView(R.layout.activity_login_or_register);
        btntext = "Connect with Google +";

        googleSignIn = (SignInButton) findViewById(R.id.google_btn_sign_in_login_or_register_activity);
        authButton = (LoginButton) findViewById(R.id.facebook_btn_sign_in_login_or_register_activity);
        googleSignIn.setOnClickListener(listener);

        setGooglePlusButtonText(googleSignIn, btntext);

        Constants.editor = getSharedPreferences("UserSession", MODE_PRIVATE).edit();

        Constants.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        authButton.setReadPermissions(Arrays.asList("email"));

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ascentsmartwaves.andealr", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(Constants.LOG_TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }

        SharedPreferences user_session = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String id = user_session.getString("Merchant Id", "NO DATA");
        if (!id.equals("NO DATA"))
        {
            Constants.merchantId=id;
            Intent i = new Intent(getApplicationContext(), LandingActivity.class);
            startActivity(i);
        }
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView)
            {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextSize(17);
                return;
            }
        }
    }


    private Session.StatusCallback callback = new Session.StatusCallback()
    {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state,Exception exception)
    {
        if (state.isOpened())
        {
            Log.i(TAG, "Logged in...");
            // make request to the /me API to get Graph user


            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Set view visibility to true
                        Log.i(TAG, "" + response);
                        fbemail = user.getProperty("email").toString();
                        fbfname = user.getFirstName();
                        fblname = user.getLastName();
                        fbgender = user.getProperty("gender").toString();
                        fbid = user.getProperty("id").toString();
                        fblink = user.getProperty("link").toString();
                        fbimgurl = "https://graph.facebook.com/" + fbid + "/picture?width=400&height=300";

                        Log.d(Constants.LOG_TAG,
                                "EMAIL:" + fbemail + "\n" +
                                        "First Name:" + fbfname + "\n" +
                                        "Last Name:" + fblname + "\n" +
                                        "GENDER:" + fbgender + "\n" +
                                        "FB-ID:" + fbid + "\n" +
                                        "FB-LINK:" + fblink + "\n" +
                                        "FB-URL" + fbimgurl + "\n");

//                        Toast.makeText(getApplicationContext(),
//                                "EMAIL:"+fbemail+"\n"+
//                                "First Name:"+fbfname+"\n"+
//                                "Last Name:"+fblname+"\n"+
//                                "GENDER:"+fbgender+"\n"+
//                                "FB-ID:"+fbid+"\n"+
//                                "FB-LINK:"+fblink+"\n"+
//                                "FB-URL"+fbimgurl+"\n",
//                                Toast.LENGTH_LONG).show();


                        try
                        {
                            fbemail = URLEncoder.encode(fbemail, "UTF-8");
                            fbfname = URLEncoder.encode(fbfname, "UTF-8");
                            fblname = URLEncoder.encode(fblname, "UTF-8");
                            fbgender = URLEncoder.encode(fbgender, "UTF-8");
                            fbid = URLEncoder.encode(fbid, "UTF-8");
                            fblink = URLEncoder.encode(fblink, "UTF-8");
                            fbimgurl = URLEncoder.encode(fbimgurl, "UTF-8");
                        }
                        catch(Exception e)
                        {
                            Log.d(Constants.LOG_TAG,""+e);
                        }


                        new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext()), new RegisterApp.RegisterAppCallback() {
                            @Override
                            public void onStart(boolean status)
                            {
                                dialog = new ProgressDialog(LoginOrRegisterActivity.this);
                                dialog.setTitle("Registering");
                                dialog.setMessage("Please Wait");
                                dialog.show();
                                dialog.setCancelable(false);
                            }
                            @Override
                            public void onResult(boolean result)
                            {
                                dialog.dismiss();
                                String finalurl = Constants.facebookRegistrationURL+fbemail+"&firstName="+fbfname+"&lastName="+fblname+"&gender="+fbgender+"&fbID="+fbid+"&fbLink="+fblink+"&fbImageURL="+fbimgurl+"&registrationID="+Constants.GCMRegID+"&latitude=0.0&longitude=0.0";
                                Log.d(Constants.LOG_TAG, finalurl);
                                new RegisterMerchant(getApplicationContext(), new RegisterMerchant.RegisterMerchantCallback() {
                                    @Override
                                    public void onStart(boolean a)
                                    {
                                        dialog = new ProgressDialog(LoginOrRegisterActivity.this);
                                        dialog.setTitle("Registering");
                                        dialog.setMessage("Loading... please wait");
                                        dialog.show();
                                        dialog.setCancelable(false);
                                    }
                                    @Override
                                    public void onResult(boolean result) {
                                        if (result)
                                        {
                                            dialog.dismiss();
                                            //logout for facebook
                                            if (Session.getActiveSession() != null) {
                                                Session.getActiveSession().closeAndClearTokenInformation();
                                            }
                                            Session.setActiveSession(null);


                                            Constants.editor.putString("Merchant Id", Constants.merchantId);
                                            Constants.editor.commit();

                                            if(Constants.merchantRegisterationStatus.equals("empty"))
                                            {
                                                Intent i = new Intent(LoginOrRegisterActivity.this, ProfileActivity.class);
                                                startActivity(i);
                                            }
                                            else if(Constants.merchantRegisterationStatus.equals("exist"))
                                            {
                                                Intent i = new Intent(LoginOrRegisterActivity.this, LandingActivity.class);
                                                startActivity(i);
                                            }
                                        }
                                        else
                                        {
                                            dialog.dismiss();
                                            //logout for facebook
                                            if (Session.getActiveSession() != null)
                                            {
                                                Session.getActiveSession().closeAndClearTokenInformation();
                                            }
                                            Toast.makeText(getApplicationContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).execute(finalurl);
                            }
                        }).execute();
                    }
                }
            }).executeAsync();
        }
        else if (state.isClosed())
        {
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        uiHelper.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        uiHelper.onPause();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }


    private static int getAppVersion(Context context)
    {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }



    protected void onStart()
    {
        super.onStart();
        Constants.mGoogleApiClient.connect();
    }
    protected void onStop()
    {
        super.onStop();
        if (Constants.mGoogleApiClient.isConnected())
        {
            Constants.mGoogleApiClient.disconnect();
        }
    }



    //FOR GOOGLE
    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        if (!result.hasResolution())
        {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress)
        {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
            if (Constants.mSignInClicked)
            {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }
    @Override
    public void onConnected(Bundle arg0)
    {
        Constants.mSignInClicked = false;
        // Get user's information
        getProfileInformation();
        // Update the UI after signin
        updateUI(true);
    }

    @Override
    public void onConnectionSuspended(int arg0)
    {
        Constants.mGoogleApiClient.connect();
        updateUI(false);
    }
    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn)
    {
        if (isSignedIn)
        {
//            Intent i = new Intent(RegisterLoginActivity.this,LandingActivity.class);
//            startActivity(i);
        }
        else
        {
            // do something
        }
    }
    private void signInWithGplus() {
        if (!Constants.mGoogleApiClient.isConnecting()) {
            Constants.mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                Constants.mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation()
    {
        try
        {
            if (Plus.PeopleApi.getCurrentPerson(Constants.mGoogleApiClient) != null)
            {

                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(Constants.mGoogleApiClient);

                googleName = currentPerson.getDisplayName();
                googlePhotoUrl = currentPerson.getImage().getUrl();
                googlePlusProfile = currentPerson.getUrl();
                googleemail = Plus.AccountApi.getAccountName(Constants.mGoogleApiClient);
                googledisplayname =currentPerson.getDisplayName();
                googlefname= currentPerson.getName().getGivenName();
                googlelname= currentPerson.getName().getFamilyName();
                googlePhotoUrl = googlePhotoUrl.substring(0,
                        googlePhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;
                Log.e(TAG,"Lname: " + googlelname +", Fname: " + googlefname + ", Name: " + googleName + ", plusProfile: "
                        + googlePlusProfile + ", email: " + googleemail
                        + ", Image: " + googlePhotoUrl);
//                Toast.makeText(getApplicationContext(),
//                        "Name: "+googleName+"\n"+
//                                "PlusProfile: "+googlePlusProfile+"\n"+
//                                "Email: "+googleemail+"\n"+
//                                "Image: "+googlePhotoUrl+"\n",
//                        Toast.LENGTH_LONG).show();

                googleName = URLEncoder.encode(googleName,"UTF-8");
                googlePhotoUrl = URLEncoder.encode(googlePhotoUrl,"UTF-8");
                googlePlusProfile = URLEncoder.encode(googlePlusProfile,"UTF-8");
                googleemail = URLEncoder.encode(googleemail,"UTF-8");
                googledisplayname =URLEncoder.encode(googledisplayname,"UTF-8");
                googlefname= URLEncoder.encode(googlefname,"UTF-8");
                googlelname= URLEncoder.encode(googlelname,"UTF-8");

                new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext()), new RegisterApp.RegisterAppCallback() {
                    @Override
                    public void onStart(boolean status)
                    {
                        dialog = new ProgressDialog(LoginOrRegisterActivity.this);
                        dialog.setTitle("Registering");
                        dialog.setMessage("Please Wait");
                        dialog.show();
                        dialog.setCancelable(false);
                    }
                    @Override
                    public void onResult(boolean result)
                    {
                        dialog.dismiss();
                        String finalurl = Constants.googleRegistrationURL + googlefname + "&lastName="+googlelname+"&emailID="+googleemail+"&googleProfileImage="+googlePhotoUrl+"&googleProfileLink="+googlePlusProfile+"&displayName="+googledisplayname+"&googleID=&language=&birthday=&name=&plusProfile=&image=&gender=&city=&registrationID="+Constants.GCMRegID+"&latitude=0.0&longitude=0.0";
                        Log.d(Constants.LOG_TAG, finalurl);
                        new RegisterMerchant(getApplicationContext(), new RegisterMerchant.RegisterMerchantCallback()
                        {
                            @Override
                            public void onStart(boolean a)
                            {
                                dialog = new ProgressDialog(LoginOrRegisterActivity.this);
                                dialog.setTitle("Registering");
                                dialog.setMessage("Loading... please wait");
                                dialog.show();
                                dialog.setCancelable(false);
                            }
                            @Override
                            public void onResult(boolean result)
                            {
                                if (result)
                                {
                                    dialog.dismiss();
                                    //FOR GOOGLE LOGOUT
                                    if (Constants.mGoogleApiClient.isConnected())
                                    {
                                        Plus.AccountApi.clearDefaultAccount(Constants.mGoogleApiClient);
                                        Constants.mGoogleApiClient.disconnect();
                                        Constants.mGoogleApiClient.connect();
                                    }
                                    Constants.editor.putString("Merchant Id", Constants.merchantId);
                                    Constants.editor.commit();

                                    if(Constants.merchantRegisterationStatus.equals("empty"))
                                    {
                                        Intent i = new Intent(LoginOrRegisterActivity.this, ProfileActivity.class);
                                        startActivity(i);
                                    }
                                    else if(Constants.merchantRegisterationStatus.equals("exist"))
                                    {
                                        Intent i = new Intent(LoginOrRegisterActivity.this, LandingActivity.class);
                                        startActivity(i);
                                    }

                                }
                                else
                                {
                                    dialog.dismiss();
                                    //FOR GOOGLE LOGOUT
                                    if (Constants.mGoogleApiClient.isConnected())
                                    {
                                        Plus.AccountApi.clearDefaultAccount(Constants.mGoogleApiClient);
                                        Constants.mGoogleApiClient.disconnect();
                                        Constants.mGoogleApiClient.connect();
                                    }
                                    Toast.makeText(getApplicationContext(), "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).execute(finalurl);
                    }
                }).execute();

            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "Cannot connect to the internet", Toast.LENGTH_LONG).show();
                //FOR GOOGLE LOGOUT
                if (Constants.mGoogleApiClient.isConnected())
                {
                    Plus.AccountApi.clearDefaultAccount(Constants.mGoogleApiClient);
                    Constants.mGoogleApiClient.disconnect();
                    Constants.mGoogleApiClient.connect();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    View.OnClickListener listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent i ;
            switch (v.getId())
            {
                case R.id.google_btn_sign_in_login_or_register_activity: signInWithGplus();
                    break;
            }

        }
    };
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this,SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,Intent intent)
    {
        super.onActivityResult(requestCode, responseCode, intent);
        if (requestCode == RC_SIGN_IN)
        {
            if (responseCode != RESULT_OK)
            {
                Constants.mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!Constants.mGoogleApiClient.isConnecting())
            {
                Constants.mGoogleApiClient.connect();
            }
        }
        Session.getActiveSession().onActivityResult(this, requestCode, responseCode, intent);
        uiHelper.onActivityResult(requestCode, responseCode, intent);
        Log.i(TAG, "OnActivityResult...");
    }
}
