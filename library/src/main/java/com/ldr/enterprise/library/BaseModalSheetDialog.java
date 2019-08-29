package com.ldr.enterprise.library;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BaseModalSheetDialog extends BottomSheetDialog {



    public BaseModalSheetDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public BaseModalSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
        init();
    }

    protected BaseModalSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {

    }

}
