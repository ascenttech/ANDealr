package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 01-04-2015.
 */
public class UserProfileData
{
    String companyName;
    String emailID;
    String contactNo;
    String companyLogo;
    String locality;
    String dealsPushed;
    String dealLikes;
    String dealRedeems;
    String merchantHandle;
    String pincode;
    String followers;

    public UserProfileData(String companyName, String emailID, String contactNo, String companyLogo, String locality, String dealsPushed, String dealLikes, String dealRedeems, String merchantHandle, String pincode,String followers)
    {
        this.companyName = companyName;
        this.emailID = emailID;
        this.contactNo = contactNo;
        this.companyLogo = companyLogo;
        this.locality = locality;
        this.dealsPushed = dealsPushed;
        this.dealLikes = dealLikes;
        this.dealRedeems = dealRedeems;
        this.merchantHandle = merchantHandle;
        this.pincode = pincode;
        this.followers = followers;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDealsPushed() {
        return dealsPushed;
    }

    public void setDealsPushed(String dealsPushed) {
        this.dealsPushed = dealsPushed;
    }

    public String getDealLikes() {
        return dealLikes;
    }

    public void setDealLikes(String dealLikes) {
        this.dealLikes = dealLikes;
    }

    public String getDealRedeems() {
        return dealRedeems;
    }

    public void setDealRedeems(String dealRedeems) {
        this.dealRedeems = dealRedeems;
    }

    public String getMerchantHandle() {
        return merchantHandle;
    }

    public void setMerchantHandle(String merchantHandle) {
        this.merchantHandle = merchantHandle;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }


}
