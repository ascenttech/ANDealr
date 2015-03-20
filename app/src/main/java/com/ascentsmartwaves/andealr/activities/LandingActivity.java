package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.adapters.CustomDrawerListAdapter;
import com.ascentsmartwaves.andealr.async.CheckValidityAsyncTask;
import com.ascentsmartwaves.andealr.async.FetchProfileAsyncTask;
import com.ascentsmartwaves.andealr.async.ProfileValidAsyncTask;
import com.ascentsmartwaves.andealr.data.DrawerListData;
import com.ascentsmartwaves.andealr.fragments.FollowersFragment;
import com.ascentsmartwaves.andealr.fragments.LandingFragment;
import com.ascentsmartwaves.andealr.fragments.NotificationsFragment;
import com.ascentsmartwaves.andealr.fragments.PaymentsFragment;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;
import com.google.zxing.integration.android.IntentIntegrator;

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
    RelativeLayout profilelayout;
    String id;
    TextView name;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ProgressDialog dialog;
    Button shareTheApp;
    int backCounter =0;

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
        shareTheApp = (Button) findViewById(R.id.shareTheAppButton);
        profilelayout=(RelativeLayout)findViewById(R.id.profileRL);


        shareTheApp.setOnClickListener(listener);
        navigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        name=(TextView)findViewById(R.id.profile_name_activity_main);
        //setting image
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
        profile.setImageResource(R.drawable.default_avatar);



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

        profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });
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

        new FetchProfileAsyncTask(getApplicationContext(),new FetchProfileAsyncTask.FetchProfileCallback(){


            @Override
            public void onStart(boolean a) {
                dialog = new ProgressDialog(LandingActivity.this);
                dialog.setTitle("Creating Your Profile");
                dialog.setMessage("Loading... please wait");
            }

            @Override
            public void onResult(boolean b) {

                dialog.dismiss();
                if(b)
                {

                     if(Constants.fname.equals("null"))
                     {
                    
                     }
                    else
                     {
                        name.setText(Constants.fname + "\n" + Constants.lname);
                        imageLoader.DisplayImage(Constants.photolandingimg.replaceAll(" ", "%20"), profile);
                     }

                }






            }
        }).execute(Constants.fetchProfileURL+Constants.merchantId);


    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        Constants.landingFragmentData.clear();
//    }

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


        new ProfileValidAsyncTask(getApplicationContext().getApplicationContext(),new ProfileValidAsyncTask.ProfileValidAsyncTaskCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(LandingActivity.this);
                dialog.setTitle("Validating");
                dialog.setMessage("Please Wait");
                dialog.show();
                dialog.setCancelable(false);
            }

            @Override
            public void onResult(boolean b) {

                if (b)
                {
                    if(Constants.userValidity)
                    {

                        Intent i = new Intent(LandingActivity.this, AddDealActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(i);
                    }
                }
                else{

                }
            }
        }).execute(Constants.validUserURL +Constants.merchantId);

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
                case R.id.shareTheAppButton:
                    Intent z = new Intent(Intent.ACTION_SEND);
                    z.setType("text/plain");
                    z.putExtra(android.content.Intent.EXTRA_TEXT, "Give Amazing Deals and boost your sales only on ANDealr");

                    Intent mailer = Intent.createChooser(z , null);
                    mailer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mailer);
                 break;

//                z.putExtra(android.content.Intent.EXTRA_SUBJECT, "Important Message");

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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mDrawerLayout.closeDrawer(mDrawer);
//
//
//        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();
//        select(0);
//
//    }

    @Override
    public void onBackPressed()
    {
        ++backCounter;
        if((backCounter%2) == 0)
        {
            Intent intent=new Intent(this,SplashScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Press Back again to exit",5000).show();
        }
        //super.onBackPressed();
    }

}
