package com.ascentsmartwaves.andealr.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.utils.Constants;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;


public class LoginOrRegisterActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener
{

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "LoginOrRegisterActivity";
    // Facebook APP id
    private static String APP_ID = "1075294219152738";
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    private static  int count = 0;
    // stuff required for FACEBOOK
    private Facebook facebook;
    private AsyncFacebookRunner mAsyncRunner;
    String FILENAME = "AndroidSSO_data";
    private SharedPreferences mPrefs;
    // stuff required for FACEBOOK
    // Google client to interact with Google API
//    private GoogleApiClient mGoogleApiClient;
    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;
//    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
//    private SignInButton googleSignIn;
    private ImageView imgProfilePic;
    private Button logIn,register;
//    private Button facebookBtn;
    String name,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);


        logIn = (Button) findViewById(R.id.log_in_btn_login_or_register_activity);
        register = (Button) findViewById(R.id.register_btn_login_or_register_activity);


//        googleSignIn = (SignInButton) findViewById(R.id.google_btn_sign_in_login_or_register_activity);
//        facebookBtn = (Button) findViewById(R.id.facebook_btn_sign_in_login_or_register_activity);
//        facebookBtn.setOnClickListener(listener);
//        googleSignIn.setOnClickListener(listener);



        logIn.setOnClickListener(listener);
        register.setOnClickListener(listener);

        SharedPreferences prefs =getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        name = prefs.getString("DEALER", "NO DATA");
        id = prefs.getString("Merchant Id", "NO DATA");
        if(id.equals("NO DATA"))
        {

        }
        else
        {
            Constants.merchantId=id;
            Intent i = new Intent(LoginOrRegisterActivity.this,LandingActivity.class);
            startActivity(i);
        }





        Constants.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        facebook = new Facebook(APP_ID);
        mAsyncRunner = new AsyncFacebookRunner(facebook);

    }

    protected void onStart() {
        super.onStart();
        Constants.mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (Constants.mGoogleApiClient.isConnected()) {
            Constants.mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent=new Intent(this,SplashScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (Constants.mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                Constants.mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!Constants.mGoogleApiClient.isConnecting()) {
                Constants.mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        Constants.mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        updateUI(true);

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Constants.mGoogleApiClient.connect();
        updateUI(false);
    }

    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            Intent i = new Intent(LoginOrRegisterActivity.this,LandingActivity.class);
            startActivity(i);

        } else {
            // do something

        }
    }

    /**
     * Sign-in into google
     * */
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
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(Constants.mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(Constants.mGoogleApiClient);

                Log.d("SAGAR","person "+ currentPerson);

                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(Constants.mGoogleApiClient);
                String birthday = currentPerson.getBirthday();
                int gender = currentPerson.getGender();
             //   int id = currentPerson.getId();
                String language = currentPerson.getLanguage();



                Log.e("SAGAR", "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl+ ", Birthday: " + birthday+ ", Gender: " + gender);


                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Sign-out from google
     * */
    public void signOutFromGplus() {

        Log.d("SAGAR","IS CONNECTED "+ Constants.mGoogleApiClient.isConnected());

        if (Constants.mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(Constants.mGoogleApiClient);
            Constants.mGoogleApiClient.disconnect();
            Constants.mGoogleApiClient.connect();

//            onCreate();

        }
    }


    public void loginToFacebook() {
        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);

        if (access_token != null) {
            facebook.setAccessToken(access_token);
        }

        if (expires != 0) {
            facebook.setAccessExpires(expires);
        }

        if (!facebook.isSessionValid()) {
            facebook.authorize(this,
                    new String[] { "email", "publish_stream" },
                    new Facebook.DialogListener() {

                        @Override
                        public void onCancel() {
                            // Function to handle cancel event
                        }

                        @Override
                        public void onComplete(Bundle values) {
                            // Function to handle complete event
                            // Edit Preferences and update facebook acess_token
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("access_token",
                                    facebook.getAccessToken());
                            editor.putLong("access_expires",
                                    facebook.getAccessExpires());
                            editor.commit();

                        }

                        @Override
                        public void onError(DialogError error) {
                            // Function to handle error

                        }

                        @Override
                        public void onFacebookError(FacebookError fberror) {
                            // Function to handle Facebook errors

                        }

                    });
        }
    }


    public void logoutFromFacebook() {
        mAsyncRunner.logout(this, new AsyncFacebookRunner.RequestListener() {
            @Override
            public void onComplete(String response, Object state) {
                Log.d("Logout from Facebook", response);
                if (Boolean.parseBoolean(response) == true) {
                    // User successfully Logged out
                }
            }

            @Override
            public void onIOException(IOException e, Object state) {
            }

            @Override
            public void onFileNotFoundException(FileNotFoundException e,
                                                Object state) {
            }

            @Override
            public void onMalformedURLException(MalformedURLException e,
                                                Object state) {
            }

            @Override
            public void onFacebookError(FacebookError e, Object state) {
            }
        });
    }

    public void logIn()
    {
        Intent i=new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(i);
    }
    public void register()
    {
        Intent i=new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(i);
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i ;

            switch (v.getId()){

                case R.id.log_in_btn_login_or_register_activity: logIn();
                    break;
                case R.id.register_btn_login_or_register_activity: register();
                    break;
//                case R.id.google_btn_sign_in_login_or_register_activity: signInWithGplus();
//                    break;
//                case R.id.facebook_btn_sign_in_login_or_register_activity: loginToFacebook();
//                    break;

            }

        }
    };
}
