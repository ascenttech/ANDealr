package com.ascentsmartwaves.andealr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    TextView dealName,success,timestamp,amount,orderNumber;

    RelativeLayout paymentsListItem;
    Button repeat;
//    final int pos;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PaymentsAdapter(ArrayList<PaymentsData> paymentsData, Context context) {
        this.paymentsData = paymentsData;
        this.context = context;
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

        paymentsListItem = (RelativeLayout) holder.view.findViewById(R.id.payment_list_item_relative_layout_payments_activity);
        dealName=(TextView) holder.view.findViewById(R.id.deal_name_text_payments_activity);
        success=(TextView) holder.view.findViewById(R.id.deal_status_text_payments_activity);
        timestamp=(TextView) holder.view.findViewById(R.id.date_when_order_was_placed_text_payments_activity);
        amount=(TextView) holder.view.findViewById(R.id.deal_amount_text_payments_activity);
        orderNumber=(TextView) holder.view.findViewById(R.id.order_number_text_payments_activity);
//        repeat.setTag("repeat_"+position);
//        repeat.setOnClickListener(listener);
//        pos = position;
//        paymentsListItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"Postions"+pos,5000 ).show();
//            }
//        });
        dealName.setText(paymentsData.get(position).getDealName());
        dealName.setTypeface(Constants.customFont1);
        if(success.equals(1)) {
            success.setText("SUCCESS");
        }
        else
        {
            success.setText("PENDING");
        }

        success.setTypeface(Constants.customFont1);
        timestamp.setText(paymentsData.get(position).getTimestamp());
        timestamp.setTypeface(Constants.customFont1);
        amount.setText(paymentsData.get(position).getAmount());
        amount.setTypeface(Constants.customFont1);
        orderNumber.setText(paymentsData.get(position).getOrderNumber());
        orderNumber.setTypeface(Constants.customFont1);
    }

    @Override
    public int getItemCount() {
        return paymentsData.size();
    }

//    View.OnClickListener listener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            String tag = (String) v.getTag();
//            String identifier[] = tag.split("_");
//
//            switch (v.getId()){
//
//                case R.id.repeat_button_payments_activity :
//                    Intent i = new Intent(context, AddDealActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(i);
//                    break;
//
//            }
//
//        }
//    };

}
