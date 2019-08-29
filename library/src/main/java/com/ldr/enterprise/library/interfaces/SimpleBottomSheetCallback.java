package com.ldr.enterprise.library.interfaces;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public abstract class SimpleBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

    private static final String TAG = "BottomSheetCallback";

    public void onStateChanged(@BottomSheetBehavior.State int newState) {
        if (newState == BottomSheetBehavior.STATE_SETTLING) {
            Log.d(TAG, "STATE_SETTLING");
        }
        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
            Log.d(TAG, "STATE_DRAGGING");
        }
        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
            Log.d(TAG, "STATE_COLLAPSED");
        }
        if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
            Log.d(TAG, "STATE_HALF_EXPANDED");
        }
        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
            Log.d(TAG, "STATE_EXPANDED");
        }
    }

    public void activateParallax(float slideOffset){

    }

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
        onStateChanged(newState);
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        activateParallax(slideOffset);
    }
}
