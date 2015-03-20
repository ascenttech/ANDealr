package com.ascentsmartwaves.andealr.utils;

import android.graphics.Typeface;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.FollowersData;
import com.ascentsmartwaves.andealr.data.LandingFragmentData;
import com.ascentsmartwaves.andealr.data.MerchantProfileData;
import com.ascentsmartwaves.andealr.data.NotificationsData;
import com.ascentsmartwaves.andealr.data.PaymentsData;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by ADMIN on 22-12-2014.
 */
public class Constants {

    public static final String LOG_TAG = "Andealr";

      public static String[] TitleOfDrawersListView={
            "Home","Scan QR","Followers","Payment","Notification","Rate Us"
    };

    public static int logosOfDrawersListView[] = {R.drawable.home_icon,R.drawable.scan_qr_icon,R.drawable.follower_icon,R.drawable.payment_icon,R.drawable.notification_icon,R.drawable.rate_icon};
    public static Typeface customFont , customFont1;
    public static GoogleApiClient mGoogleApiClient;

    public static boolean mSignInClicked;

    // Constants Data
    public static String photoURL;
    public static String fname,lname,photolandingimg;
    public static String CompanyphotoURL;
    public static String photoId;
    public static int photoIdofAddedDeal = 0;
    public static boolean dealValidity = false;
    public static boolean userValidity = false;
    public static String merchantId ;

    // All the Data arraylist
    public static ArrayList<LandingFragmentData> landingFragmentData;
    public static ArrayList<FollowersData> followersData;
    public static ArrayList<PaymentsData> paymentsData;
    public static ArrayList<NotificationsData> notificationsData;
    public static ArrayList<MerchantProfileData> merchantprofiledata;

    // All the links to fetch the data

    public static String registerMerchant = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantRegistration.php?emailID=";
    public static String fetchDealDetailsURL = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantDealDetails.php?dealID=";
    public static String checkValidityURL = "http://integration.andealr.com/apps/v1.0/andealr/json/redeemCheck.php?userID=";
    public static String fetchNotificationsURL = "http://integration.andealr.com/apps/v1.0/andealr/json/dealValidityCheck.php?merchantID=";
    public static String fetchProfileURL = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantProfile.php?merchantID=";
    public static String fetchDealURL = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantDeals.php?merchantID=";
    public static String addDealUrl ="http://integration.andealr.com/apps/v1.0/andealr/json/addDeal.php?merchantID=";
    public static String fetchFollowersURL = "http://integration.andealr.com/apps/v1.0/andealr/json/json_followers.php?merchantID=";
    public static String fetchPaymentURL = "http://integration.andealr.com/apps/v1.0/andealr/json/paymentDetails.php?merchantID=";
    public static String fetchMerchantProfileURL="http://integration.andealr.com/apps/v1.0/andealr/json/merchantProfile.php?merchantID=";
    public static String validUserURL = "http://integration.andealr.com/apps/v1.0/andealr/json/checkProfile.php?merchantID=";


}





