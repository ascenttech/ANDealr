package com.ascent.andealr.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.widget.Toast;


import com.ascent.andealr.R;
import com.ascent.andealr.adapters.CustomDrawerListAdapter;
import com.ascent.andealr.async.CheckValidityAsyncTask;
import com.ascent.andealr.data.DrawerListData;
import com.ascent.andealr.fragments.FollowersFragment;
import com.ascent.andealr.fragments.LandingFragment;
import com.ascent.andealr.fragments.NotificationsFragment;
import com.ascent.andealr.fragments.PaymentsFragment;
import com.ascent.andealr.fragments.RewardsFragment;
import com.ascent.andealr.fragments.SendFeedbackFragment;
import com.ascent.andealr.imagecaching.ImageLoader;
import com.ascent.andealr.utils.Constants;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.util.ArrayList;


public class LandingActivity extends ActionBarActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<DrawerListData> mDrawerListData;
    String navigationDrawerTitles[];
    String fname,lname,photo;
    CustomDrawerListAdapter customDrawerListAdapter;
    LinearLayout mDrawer;
    ImageView profile;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    IntentIntegrator scanIntegrator;
    Intent intent;
    ImageLoader imageLoader;
    String id;
    TextView name;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ProgressDialog dialog;
    /// everything is set you need to display navigation icon to toggle the navigation drawer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        mTitle = mDrawerTitle = getTitle();

        mNavigationDrawerItemTitles= Constants.TitleOfDrawersListView;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (LinearLayout) findViewById(R.id.left_drawer_view);
        profile = (ImageView) findViewById(R.id.profile_background);
        navigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);

        name=(TextView)findViewById(R.id.profile_name_activity_main);

        //setting image
       Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
       profile.setImageBitmap(icon);


        mDrawerListData = new ArrayList<DrawerListData>();

        for(int i = 0 ; i <6;i++)
        {
            mDrawerListData.add(new DrawerListData(Constants.TitleOfDrawersListView[i], Constants.logosOfDrawersListView[i]));
        }
        // above for loop adds the CONTENTS of the list view in the drawer

        customDrawerListAdapter = new CustomDrawerListAdapter(getApplicationContext(),mDrawerListData);
        mDrawerList.setAdapter(customDrawerListAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new DrawerItemListener());

        profile.setOnClickListener(listener);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        // This two lines set the first fragment when the app starts
        if(savedInstanceState == null){
            select(0);
        }

        imageLoader = new ImageLoader(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        id = prefs.getString("Merchant Id", "NO DATA");
        new JSONAsyncTask().execute("http://andnrbytest190215.ascentinc.in/merchantProfile.php?merchantID=" + id);

    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getApplicationContext());
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
                    JSONArray jarray = jsono.getJSONArray("merchantProfile");
                    int cou = jarray.length();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        fname = object.getString("firstName");
                        lname = object.getString("lastName");
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
            dialog.cancel();
            try
            {
//
                if(fname.equalsIgnoreCase(null)) {
                    name.setText(fname + "\n" + lname);
//
//                ImageView profile = (ImageView) getActivity().findViewById(R.id.profileimg);
                    imageLoader.DisplayImage(photo.replaceAll(" ", "%20"), profile);
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







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {


            case R.id.action_settings:
                i = new Intent(LandingActivity.this,SettingsActivity.class);
                startActivity(i);
                break;



        }
        return true;
    }



    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



    public void select(int position){
        Intent i;
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new LandingFragment();
                break;
            case 1 : scanQRCode();
                break;
            case 2 :
                fragment = new FollowersFragment();
                break;
            case 3 :
                fragment = new PaymentsFragment();
                break;
            case 4 :
                fragment = new NotificationsFragment();
                break;
            case 5 :
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.king.candycrushsaga&hl=en");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.king.candycrushsaga&hl=en")));
                }
                break;


        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            getSupportActionBar().setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawer);

        } else {
            Log.e("LandingActivity", "Error in creating fragment");
        }

    }


    public void AddDeal(View v)
    {
        Intent i = new Intent(LandingActivity.this, AddDealActivity.class);
        startActivity(i);
    }




    public void scanQRCode() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(LandingActivity.this, "No Scanner Found", "Download a QR Code Scanner ?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                String finalURL = Constants.checkValidityURL + "1" + "&"+"dealID="+"1";

                new CheckValidityAsyncTask(getApplicationContext(),new CheckValidityAsyncTask.CheckValidityCallback() {
                    @Override
                    public void onStart(boolean a) {

                        dialog = new ProgressDialog(LandingActivity.this);
                        dialog.setTitle("Validating");
                        dialog.setMessage("We are validating the deal");
                        dialog.show();
                        dialog.setCancelable(false);

                    }

                    @Override
                    public void onResult(boolean b) {

           dialog.dismiss();

                        AlertDialog builder = new AlertDialog.Builder(LandingActivity.this).create();
                        builder.setTitle("Validity of the Deal");
                        builder.setMessage("Deal is Valid");
                        builder.show();


                    }
                }).execute(finalURL);

            }
        }
    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

//                case R.id.profile_background : getSupportFragmentManager()
//                                                .beginTransaction()
//                                                .replace(R.id.content_frame,new ProfileFragment()).commit();
//                    mDrawerLayout.closeDrawer(mDrawer);
//                    break;
                case R.id.profile_background : mDrawerLayout.closeDrawer(mDrawer);
                    Intent i = new Intent(LandingActivity.this, ProfileActivity.class);
                    startActivity(i);
                    break;

            }
        }
    };

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    private class DrawerItemListener  implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            select(position);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawerLayout.closeDrawer(mDrawer);
    }


}
