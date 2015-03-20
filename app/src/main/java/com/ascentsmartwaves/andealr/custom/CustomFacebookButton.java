package com.ascentsmartwaves.andealr.custom;

import android.widget.Button;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;


/**
 * Created by ADMIN on 17-02-2015.
 */
public class CustomFacebookButton extends Button {

    String loginText;

    public CustomFacebookButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs.getStyleAttribute() == 0) {

            this.setGravity(Gravity.CENTER);
            this.setTextColor(getResources().getColor(com.facebook.android.R.color.com_facebook_loginview_text_color));
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(com.facebook.android.R.dimen.com_facebook_loginview_text_size));
            this.setTypeface(Typeface.DEFAULT_BOLD);
            if (isInEditMode()) {
                // cannot use a drawable in edit mode, so setting the backgroundregisterlogin color instead
                // of a backgroundregisterlogin resource.
                this.setBackgroundColor(getResources().getColor(com.facebook.android.R.color.com_facebook_blue));
                // hardcoding in edit mode as getResources().getString() doesn't seem to work in IntelliJ

            } else {
                this.setBackgroundResource(com.facebook.android.R.drawable.com_facebook_button_blue);
                this.setCompoundDrawablesWithIntrinsicBounds(com.facebook.android.R.drawable.com_facebook_inverse_icon, 0, 0, 0);
                this.setCompoundDrawablePadding(
                        getResources().getDimensionPixelSize(com.facebook.android.R.dimen.com_facebook_loginview_compound_drawable_padding));
                this.setPadding(getResources().getDimensionPixelSize(com.facebook.android.R.dimen.com_facebook_loginview_padding_left),
                        getResources().getDimensionPixelSize(com.facebook.android.R.dimen.com_facebook_loginview_padding_top),
                        getResources().getDimensionPixelSize(com.facebook.android.R.dimen.com_facebook_loginview_padding_right),
                        getResources().getDimensionPixelSize(com.facebook.android.R.dimen.com_facebook_loginview_padding_bottom));
            }
        }
    }
}