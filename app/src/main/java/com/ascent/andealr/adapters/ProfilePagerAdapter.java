package com.ascent.andealr.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ascent.andealr.fragments.CompanyProfileFragment;
import com.ascent.andealr.fragments.MessagesFragment;
import com.ascent.andealr.fragments.NotificationsFragment;
import com.ascent.andealr.fragments.UserProfileFragment;

public class ProfilePagerAdapter extends FragmentPagerAdapter {

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new CompanyProfileFragment();
            case 1:
                // Games fragment activity
                return new UserProfileFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}