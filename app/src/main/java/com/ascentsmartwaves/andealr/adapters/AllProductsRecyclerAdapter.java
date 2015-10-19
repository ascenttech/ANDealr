package com.ascentsmartwaves.andealr.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.activities.DealDetailsActivity;
import com.ascentsmartwaves.andealr.custom.CustomTextView;
import com.ascentsmartwaves.andealr.data.AllProductsData;
import com.ascentsmartwaves.andealr.imagecaching.ImageLoader;
import com.ascentsmartwaves.andealr.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 23-12-2014.
 */
public class AllProductsRecyclerAdapter extends RecyclerView.Adapter<AllProductsRecyclerAdapter.ViewHolder> {


    ArrayList<AllProductsData> allProductsData;
    Context context;
    ImageLoader imageLoader;

    private ImageView productImage;
    private CustomTextView productName;

    public AllProductsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public AllProductsRecyclerAdapter(ArrayList<AllProductsData> allProductsData, Context context) {

        Log.d(Constants.LOG_TAG, Constants.AllProductsRecyclerAdapter);
        this.allProductsData = allProductsData;
        this.context = context;
        imageLoader = new ImageLoader(context.getApplicationContext());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }



    // Create new views (invoked by the layout manager)
    @Override
    public AllProductsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_all_products, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        findViews(holder);
        setViews(position);

    }

    public void findViews(ViewHolder holder){


        productImage = (ImageView) holder.view.findViewById(R.id.product_image_all_products_landing_fragment);
        productName = (CustomTextView) holder.view.findViewById(R.id.product_text_all_products_landing_fragment);

    }
    public void setViews(int position){


//        productName.setText(Constants.allProductsData.get(position).getProductName());
//        imageLoader.DisplayImage(Constants.allProductsData.get(position).getProductImageUrl(), productImage);
          productImage.setTag("product_"+position);
          productImage.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return 5;
//        return allProductsData.size();
    }

    public void showDialog(){

        Toast.makeText(context," Calling show dialog ",5000).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Upload Product Image?")
                .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        openGallery();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void openGallery(){


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String tagDetails[] = v.getTag().toString().split("_");
            Toast.makeText(context," Position is "+tagDetails[1],5000).show();
//            showDialog();

        }
    };

}
