package com.ascent.andealr.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;

import com.ascent.andealr.R;
import com.ascent.andealr.adapters.NotificationsPagerAdapter;
import com.ascent.andealr.fragments.MessagesFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by ADMIN on 06-01-2015.
 */
public class NotificationsActivity extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private FragmentActivity myContext;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, NotificationsActivity.TabInfo>();
    private NotificationsPagerAdapter mPagerAdapter;
    ImageView settings;
    ActionBar actionBar;
    /**
     *
     * @author mwho
     * Maintains extrinsic info of a tab's construct
     */
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;
        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }
    /**
     * A simple factory that returns dummy views to the Tabhost
     * @author mwho
     */
    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        /** (non-Javadoc)
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }
    /** (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        final View rootView = inflater.inflate(R.layout.notifications,null);


//        settings = (ImageView) rootView.findViewById(R.id.notifications_settings_image_notifications_activity);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(rootView.getContext(),NotificationsSettings.class);
//                startActivity(i);
//            }
//        });

        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState,rootView);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager(rootView);
        return rootView;
    }

    /** (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager(View v) {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(v.getContext(), com.ascent.andealr.fragments.NotificationsFragment.class.getName()));
        fragments.add(Fragment.instantiate(v.getContext(), MessagesFragment.class.getName()));
        this.mPagerAdapter  = new NotificationsPagerAdapter(myContext.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager)v.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(Bundle args,View v) {
        mTabHost = (TabHost)v.findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        NotificationsActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Notifications").setIndicator("Notifications"), (tabInfo = new TabInfo("Tab1", com.ascent.andealr.fragments.NotificationsFragment.class, args)),v);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        NotificationsActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Messages").setIndicator("Messages"), (tabInfo = new TabInfo("Tab2", MessagesFragment.class, args)),v);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);


        // Default to first tab
        //this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    private static void AddTab(NotificationsActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo,View v) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(v.getContext()));
        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }


}
