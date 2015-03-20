package com.ascentsmartwaves.andealr.custom;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by ADMIN on 09-02-2015.
 */
public class CustomButton extends Button {
    public CustomButton(Context context) {
        super(context);
        changeButton();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeButton();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        changeButton();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        changeButton();
    }

    private void changeButton() {

        Typeface t = Typeface.createFromAsset(getContext().getAssets(),"ropa.otf");
        setTypeface(t);

    }


}