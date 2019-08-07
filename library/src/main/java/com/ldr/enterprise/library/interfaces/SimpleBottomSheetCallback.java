package com.ldr.enterprise.library.interfaces;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public abstract class SimpleBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

    public abstract void onStateChanged(int newState);

    public abstract void activateParallax(float slideOffset);

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
        onStateChanged(newState);
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        activateParallax(slideOffset);
    }
}
