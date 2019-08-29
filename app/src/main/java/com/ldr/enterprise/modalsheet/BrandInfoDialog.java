package com.ldr.enterprise.modalsheet;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ldr.enterprise.library.BaseModalSheetFragmentDialog;

public class BrandInfoDialog extends BaseModalSheetFragmentDialog {

    public BrandInfoDialog() {
    }

    public static BrandInfoDialog newInstance() {
        return new BrandInfoDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHideable(false);
        setHalfExpandedEnabled();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_brand_info;
    }

    @Override
    protected void onViewAttached() {
        Toast.makeText(getContext(), "Showing Modal", Toast.LENGTH_LONG).show();
    }
}
