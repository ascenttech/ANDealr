package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.data.PaymentsData;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    ArrayList<PaymentsData> paymentsData;
    Context context;
    TextView generalText,dealTittle,amount,date,time;
    ImageView paymentStatusImage;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PaymentsAdapter(Context context) {
        this.context = context;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PaymentsAdapter(ArrayList<PaymentsData> paymentsData, Context context) {
        this.paymentsData = paymentsData;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    @Override
    public PaymentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payments_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PaymentsAdapter.ViewHolder holder, int position) {


        findViews(holder);

        setViews(position);



    }

    private void findViews(PaymentsAdapter.ViewHolder holder){

        generalText = (TextView) holder.view.findViewById(R.id.general_text_payments_fragment);
        dealTittle = (TextView) holder.view.findViewById(R.id.deal_tittle_text_payments_fragment);
        amount = (TextView) holder.view.findViewById(R.id.amount_text_payments_fragment);
        date = (TextView) holder.view.findViewById(R.id.date);
        time = (TextView) holder.view.findViewById(R.id.time);
        paymentStatusImage = (ImageView) holder.view.findViewById(R.id.image_status_payment_fragment);

    }

    private void setViews(int position){

        // isHePaid() checks if he paid us a specified amount
        if(Constants.paymentsData.get(position).isHePaid()){

            generalText.setText("Payment of Rs "+context.getString(R.string.Rs)+" "+Constants.paymentsData.get(position).getAmount()+" has \nbeen  added to your account");
            Log.d(Constants.LOG_TAG,"Setting general text" +Constants.paymentsData.get(position).getAmount());

            date.setText(Constants.paymentsData.get(position).getDate());
            Log.d(Constants.LOG_TAG," Setting date text "+Constants.paymentsData.get(position).getDate());

            dealTittle.setVisibility(View.INVISIBLE);
            amount.setVisibility(View.INVISIBLE);
            time.setVisibility(View.INVISIBLE);
            paymentStatusImage.setImageResource(R.drawable.tick);
        }
        else{

            generalText.setText("Invoice # "+Constants.paymentsData.get(position).getOrderID());
            dealTittle.setVisibility(View.VISIBLE);
            dealTittle.setText("Deal Title : "+Constants.paymentsData.get(position).getDealTitle());
            amount.setVisibility(View.VISIBLE);
            amount.setText("Amount : "+context.getString(R.string.Rs)+" "+Constants.paymentsData.get(position).getAmount());
            date.setText(Constants.paymentsData.get(position).getDate());
            time.setVisibility(View.INVISIBLE);
            paymentStatusImage.setImageResource(R.drawable.invoice);

        }


    }

    @Override
    public int getItemCount() {
        return paymentsData.size();
    }

}
