package com.ascentsmartwaves.andealr.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.ascentsmartwaves.andealr.R;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class RewardsFragment extends Fragment {

    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.rewards,null);

        rootView.setFocusableInTouchMode(true);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                    //Intent i = new Intent(rootView.getContext(), LandingActivity.class);
                   // startActivity(i);
                }
                return false;
            }
        });
        return rootView;
    }


}
