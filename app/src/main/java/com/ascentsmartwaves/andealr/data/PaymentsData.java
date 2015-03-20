package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class PaymentsData {


    String dealName,success,timestamp;
    String amount,orderNumber;



    public PaymentsData(String orderNumber, String dealName, String amount,String success,String timestamp) {
        this.orderNumber = orderNumber;
        this.dealName = dealName;
        this.amount = amount;
        this.success = success;
        this.timestamp = timestamp;
    }



    public String getDealName() {
        return dealName;
    }

    public String getSuccess() {
        return success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }


}
