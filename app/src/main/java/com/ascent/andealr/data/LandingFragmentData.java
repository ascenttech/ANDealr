package com.ascent.andealr.data;

/**
 * Created by ADMIN on 23-12-2014.
 */
public class LandingFragmentData {

    String dealId,dealTittle,city,photoURL,dealDescription;
    int likes,redeem;

    public LandingFragmentData(String dealId, String dealTittle, String city, int likes, int redeem, String photoURL) {
        this.dealId = dealId;
        this.dealTittle = dealTittle;
        this.city = city;
        this.likes = likes;
        this.redeem = redeem;
        this.photoURL = photoURL;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDealTittle() {
        return dealTittle;
    }

    public void setDealTittle(String dealTittle) {
        this.dealTittle = dealTittle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRedeem() {
        return redeem;
    }

    public void setRedeem(int redeem) {
        this.redeem = redeem;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    //
//    String cardBackground;
//    String dealTittle;
//    String dealDescription;
//
//    public LandingFragmentData(String cardBackground, String dealTittle, String dealDescription) {
//        this.cardBackground = cardBackground;
//        this.dealTittle = dealTittle;
//        this.dealDescription = dealDescription;
//    }
//
//    public String getCardBackground() {
//        return cardBackground;
//    }
//
//    public String getDealTittle() {
//        return dealTittle;
//    }
//
//    public String getDealDescription() {
//        return dealDescription;
//    }

}
