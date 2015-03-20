package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.DrawerListData;

import java.util.ArrayList;

/**
 * Created by ADMIN on 22-12-2014.
 */
public class CustomDrawerListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<DrawerListData> drawerListData;
    ImageView drawerListItemIcon;
    TextView drawerListItemName;

    public CustomDrawerListAdapter(Context context, ArrayList<DrawerListData> drawerListData) {
        this.context = context;
        this.drawerListData = drawerListData;
    }

    @Override
    public int getCount() {
        return drawerListData.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.drawer_list_item,null);
        drawerListItemIcon = (ImageView) row.findViewById(R.id.drawer_item_image_custom_list_adapter);
        drawerListItemName = (TextView) row.findViewById(R.id.drawer_item_name_custom_list_adapter);

        drawerListItemIcon.setImageResource(drawerListData.get(position).getListItemLogo());
        drawerListItemName.setText(drawerListData.get(position).getListItemName());
        return row;
    }
}
