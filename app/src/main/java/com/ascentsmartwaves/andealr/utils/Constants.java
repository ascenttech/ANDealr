package com.ascentsmartwaves.andealr.utils;

import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.AllDealsData;
import com.ascentsmartwaves.andealr.data.AllProductsData;
import com.ascentsmartwaves.andealr.data.FollowersData;
import com.ascentsmartwaves.andealr.data.LandingFragmentDetail;
import com.ascentsmartwaves.andealr.data.NotificationsData;
import com.ascentsmartwaves.andealr.data.NotificationsDataPrevious;
import com.ascentsmartwaves.andealr.data.PaymentsData;
import com.ascentsmartwaves.andealr.data.UserProfileData;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by ADMIN on 22-12-2014.
 */
public class Constants
{
    public static final String LOG_TAG = "ANDealr";

    public static String[] titleOfDrawersListView ={
            "Home","Scan QR","Followers","Ledger","Notifications","Rate Us"
    };


    public static SharedPreferences.Editor editor;

    public static int logosOfDrawersListView[] = {R.drawable.home_icon,R.drawable.scan_qr_icon,R.drawable.follower_icon,R.drawable.payment_icon,R.drawable.notification_icon,R.drawable.rate_icon};
    public static Typeface customFont , customFont1;
    public static GoogleApiClient mGoogleApiClient;

    public static boolean mSignInClicked;

    // Constants Data
    public static String photoURL;
    public static String fname,lname,photolandingimg;
    public static String CompanyLogoURL;
    public static String photoId;
    public static String GCMRegID;
    public static String accountStatus;
    public static String profileStatus;
    public static final String IMAGE_DIRECTORY_NAME = "Deals Upload";


    public static int photoIdofAddedDeal = 0;
    public static boolean userValidity = false;

    public static String dealValidity ;
    public static String merchantId ;
    public static String merchantRegisterationStatus ;
    public static String field ;
    public static String handle ;
    public static String companyLogo ;
    public static String currentBalance ;
    public static int balance ;


    // Log Tags for all the activities
    public static final String AboutUsActivity = " ABOUT_US_ACTIVITY ";
    public static final String AddDealActivity = " ADD_DEAL_ACTIVITY ";
    public static final String DealDetailsActivity = " DEAL_DETAILS_ACTIVITY ";
    public static final String LandingActivity = " LANDING_ACTIVITY ";
    public static final String LoginOrRegisterActivity = " LOGIN_OR_REGISTER_ACTIVITY ";
    public static final String PrivacyPolicyActivity = " PRIVACY_POLICY_ACTIVITY ";
    public static final String ProfileActivity = " PROFILE_ACTIVITY ";
    public static final String ScannerActivity = " SCANNER_ACTIVITY ";
    public static final String SettingsActivity = " SETTINGS_ACTIVITY ";
    public static final String SignUpActivity = " SIGN_UP_ACTIVITY ";
    public static final String SplashScreenActivity = " SPLASH_SCREEN_ACTIVITY ";
    public static final String TermsAndConditionActivity = " TERMS_AND_CONDITION_ACTIVITY ";


    // Log Tags for all the adapters
    public static final String AllDealsRecyclerAdapter = " ALL DEALS RECYCLER ADAPTER ";
    public static final String AllProductsRecyclerAdapter = " ALL PRODUCTS RECYCLER ADAPTER ";
    public static final String CustomDrawerListAdapter = " CUSTOM_DRAWER_LIST_ADAPTER ";
    public static final String FollowersAdapter = " FOLLOWERS_ADAPTER ";
    public static final String NotificationsFragmentAdapter = " NOTIFICATIONS_FRAGMENT_ADAPTER ";
    public static final String PaymentsAdapter = " PAYMENTS_ADAPTER ";

    // Log Tags for all the asyncTask

    public static final String AddDealAsyncTask = " ADD_DEAL_ASYNC_TASK ";
    public static final String CheckValidityAsyncTask = " ADD_DEAL_ASYNC_TASK ";
    public static final String CheckProfileValidityAsyncTask = " CHECK_PROFILE_VALIDITY_ASYNC_TASK ";
    public static final String FetchDealAsyncTask = " FETCH_DEAL_ASYNC_TASK ";
    public static final String FetchDealDetailsAsyncTask = " FETCH_DEAL_DETAILS_ASYNC_TASK ";
    public static final String FetchFollowersAsyncTask = " FETCH_FOLLOWERS_ASYNC_TASK ";
    public static final String FetchNotificationsAsyncTask = " FETCH_NOTIFICATIONS_ASYNC_TASK ";
    public static final String FetchPaymentsAsyncTask = " FETCH_PAYMENTS_ASYNC_TASK ";
    public static final String FetchProfileAsyncTask = " FETCH_PROFILE_ASYNC_TASK ";
    public static final String MerchantProfileAsyncTask = " MERCHANT_PROFILE_ASYNC_TASK ";
    public static final String RegisterMerchantAsyncTask = " REGISTER_MERCHANT_ASYNC_TASK ";
    public static final String UpdateProfileAsyncTask = " UPDATE_PROFILE_ASYNC_TASK ";
    public static final String UploadProfileImageAsyncTask = " UPDATE_PROFILE_IMAGE_ASYNC_TASK ";



    // Log Tags for all the fragments
    public static final String FollowersFragment = " FOLLOWERS_FRAGMENT ";
    public static final String LandingFragment = " LANDING_FRAGMENT ";
    public static final String NotificationsFragment = " NOTIFICATIONS_FRAGMENT ";
    public static final String PaymentsFragment = " PAYMENTS_FRAGMENT ";
    public static final String UserProfileFragment = " USER_PROFILE_FRAGMENT ";

