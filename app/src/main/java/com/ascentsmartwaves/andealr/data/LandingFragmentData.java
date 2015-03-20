package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 23-12-2014.
 */
public class LandingFragmentData {

    String dealId;
    String dealTittle;
    String city;
    String photoURL;
    String dealDescription;
    String likes;
    String redeem;
    String dealStart;

    public String getDealEnd() {
        return dealEnd;
    }

    public void setDealEnd(String dealEnd) {
        this.dealEnd = dealEnd;
    }

    public String getDealStart() {
        return dealStart;
    }

    public void setDealStart(String dealStart) {
        this.dealStart = dealStart;
    }

    String dealEnd;

    public LandingFragmentData(String dealId, String dealTittle, String city, String likes, String redeem, String photoURL) {
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

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getRedeem() {
        return redeem;
    }

    public void setRedeem(String redeem) {
        this.redeem = redeem;
    }
}