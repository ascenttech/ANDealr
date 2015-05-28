package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class PaymentsData {

    String orderID,dealTitle,amount,date,time;
    boolean hePaid;

    public PaymentsData(String orderID, String dealTitle, String amount, String date, String time, boolean hePaid) {
        this.orderID = orderID;
        this.dealTitle = dealTitle;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.hePaid = hePaid;
    }

    public boolean isHePaid() {
        return hePaid;
    }

    public void setHePaid(boolean hePaid) {
        this.hePaid = hePaid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