    // All the Data arraylist
    public static ArrayList<AllDealsData> allDealsData;
    public static ArrayList<AllProductsData> allProductsData;
    public static ArrayList<LandingFragmentDetail> landingFragmentDetail;
    public static ArrayList<FollowersData> followersData;
    public static ArrayList<PaymentsData> paymentsData;
    public static ArrayList<UserProfileData> userProfileData;
    public static ArrayList<NotificationsData> notificationsData;
    public static ArrayList<NotificationsDataPrevious> notificationsDataPrevious;

    // All the links to fetch the data


//    TEST ENVIROMENT
    public static String registerMerchant = "http://integration.andealr.com/apps/v1.0/andealr/test/merchantRegistration.php?emailID=";
    public static String fetchDealDetailsURL = "http://integration.andealr.com/apps/v1.0/andealr/test/merchantDealDetails.php?dealID=";
    public static String checkValidityURL = "http://integration.andealr.com/apps/v1.0/andealr/test/redeemCheck.php?userID=";
    public static String fetchNotificationsURL = "http://integration.andealr.com/apps/v1.0/andealr/test/dealValidityCheck.php?merchantID=";
    public static String fetchProfileURL = "http://integration.andealr.com/apps/v1.0/andealr/test/merchantProfile.php?merchantID=";
    public static String fetchDealURL = "http://integration.andealr.com/apps/v1.0/andealr/test/merchantDeals.php?merchantID=";
    public static String addDealUrl ="http://integration.andealr.com/apps/v1.0/andealr/test/addDeal.php?merchantID=";
    public static String fetchFollowersURL = "http://integration.andealr.com/apps/v1.0/andealr/test/json_followers.php?merchantID=";
    public static String fetchPaymentURL = "http://integration.andealr.com/apps/v1.0/andealr/test/paymentDetails.php?merchantID=";
    public static String fetchMerchantProfileURL="http://integration.andealr.com/apps/v1.0/andealr/test/merchantProfile.php?merchantID=";
    public static String validUserURL = "http://integration.andealr.com/apps/v1.0/andealr/test/checkProfile.php?merchantID=";
    public static final String FILE_UPLOAD_URL_MERCHANTPROFILE = "http://integration.andealr.com/apps/v1.0/andealr/test/merchantImage.php?merchantID=";
    public static final String dealImageUploadURL = "http://integration.andealr.com/apps/v1.0/andealr/test/dealImageUpload.php";
    public static final String updateMerchantProfileURL="http://integration.andealr.com/apps/v1.0/andealr/test/updateMerchant.php?merchantID=";
    public static final String facebookRegistrationURL ="http://integration.andealr.com/apps/v1.0/andealr/test/merchantRegistrationFacebook.php?emailID=";
    public static final String googleRegistrationURL ="http://integration.andealr.com/apps/v1.0/andealr/test/merchantRegistrationGoogle.php?firstName=";
    public static final String fetchNotificationsForDealerURL ="http://integration.andealr.com/apps/v1.0/andealr/test/merchantNotifications.php?merchantID=";



//  PRODUCTION ENVIROMENT
//    public static String registerMerchant = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantRegistration.php?emailID=";
//    public static String fetchDealDetailsURL = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantDealDetails.php?dealID=";
//    public static String checkValidityURL = "http://integration.andealr.com/apps/v1.0/andealr/json/redeemCheck.php?userID=";
//    public static String fetchNotificationsURL = "http://integration.andealr.com/apps/v1.0/andealr/json/dealValidityCheck.php?merchantID=";
//    public static String fetchProfileURL = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantProfile.php?merchantID=";
//    public static String fetchDealURL = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantDeals.php?merchantID=";
//    public static String addDealUrl ="http://integration.andealr.com/apps/v1.0/andealr/json/addDeal.php?merchantID=";
//    public static String fetchFollowersURL = "http://integration.andealr.com/apps/v1.0/andealr/json/json_followers.php?merchantID=";
//    public static String fetchPaymentURL = "http://integration.andealr.com/apps/v1.0/andealr/json/paymentDetails.php?merchantID=";
//    public static String fetchMerchantProfileURL="http://integration.andealr.com/apps/v1.0/andealr/json/merchantProfile.php?merchantID=";
//    public static String validUserURL = "http://integration.andealr.com/apps/v1.0/andealr/json/checkProfile.php?merchantID=";
//    public static final String FILE_UPLOAD_URL_MERCHANTPROFILE = "http://integration.andealr.com/apps/v1.0/andealr/json/merchantImage.php?merchantID=";
//    public static final String dealImageUploadURL = "http://integration.andealr.com/apps/v1.0/andealr/json/dealImageUpload.php";
//    public static final String updateMerchantProfileURL="http://integration.andealr.com/apps/v1.0/andealr/json/updateMerchant.php?merchantID=";
//    public static final String facebookRegistrationURL ="http://integration.andealr.com/apps/v1.0/andealr/json/merchantRegistrationFacebook.php?emailID=";
//    public static final String googleRegistrationURL ="http://integration.andealr.com/apps/v1.0/andealr/json/merchantRegistrationGoogle.php?firstName=";
//    public static final String fetchNotificationsForDealerURL ="http://integration.andealr.com/apps/v1.0/andealr/json/merchantNotifications.php?merchantID=";

}





