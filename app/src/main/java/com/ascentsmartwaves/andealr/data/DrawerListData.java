package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 22-12-2014.
 */
public class DrawerListData {


    String listItemName;
    int listItemLogo;

    public DrawerListData(String listItemName, int listItemLogo) {
        this.listItemName = listItemName;
        this.listItemLogo = listItemLogo;
    }

    public String getListItemName() {
        return listItemName;
    }

    public void setListItemName(String listItemName) {
        this.listItemName = listItemName;
    }

    public int getListItemLogo() {
        return listItemLogo;
    }

    public void setListItemLogo(int listItemLogo) {
        this.listItemLogo = listItemLogo;
    }
}
