package com.ascentsmartwaves.andealr.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.adapters.PaymentsAdapter;
import com.ascentsmartwaves.andealr.async.FetchPaymentAsyncTask;
import com.ascentsmartwaves.andealr.data.PaymentsData;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class PaymentsFragment extends Fragment {

    private RecyclerView paymentsRecyclerView;
    private RecyclerView.Adapter paymentsAdapter;
    private RecyclerView.LayoutManager paymentsLayoutManager;

    String id;
    ActionBar actionBar;
    ProgressDialog dialog;
    TextView currentBalance,balanceText;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.payment,null);
        currentBalance = (TextView) rootView.findViewById(R.id.current_balance);
        balanceText = (TextView) rootView.findViewById(R.id.balance_static_text);

        paymentsRecyclerView = (RecyclerView) rootView.findViewById(R.id.payments_recycler_view);
        Constants.paymentsData = new ArrayList<PaymentsData>();

        paymentsRecyclerView.setHasFixedSize(true);

        paymentsLayoutManager = new LinearLayoutManager(getActivity());
        paymentsRecyclerView.setLayoutManager(paymentsLayoutManager);


        new FetchPaymentAsyncTask(getActivity().getApplicationContext(),new FetchPaymentAsyncTask.FetchPaymentCallback() {
            @Override
            public void onStart(boolean a) {

                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Getting Your Payment Details");
                dialog.setMessage("Loading... please wait");
                dialog.show();
                dialog.setCancelable(false);

            }
            @Override
            public void onResult(boolean b) {

                if(b){
                    dialog.dismiss();

                    balanceText.setText("Balance");
                    currentBalance.setText(getActivity().getApplicationContext().getString(R.string.Rs)+" "+Constants.currentBalance);
                    paymentsAdapter = new PaymentsAdapter(Constants.paymentsData, getActivity().getApplicationContext());
                    paymentsRecyclerView.setAdapter(paymentsAdapter);

                }
                else{
                    dialog.dismiss();
                    AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                    builder.setTitle("Payment Details");
                    builder.setMessage("No Payment Records");
                    builder.show();

                }

            }
        }).execute(Constants.fetchPaymentURL+Constants.merchantId);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constants.paymentsData = new ArrayList<PaymentsData>();
    }

}
