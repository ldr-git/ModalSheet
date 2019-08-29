package com.ldr.enterprise.modalsheet;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.ref.WeakReference;

public class SheetDialogHelper {

    private BottomSheetDialog sheetDialog;

    private SheetDialogHelper(Builder builder) {

        sheetDialog = new BottomSheetDialog(builder.context.get());
        sheetDialog.setContentView(builder.customView);
        sheetDialog.setCancelable(builder.cancellable);
        sheetDialog.setCanceledOnTouchOutside(builder.cancellableOnTouchOutside);
        sheetDialog.setOnDismissListener(builder.onDismissListener);
        sheetDialog.setOnCancelListener(builder.onCancelListener);

    }

    public BottomSheetDialog show() {
        if (sheetDialog != null && !sheetDialog.isShowing())
            sheetDialog.show();
        return sheetDialog;
    }

    public static class Builder {
        private WeakReference<Context> context;
        private View customView;
        private boolean cancellable = true;
        private boolean cancellableOnTouchOutside = true;
        private DialogInterface.OnCancelListener onCancelListener;
        private DialogInterface.OnDismissListener onDismissListener;

        public Builder(@NonNull Activity activity) {
            this.context = new WeakReference<>(activity);
        }

        public Builder(@NonNull Fragment fragment) {
            this.context = new WeakReference<>(fragment.getActivity());
        }

        public Builder(@NonNull Context context) {
            this.context = new WeakReference<>(context);
        }

        public Builder setContentView(@NonNull View view) {
            this.customView = view;
            return this;
        }

        public Builder setCancellable(boolean cancellable) {
            this.cancellable = cancellable;
            return this;
        }

        public Builder setCancellableOnTouchOutside(boolean cancellableOnTouchOutside) {
            this.cancellableOnTouchOutside = cancellableOnTouchOutside;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public SheetDialogHelper build() {
            return new SheetDialogHelper(this);
        }

    }

}