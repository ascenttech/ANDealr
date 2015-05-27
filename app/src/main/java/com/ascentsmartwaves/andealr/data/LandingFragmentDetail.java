package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 03-04-2015.
 */
public class LandingFragmentDetail
{
    String dealId,dealTittle,dealCity,photoURL,dealDescription,likes,redeem,dealStart,dealEnd;
    int reach;

    public LandingFragmentDetail(String dealId, String dealDescription, String likes, String redeem, String dealStart, String dealEnd, String photoURL, String dealTittle, String dealCity, int reach) {
        this.dealId = dealId;
        this.dealDescription = dealDescription;
        this.likes = likes;
        this.redeem = redeem;
        this.dealStart = dealStart;
        this.dealEnd = dealEnd;
        this.photoURL = photoURL;
        this.dealTittle = dealTittle;
        this.dealCity = dealCity;
        this.reach = reach;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
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

    public String getDealCity() {
        return dealCity;
    }

    public void setDealCity(String dealCity) {
        this.dealCity = dealCity;
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

    public String getDealStart() {
        return dealStart;
    }

    public void setDealStart(String dealStart) {
        this.dealStart = dealStart;
    }

    public String getDealEnd() {
        return dealEnd;
    }

    public void setDealEnd(String dealEnd) {
        this.dealEnd = dealEnd;
    }





}