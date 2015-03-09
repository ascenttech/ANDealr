package com.ascent.andealr.data;

/**
 * Created by ADMIN on 06-01-2015.
 */
public class NotificationsData {

    String dealId,dealTittle,startDate,startTime,endDate,endTime,dealStatus;
    int likesCounter,redeemCounter;

    public NotificationsData(String dealId, String dealTittle, String startDate, String startTime, String endDate, String endTime, String dealStatus, int likesCounter, int redeemCounter) {
        this.dealId = dealId;
        this.dealTittle = dealTittle;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.dealStatus = dealStatus;
        this.likesCounter = likesCounter;
        this.redeemCounter = redeemCounter;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    public int getRedeemCounter() {
        return redeemCounter;
    }

    public void setRedeemCounter(int redeemCounter) {
        this.redeemCounter = redeemCounter;
    }
}
