package com.ascentsmartwaves.andealr.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ADMIN on 16-02-2015.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        customText();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        customText();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customText();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        customText();
    }

    private void customText() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"ropa.otf");
        setTypeface(tf);
    }


}
