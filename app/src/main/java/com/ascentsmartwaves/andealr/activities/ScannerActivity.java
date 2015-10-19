package com.ascentsmartwaves.andealr.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ascentsmartwaves.andealr.async.CheckValidityAsyncTask;
import com.ascentsmartwaves.andealr.utils.Constants;
import com.google.zxing.Result;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    ProgressDialog dialog;
    String userid,dealid;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        Log.d(Constants.LOG_TAG,Constants.ScannerActivity);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult)
    {
        // Do something with the result here
        Log.v("SCANNER", rawResult.getText()); // Prints scan results
        Log.v("SCANNER", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        String contents = rawResult.getText();
        try
        {
          contents= URLDecoder.decode(contents, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        try
        {
            StringTokenizer tokens = new StringTokenizer(contents, ",");
            userid = tokens.nextToken();
            dealid = tokens.nextToken();
            String finalURL = Constants.checkValidityURL + userid + "&dealID="+dealid+ "&merchantID="+Constants.merchantId;
            Log.d(Constants.LOG_TAG,finalURL);
            new CheckValidityAsyncTask(getApplicationContext(),new CheckValidityAsyncTask.CheckValidityCallback()
            {
                @Override
                public void onStart(boolean a)
                {
                    dialog = new ProgressDialog(ScannerActivity.this);
                    dialog.setTitle("Validating");
                    dialog.setMessage("We are validating the deal");
                    dialog.show();
                    dialog.setCancelable(false);
                }
                @Override
                public void onResult(boolean b)
                {
                    if(b)
                    {
                        dialog.dismiss();
                        if(Constants.dealValidity.equals("noDeal"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                            builder.setMessage("This Deal is Expired").setPositiveButton("Ok", dialogClickListener);
                            builder.show();
                        }
                        if(Constants.dealValidity.equals("noMerchant"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                            builder.setMessage("Deal does not exist").setPositiveButton("Ok", dialogClickListener);
                            builder.show();
                        }
                        if(Constants.dealValidity.equals("dealValid"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                            builder.setMessage("Deal is Valid").setPositiveButton("Ok", dialogClickListener);
                            builder.show();
                        }
                        if(Constants.dealValidity.equals("dealExpired"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                            builder.setMessage("This Deal is Expired").setPositiveButton("Ok", dialogClickListener);
                            builder.show();
                        }
                        if(Constants.dealValidity.equals("dealIncorrect"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                            builder.setMessage("Incorrect Deal Code").setPositiveButton("Ok", dialogClickListener);
                            builder.show();
                        }
                        if(Constants.dealValidity.equals("InvalidQRCode"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                            builder.setMessage("Incorrect QR Code").setPositiveButton("Ok", dialogClickListener);
                            builder.show();
                        }
                    }
                    else
                    {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                        builder.setTitle("ANDealr");
                        builder.setMessage("Cannot Connect to the server");
                        builder.show();
                    }
                }
            }).execute(finalURL);
        }
        catch(NoSuchElementException e)
        {
            userid = "";
            dealid = "";
            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
            builder.setTitle("ANDealr");
            builder.setMessage("Not a Valid QR Code");
            builder.show();
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            Intent i;
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    Constants.allDealsData.clear();
                    i=new Intent(getApplicationContext(),LandingActivity.class);
                    startActivity(i);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    i=new Intent(getApplicationContext(),ScannerActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };
}