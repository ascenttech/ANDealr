package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 27-05-2015.
 */
public class NotificationsData {

    int notificationId,merchantId;
    String message,date,time;

    public NotificationsData(int notificationId, int merchantId, String message, String date, String time) {
        this.notificationId = notificationId;
        this.merchantId = merchantId;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
