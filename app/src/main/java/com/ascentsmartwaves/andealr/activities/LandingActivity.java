package com.ascentsmartwaves.andealr.activities;

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
import com.ascentsmartwaves.andealr.async.FetchProfileAsyncTask;
import com.ascentsmartwaves.andealr.async.CheckProfileValidityAsyncTask;
import com.ascentsmartwaves.andealr.data.DrawerListData;
import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.fragments.FollowersFragment;
import com.ascentsmartwaves.andealr.fragments.LandingFragment;
import com.ascentsmartwaves.andealr.fragments.NotificationsFragment;
import com.ascentsmartwaves.andealr.fragments.PaymentsFragment;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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

        Constants.landingFragmentData = new ArrayList<LandingFragmentData>();
        setContentView(R.layout.activity_landing);

        mTitle = mDrawerTitle = getTitle();

        mNavigationDrawerItemTitles= Constants.titleOfDrawersListView;
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
            mDrawerListData.add(new DrawerListData(Constants.titleOfDrawersListView[i], Constants.logosOfDrawersListView[i]));
        }
        // above for loop adds the CONTENTS of the list view in the drawer

        customDrawerListAdapter = new CustomDrawerListAdapter(getApplicationContext(),mDrawerListData);
        mDrawerList.setAdapter(customDrawerListAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new DrawerItemListener());

        profile.setOnClickListener(listener);

        profilelayout.setOnClickListener(new View.OnClickListener()
        {
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

        new FetchProfileAsyncTask(getApplicationContext(),new FetchProfileAsyncTask.FetchProfileAsyncTaskCallback() {
            @Override
            public void onStart(boolean a)
            {
                dialog = new ProgressDialog(LandingActivity.this);
                dialog.setTitle("Receiving Data");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);
                Log.d(Constants.LOG_TAG,Constants.fetchMerchantProfileURL + Constants.merchantId);
            }
            @Override
            public void onResult(boolean b) throws UnsupportedEncodingException, URISyntaxException {
                dialog.dismiss();
                if (b)
                {
                    if(Constants.handle!=null)
                    {
                        URI uri = new URI(Constants.companyLogo.replace(" ", "%20"));
                        name.setText("& "+Constants.handle);
                        imageLoader.DisplayImage(uri.toString(),profile);
                        Log.d(Constants.LOG_TAG,Constants.companyLogo);
                    }
                    if (Constants.handle.equals("null"))
                    {
                        name.setText("& Handle");
                    }
                }
                else
                {

                }
            }
        }).execute(Constants.fetchMerchantProfileURL + Constants.merchantId);
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        Constants.landingFragmentData.clear();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawer);
//        menu.findItem(R.id.settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent i;
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch(item.getItemId())
        {
            case R.id.settings:
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
            case 1 :
                i=new Intent(getApplicationContext(),ScannerActivity.class);
                startActivity(i);
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
                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
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


        new CheckProfileValidityAsyncTask(getApplicationContext().getApplicationContext(),new CheckProfileValidityAsyncTask.ProfileValidAsyncTaskCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(LandingActivity.this);
                dialog.setTitle("Validating");
                dialog.setMessage("Please Wait");
                dialog.show();
                dialog.setCancelable(false);
            }

            @Override
            public void onResult(boolean b)
            {

                if (b)
                {
                    if(Constants.accountStatus.equals("Profile not updated"))
                    {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LandingActivity.this);
                        builder.setMessage("Please complete your profile").setPositiveButton("OK", dialogClickListener)
                                .setNegativeButton("Cancel", dialogClickListener).show();
                    }
                    else if(Constants.accountStatus.equals("Account not verified"))
                    {
                        dialog.dismiss();
                        AlertDialog builder = new AlertDialog.Builder(LandingActivity.this).create();
                        builder.setTitle("ANDealr");
                        builder.setMessage("Your account is under verification, you will be notified once account is approved.");
                        builder.show();
                    }
                    else if(Constants.accountStatus.equals("Profile updated"))
                    {
                        dialog.dismiss();
                        Intent i = new Intent(LandingActivity.this, AddDealActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        dialog.dismiss();
                      Toast.makeText(getApplicationContext(),"Cannot connect to the server",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).execute(Constants.validUserURL +Constants.merchantId);

    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(LandingActivity.this, ProfileActivity.class);
                    startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };






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
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Get Amazing Deals and boost your sales only on ANDealr");
                    share.putExtra(android.content.Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    Intent mailer = Intent.createChooser(share , null);
                    mailer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mailer);
                 break;

//                z.putExtra(android.content.Intent.EXTRA_SUBJECT, "Important Message");

            }
        }
    };

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap)
    {
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
