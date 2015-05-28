package com.ascentsmartwaves.andealr.utils;

import android.content.SharedPreferences;
import android.graphics.Typeface;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.FollowersData;
import com.ascentsmartwaves.andealr.data.LandingFragmentData;
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
    public static final String LOG_TAG = "Andealr";

    public static String[] TitleOfDrawersListView={
            "Home","Scan QR","Followers","Payment","Notification","Rate Us"
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
    public static final String IMAGE_DIRECTORY_NAME = "Deals Upload";


    public static int photoIdofAddedDeal = 0;
    public static boolean userValidity = false;

    public static String dealValidity ;
    public static String merchantId ;
    public static String merchantRegisterationStatus ;
    public static String field ;
    public static String handle ;
    public static String companyLogo ;
    public static int balance ;


    // Log Tags for all the activities
    public static final String AbooutUsActivity = " ABOUT_US_ACTIVITY ";
    public static final String AddDealActivity = " ADD_DEAL_ACTIVITY ";
    public static final String DealDetailsActivity = " DEAL_DETAILS_ACTIVITY ";
    public static final String LandingActivity = " LANDING_ACTIVITY ";
    public static final String LogInActivity = " LOGIN_ACTIVITY ";
    public static final String LoginOrRegisterActivity = " LOGIN_OR_REGISTER_ACTIVITY ";
    public static final String NotificationsActivity = " NOTIFICATIONS_ACTIVITY ";
    public static final String PrivacyPolicyActivity = " PRIVACY_POLICY_ACTIVITY ";
    public static final String ProfileActivity = " PROFILE_ACTIVITY ";
    public static final String ScannerActivity = " SCANNER_ACTIVITY ";
    public static final String SignUpActivity = " SIGN_UP_ACTIVITY ";
    public static final String SplashScreenActivity = " SPLASH_SCREEN_ACTIVITY ";
    public static final String TermsAndConditionActivity = " TERMS_AND_CONDITION_ACTIVITY ";


    // Log Tags for all the adapters
    // Log Tags for all the asyncTask


    // All the Data arraylist
    public static ArrayList<LandingFragmentData> landingFragmentData;
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





