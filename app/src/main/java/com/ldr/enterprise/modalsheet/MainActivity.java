package com.ldr.enterprise.modalsheet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.ldr.enterprise.library.BaseModalSheetActivity;
import com.ldr.enterprise.modalsheet.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.buttonShowModal)
    Button buttonShowModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        buttonShowModal.setOnClickListener(view -> {
            Toast.makeText(this, "Show Modal", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, TrailActivity.class));
        });


    }
}
