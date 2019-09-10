package com.ldr.enterprise.library.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

public class LibraryHelper {

    private static final String TAG = LibraryHelper.class.getSimpleName();

    public static int getStatusBarHeight(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            WindowInsets windowInsets = context.getWindow().getDecorView().getRootWindowInsets();
//            if (windowInsets != null && windowInsets.getDisplayCutout() != null) {
                Log.d("BAR", "cutouts()");
                int statusBarHeight = 0;
                int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
                Log.d("BAR", "statusBarHeight : " + statusBarHeight);
                return statusBarHeight;
//            } else {
////                Log.d("BAR", "default()");
////                return getStatusBarHeight(context.getBaseContext());
////            }
        } else {
            int height = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25;
            float density = context.getResources().getDisplayMetrics().density;
            Log.d("BAR", "height : " + height);
            Log.d("BAR", "density : " + density);
            int statusBarHeight = Math.round(height * density);
            Log.d("BAR", "statusBarHeight : " + statusBarHeight);
            return statusBarHeight;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int height = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25;
        float density = context.getResources().getDisplayMetrics().density;
        Log.d("BAR", "height : " + height);
        Log.d("BAR", "density : " + density);
        int statusBarHeight = Math.round(height * density);
        Log.d("BAR", "statusBarHeight : " + statusBarHeight);
        return statusBarHeight;
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getDeviceWidthByPercentage(Activity context, int percent) {
        double percentage = percent / 100f;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.widthPixels * percentage);
    }

    public static int getDeviceHeightByPercentage(Activity context, int percent) {
        double percentage = percent / 100f;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.heightPixels * percentage);
    }

    public static float formatDecimal(int decimal, double number) {
        StringBuilder pattern = new StringBuilder("#.");
        for (int i = 0; i < decimal; i++) {
            pattern.append("#");
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return Float.parseFloat(df.format(number));
    }

    public static int dp(Context context, int dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static void updateViewHeight(View v) {
        Activity host = (Activity) v.getContext();
        host.getWindow().getDecorView().setOnApplyWindowInsetsListener((view, windowInsets) -> {
            Log.d(TAG, "onApplyWindowInsets: " + windowInsets.getSystemWindowInsetTop());
            v.setMinimumHeight(windowInsets.getSystemWindowInsetTop());
            v.requestLayout();
            return windowInsets;
        });
    }

}