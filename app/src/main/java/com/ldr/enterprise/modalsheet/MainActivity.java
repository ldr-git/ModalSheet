package com.ldr.enterprise.modalsheet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.ldr.enterprise.modalsheet.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @OnClick(R.id.buttonShowModal)
    void onShowModalClicked() {
        Toast.makeText(this, "Show Modal", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, TrailActivity.class));
    }

    @OnClick(R.id.buttonShowModalDialogFragment)
    void onShowModalDialogFragmentClicked() {
        BrandInfoDialog.newInstance().show(this);
    }

    @OnClick(R.id.buttonShowModalDialog)
    void onShowModalDialogClicked() {
        View sheetView = getLayoutInflater().inflate(R.layout.dialog_brand_info, null, false);
        new SheetDialogHelper.Builder(this)
                .setCancellable(false)
                .setCancellableOnTouchOutside(false)
                .setContentView(sheetView)
                .setOnCancelListener(dialog -> {
                })
                .setOnDismissListener(dialog -> {
                })
                .build()
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
