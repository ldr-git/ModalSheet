package com.ldr.enterprise.modalsheet;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ldr.enterprise.library.BaseModalSheetFragmentDialog;

public class SendGiftDialog extends BaseModalSheetFragmentDialog {

    public SendGiftDialog() {
    }

    public static SendGiftDialog newInstance() {
        return new SendGiftDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHideable(true);
        setHalfExpandedEnabled();
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @NonNull
    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_send_gift_info;
    }

    @Override
    protected void onViewAttached(View view) {
        Toast.makeText(getContext(), "Showing Modal", Toast.LENGTH_LONG).show();
    }
}
