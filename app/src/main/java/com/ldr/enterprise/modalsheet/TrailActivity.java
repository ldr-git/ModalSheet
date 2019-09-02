package com.ldr.enterprise.modalsheet;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ldr.enterprise.library.BaseModalSheetActivity;
import com.ldr.enterprise.library.helper.AppHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailActivity extends BaseModalSheetActivity {

    @Nullable
    @BindView(R.id.imageview_thumbnail)
    ImageView ivThumbnail;
    @Nullable
    @BindView(R.id.textview_label_section)
    TextView textViewLabelSection;
    @Nullable
    @BindView(R.id.textview_label_title)
    TextView textViewLabelTitle;

    @NonNull
    @Override
    protected int getBackgroundLayoutRes() {
        return R.layout.activity_trail_background_layout;
    }

    @NonNull
    @Override
    protected int getForegroundLayoutRes() {
        return R.layout.activity_trail_foreground_layout;
    }

    @Override
    protected void onViewAttached() {
        ButterKnife.bind(this);

        try {
            Picasso.get().load("https://s3-ap-southeast-1.amazonaws.com/smitiv-s3-storage/trail_images/100065.png").into(ivThumbnail);
            textViewLabelTitle.setText("Kampong Glam - Walk To The Past");
            textViewLabelSection.setText("Cultural");

//            setModalTitle("Kampong Glam - Walk To The Past");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setExpandedOffset(AppHelper.getStatusBarHeight(this));

        AppHelper.getStatusBarHeight(this);

        setOnCloseClickedListener(view -> {
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
