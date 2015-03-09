package com.ascent.andealr.utils;

import android.graphics.Typeface;

import com.ascent.andealr.R;
import com.ascent.andealr.data.FollowersData;
import com.ascent.andealr.data.LandingFragmentData;
import com.ascent.andealr.data.NotificationsData;
import com.ascent.andealr.data.PaymentsData;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by ADMIN on 22-12-2014.
 */
public class Constants {

    public static ArrayList<LandingFragmentData> landingFragmentData;
    public static String landingFragmentTittle[] = {"Buy 1 Get 1","Flat 40%","Happy Hours from 5 to 8pm","10% off on college id"};
    public static String landingFragmentDescription[] = {"On All Footwears","On Apparels","On Indian Starters","On Any Stationary"};
    public static String[] TitleOfDrawersListView={

            "Home",
            "Scan QR",
            "Followers",
            "Payment",
            "Notificaltion",
            "Rate Us",

    };

    public static ArrayList<FollowersData> followersData;
    public static ArrayList<PaymentsData> paymentsData;
    public static ArrayList<NotificationsData> notificationsData;
    public static int logosOfDrawersListView[] = {R.drawable.homeicon,R.drawable.scanqricon,R.drawable.followericon,R.drawable.paymenticon,R.drawable.notificationicon,R.drawable.rateicon};
    public static Typeface customFont , customFont1;
    public static GoogleApiClient mGoogleApiClient;

    public static boolean mSignInClicked;

//    public static int backgroundImagesForLandingFragment[] = {R.drawable.b,R.drawable.a,R.drawable.c,R.drawable.d};
    public static String addDealUrl ="http://andnrbytest190215.ascentinc.in/addDeal.php?";
    public static String photoURL;
    public static String CompanyphotoURL;
    public static String photoId;
    public static int photoIdofAddedDeal = 0;
    public static String fetchDealDetailsURL = "http://andnrbytest190215.ascentinc.in/merchantDealDetails.php?dealID=1";
    public static String checkValidityURL = "http://andnrbytest190215.ascentinc.in/redeemCheck.php?userID=";
    public static boolean dealValidity = false;
    public static String fetchNotificationsURL = "http://andnrbytest190215.ascentinc.in/dealValidityCheck.php?merchantID=";
}





