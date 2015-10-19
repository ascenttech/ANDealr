package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 23-12-2014.
 */
public class AllDealsData {

    String dealId,dealTittle,city,photoURL,dealDescription,likes,redeem,dealStart,dealEnd,reach,encashed;

    public AllDealsData(String dealId, String dealTittle, String city, String likes, String redeem, String photoURL, String reach, String encashed) {
        this.dealId = dealId;
        this.dealTittle = dealTittle;
        this.city = city;
        this.likes = likes;
        this.redeem = redeem;
        this.photoURL = photoURL;
        this.reach = reach;
        this.encashed = encashed;
    }

    public String getEncashed() {
        return encashed;
    }

    public void setEncashed(String encashed) {
        this.encashed = encashed;
    }

    public String getReach() {
        return reach;
    }

    public void setReach(String reach) {
        this.reach = reach;
    }

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
