package com.ascent.andealr.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascent.andealr.R;
import com.ascent.andealr.utils.Constants;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class SendFeedbackFragment extends Fragment {
    ActionBar actionBar;
    TextView emailAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.send_feedback,null);
        emailAddress = (TextView) rootView.findViewById(R.id.static_text_send_feedback_activity);
        emailAddress.setTypeface(Constants.customFont);
        return  rootView;
    }
}
