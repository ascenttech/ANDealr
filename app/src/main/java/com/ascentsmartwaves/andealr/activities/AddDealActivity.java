package com.ascentsmartwaves.andealr.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ascentsmartwaves.andealr.R;
import com.ascentsmartwaves.andealr.async.AddDealAsyncTask;
import com.ascentsmartwaves.andealr.utils.Constants;
import com.ascentsmartwaves.andealr.utils.UploadImageToServer;

import java.io.File;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddDealActivity extends ActionBarActivity {

    Button addDeal;
    EditText name,description;
    Button startDate,endDate,startTime,endTime;
    Calendar calendar;
    Date mindate,curdate;
    int curyear,curday,curmonth;
    private int startMonth,startDay,startYear,startHour,startMinutes,endMonth,endDay,endYear,endHour,endMinutes;
    private int month,day,year,hour,minutes;
    TextView startDateText,endDateText,startTimeText,endTimeText;
    static final int START_DATE_DIALOG_ID = 999;
    static final int END_DATE_DIALOG_ID = 900;
    static final int START_TIME_DIALOG_ID = 800;
    static final int END_TIME_DIALOG_ID = 700;
    Spinner reach;
    ActionBar actionBar;
    ImageView uploadImage;
    RelativeLayout uploadOrCancel;
    ImageButton cancelButton;
    String picturePath;
    ProgressDialog dialog;
    String encodedURL;

    // LogCat tag
    private static final String TAG = AddDealActivity.class.getSimpleName();

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static Uri fileUri; // file url to store image/video
    public static String imagepath="NO DATA"; // file url to store image/video
    String finalURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setTitle("Add a Deal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.add_deal);

        initialize();

//        ArrayAdapter<CharSequence> reachAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.reach_options,R.layout.spinner_item);
//        reachAdapter.setDropDownViewResource(R.layout.spinner_item);
//        reach.setAdapter(reachAdapter);

        setCurrentDateAndTime();
        startDate.setOnClickListener(listener);
        addDeal.setOnClickListener(listener);
        endDate.setOnClickListener(listener);
        startTime.setOnClickListener(listener);
        endTime.setOnClickListener(listener);

        cancelButton.setOnClickListener(listener);
        uploadImage.setOnClickListener(listener);

    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Constants.landingFragmentData.clear();
                Intent i=new Intent(getApplicationContext(),LandingActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {

        addDeal = (Button) findViewById(R.id.add_deal_add_dealBtn);
        name = (EditText) findViewById(R.id.deal_name_add_dealET);
        description = (EditText) findViewById(R.id.deal_description_add_dealET);
        startDate = (Button) findViewById(R.id.start_date_add_dealBtn);
        startDateText = (TextView) findViewById(R.id.start_date_add_dealTV);
        endDateText = (TextView) findViewById(R.id.end_date_add_dealTV);
        endDate = (Button) findViewById(R.id.end_date_add_dealBtn);
//        reach = (Spinner) findViewById(R.id.reach_deal_add_dealS);
        startTime = (Button) findViewById(R.id.start_time_add_dealBtn);
        startTimeText = (TextView) findViewById(R.id.start_time_add_dealTV);
        endTime = (Button) findViewById(R.id.end_time_add_dealBtn);
        endTimeText = (TextView) findViewById(R.id.end_time_add_dealTV);
        uploadImage = (ImageView) findViewById(R.id.upload_image_add_deal_activity);

        uploadOrCancel = (RelativeLayout) findViewById(R.id.upload_or_cancel_included);

        cancelButton = (ImageButton) uploadOrCancel.findViewById(R.id.cancel_button_included);
        uploadOrCancel.setVisibility(View.INVISIBLE);

    }


    private void setCurrentDateAndTime() {

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        curyear = calendar.get(Calendar.YEAR);
        curmonth = calendar.get(Calendar.MONTH)+1;
        curday = calendar.get(Calendar.DAY_OF_MONTH);
        mindate=new Date(curday,curmonth,curyear);

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        startYear = endYear = year;
        startMonth = endMonth = month;
        startDay = endDay = day;
        startHour = endHour = hour;
        startMinutes = endMinutes = minutes;

//            startDateText.setText(new StringBuilder().append(month + 1)
//                .append("-").append(day).append("-").append(year)
//                .append(" "));
//            startDatePicker.init(year,month,day,null);
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG_ID: return new DatePickerDialog(this, startDatePickerListener,startYear, startMonth,startDay);
            case END_DATE_DIALOG_ID : return new DatePickerDialog(this,endDatePickerListener,endYear,endMonth,endDay);
            case START_TIME_DIALOG_ID : return new TimePickerDialog(this,startTimePickerListener,startHour,startMinutes,false);
            case END_TIME_DIALOG_ID : return new TimePickerDialog(this,endTimePickerListener,startHour,startMinutes,false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener startTimePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

           @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            startHour = hourOfDay;
            startMinutes = minute;
            startTimeText.setText(new StringBuilder().append("Start Time : ").append(startHour)
                    .append(":")
                    .append(startMinutes));
        }
    };

    private TimePickerDialog.OnTimeSetListener endTimePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            endHour = hourOfDay;
            endMinutes = minute;

            endTimeText.setText(new StringBuilder().append("End Time : ").append(endHour)
                    .append(":")
                    .append(endMinutes));
        }
    };

    private DatePickerDialog.OnDateSetListener startDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {



        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            startYear = selectedYear;
            startMonth = selectedMonth+1;
            startDay = selectedDay;

            curdate=new Date(startDay,startMonth,startYear);

            if(curdate.getTime()<mindate.getTime())
            {
                AlertDialog builder = new AlertDialog.Builder(AddDealActivity.this).create();
                builder.setTitle("Add Deal");
                builder.setMessage("Start date for the deal cannot be less than current date");
                builder.show();
                startDateText.setText(new StringBuilder().append("Start Date : ").append(curday)
                        .append("-")
                        .append(curmonth)
                        .append("-")
                        .append(curyear)
                        .append(" "));
                startYear = curyear;
                startMonth = curmonth;
                startDay = curday;
            }
            else
            {
                // set selected date into textview
                startDateText.setText(new StringBuilder().append("Start Date : ").append(startDay)
                        .append("-")
                        .append(startMonth)
                        .append("-")
                        .append(startYear)
                        .append(" "));

            }

        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            endYear = selectedYear;
            endMonth = selectedMonth+1;
            endDay = selectedDay;


            // set selected date into textview
            endDateText.setText(new StringBuilder().append("End Date : ").append(endDay)
                    .append("-")
                    .append(endMonth)
                    .append("-")
                    .append(endYear)
                    .append(" "));

        }
    };


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.start_date_add_dealBtn:showDialog(START_DATE_DIALOG_ID);
                    startDateText.setVisibility(View.VISIBLE);
                    break;
                case R.id.start_time_add_dealBtn : showDialog(START_TIME_DIALOG_ID);
                    startTimeText.setVisibility(View.VISIBLE);
                    break;
                case R.id.end_date_add_dealBtn : showDialog(END_DATE_DIALOG_ID);
                    endDateText.setVisibility(View.VISIBLE);
                    break;
                case R.id.end_time_add_dealBtn : showDialog(END_TIME_DIALOG_ID);
                    endTimeText.setVisibility(View.VISIBLE);
                    break;
                case R.id.add_deal_add_dealBtn :
                    try {
                        validateDateAndSendData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.cancel_button_included:
                case R.id.upload_image_add_deal_activity:

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDealActivity.this);
                    builder.setTitle(R.string.upload_photo_via)
                            .setItems(R.array.modes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item

                                    switch (which){

//                                        case 0 : captureImage();
//                                            break;
                                        case 0 : fromGallery();
                                            break;
                                    }

                                }
                            });
                    builder.show();
                    break;
            }
        }
    };

    private void validateDateAndSendData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currentdatetime=curmonth+"/"+curday+"/"+curyear+" "+hour+":"+minutes+":"+00;
        String dealStart=startMonth+"/"+startDay+"/"+startYear+" "+startHour+":"+startMinutes+":"+00;
        String dealEnd=endMonth+"/"+endDay+"/"+endYear+" "+endHour+":"+endMinutes+":"+00;


        Date currentDate  = format.parse(currentdatetime);
        Date startDate  = format.parse(dealStart);
        Date endDate = format.parse(dealEnd);

        long diffInDeal = endDate.getTime() - startDate.getTime();
        long diffInCurrent = startDate.getTime() - currentDate.getTime();

        long currentdiffHours = diffInCurrent / (60 * 60 * 1000) % 24;
        long diffHours = diffInDeal / (60 * 60 * 1000) % 24;
        long diffDays = diffInDeal / (24 * 60 * 60 * 1000);

        Log.d(Constants.LOG_TAG,"\nCURRENT TIME:"+currentdatetime+"\nSTART TIME:"+dealStart);
        Log.d(Constants.LOG_TAG,"\nDIFFERENCE IN DEAL TIME:"+diffInDeal+"\nDIFFERENCE IN CURRENT TIME:"+diffInCurrent);



        if(!(name.getText().toString().equalsIgnoreCase("")))
        {
            if(!(description.getText().toString().equalsIgnoreCase("")))
            {
               if(diffInCurrent>=7200000)
               {
                   if(diffInDeal>7200000)
                   {
                       if(!imagepath.equals("NO DATA"))
                       {
                           uploadData();
                       }
                       else
                       {
                           Toast.makeText(getApplicationContext(),"Please upload a image",5000).show();
                       }
                   }
                   else
                   {
                       if (diffInDeal < 0)
                       {
                           Toast.makeText(getApplicationContext(),"End time should not be less than start time",5000).show();
                       }
                       else
                       {
                       Toast.makeText(getApplicationContext(), "There should be a difference of 2 hours between start time and end time", 5000).show();
                       }
                   }
               }
                else
               {
                   Toast.makeText(getApplicationContext(),"There should be a minimum difference of 2 hours between start time and current time",5000).show();
               }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Deal Description cannot be empty",5000).show();
            }

       }
       else{
           Toast.makeText(getApplicationContext(),"Deal Name cannot be empty",5000).show();
       }




    }

    private void uploadData(){

        new UploadImageToServer(getApplicationContext(),new UploadImageToServer.UploadImageToServerCallback() {
            @Override
            public void onStart(boolean a)
            {
                dialog = new ProgressDialog(AddDealActivity.this);
                dialog.setMessage("Loading... please wait");
                dialog.setTitle("Adding your image");
                dialog.show();
                dialog.setCancelable(false);
            }
            @Override
            public void onResult(String b)
            {
                dialog.dismiss();
                try
                {
                    finalURL = Constants.addDealUrl + Constants.merchantId
                            + "&dealTitle="+  URLEncoder.encode(name.getText().toString(), "UTF-8")
                            +"&dealDescription="+   URLEncoder.encode(description.getText().toString(),"UTF-8")
                            + "&companyName=poiy3"
                            + "&startDate=" + startDay +"-"+startMonth+"-"+startYear
                            + "&startTime=" + startHour +":"+startMinutes+":"+"00"
                            + "&endDate=" + endDay +"-"+endMonth+"-"+endYear
                            + "&endTime=" + endHour +":"+endMinutes+":"+"00"
                            + "&photoID="+ Constants.photoIdofAddedDeal
                            + "&categoryName=all";
                    Log.d(Constants.LOG_TAG,finalURL);
                }
                catch(Exception e)
                {

                }
                new AddDealAsyncTask(getApplicationContext(),new AddDealAsyncTask.AddDealAsyncTaskCallback() {
                    @Override
                    public void onStart(boolean a) {

                        dialog = new ProgressDialog(AddDealActivity.this);
                        dialog.setMessage("Loading... please wait");
                        dialog.setTitle("Adding your Deal");
                        dialog.show();
                        dialog.setCancelable(false);

                    }
                    @Override
                    public void onResult(boolean b) {

                        dialog.dismiss();
                        imagepath="NO DATA";
                        Constants.landingFragmentData.clear();
                        Intent i = new Intent(AddDealActivity.this,LandingActivity.class);
                        startActivity(i);


                    }
                }).execute(finalURL);

            }
        }).execute();

    }


    private void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    // For picking up iamge from gallery

    public void fromGallery()
    {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 0);
    }



    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + Constants.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else {
            return null;
        }

        return mediaFile;
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK) {

                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

                uploadImage.setImageBitmap(bitmap);
                uploadOrCancel.setVisibility(View.VISIBLE);

//                imagePreview.setOnClickListener(listener);



            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        }
        else if (requestCode == 0 && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            imagepath=picturePath;
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            uploadImage.setImageBitmap(bitmap);
            uploadOrCancel.setVisibility(View.VISIBLE);

        }

    }

}
