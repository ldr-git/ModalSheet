package com.ldr.enterprise.modalsheet;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ldr.enterprise.library.BaseModalSheetActivity;
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
    @Nullable
    @BindView(R.id.titleView)
    LinearLayout titleView;

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
