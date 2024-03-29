package com.ascentsmartwaves.andealr.utils;



        import android.app.DatePickerDialog;
        import android.app.DatePickerDialog.OnDateSetListener;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;

public class DatePickerUtil extends DialogFragment {
    OnDateSetListener ondateSet;

    public DatePickerUtil() {
    }

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }
}