package com.ldr.enterprise.library.ui.components;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.ldr.enterprise.library.R;
import com.ldr.enterprise.library.helper.LibraryHelper;

public class AppStatusBar extends LinearLayout {

    private Context context;
    private int statusBarColor = Integer.MIN_VALUE;

    public AppStatusBar(Context context) {
        super(context);
    }

    public AppStatusBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppStatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public AppStatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.AppStatusBar, 0, 0);
            try {
                //get the text and colors specified using the names in attrs.xml
                statusBarColor = a.getInteger(R.styleable.AppStatusBar_statusBarColor, Integer.MIN_VALUE);//0 is default
            } finally {
                a.recycle();
            }
        }
    }

    public void setStatusBarColor(@ColorInt int color) {
        this.statusBarColor = color;
        requestLayout();
    }

    private Activity getActivity(Context context) {
        if (context == null)
            return null;
        else if (context instanceof Activity)
            return (Activity) context;
        else if (context instanceof ContextWrapper)
            return getActivity(((ContextWrapper) context).getBaseContext());
        return null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Activity host = getActivity(getContext());

        int height = LibraryHelper.getStatusBarHeight(host);
        Log.d("BAR", "onAttachedToWindow: " + height);
        ViewGroup.LayoutParams viewParams = getLayoutParams();
        if (viewParams != null) {
            viewParams.height = height;
        } else {
            viewParams = new LayoutParams(LayoutParams.MATCH_PARENT, height);
        }
        setLayoutParams(viewParams);

        if (statusBarColor == Integer.MIN_VALUE) {
            TypedValue typedValue = new TypedValue();
            host.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            statusBarColor = typedValue.data;
        }

        setBackgroundColor(statusBarColor);

    }

}