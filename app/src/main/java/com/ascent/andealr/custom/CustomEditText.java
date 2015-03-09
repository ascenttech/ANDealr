package com.ascent.andealr.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by ADMIN on 16-02-2015.
 */
public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        customEdit();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        customEdit();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customEdit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        customEdit();
    }

    private void customEdit() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"ropa.otf");
        setTypeface(tf);

    }


}
