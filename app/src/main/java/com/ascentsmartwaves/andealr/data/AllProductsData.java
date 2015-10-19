package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 24-09-2015.
 */
public class AllProductsData {

    String productImageUrl;
    String productName;

    public AllProductsData(String productName, String productImageUrl) {
        this.productName = productName;
        this.productImageUrl = productImageUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
