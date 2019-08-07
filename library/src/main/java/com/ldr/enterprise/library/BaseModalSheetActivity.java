package com.ldr.enterprise.library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ldr.enterprise.library.helper.AppHelper;
import com.ldr.enterprise.library.interfaces.SimpleBottomSheetCallback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseModalSheetActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";

    @LayoutRes
    protected abstract int getLayoutRes();

    @BindView(R2.id.ivThumbnail)
    ImageView ivThumbnail;
    @BindView(R2.id.bottomSheet)
    LinearLayout bottomSheet;
    @BindView(R2.id.titleView)
    LinearLayout titleView;
    @BindView(R2.id.contentView)
    LinearLayout contentView;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_modal_sheet);
        ButterKnife.bind(this);

        Picasso.get().load("https://s3-ap-southeast-1.amazonaws.com/smitiv-s3-storage/trail_images/100065.png").into(ivThumbnail);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setHalfExpandedRatio(0.6f);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setExpandedOffset(AppHelper.getActionBarHeight(this));

        bottomSheetBehavior.setBottomSheetCallback(new SimpleBottomSheetCallback() {

            @Override
            public void onStateChanged(int newState) {
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

            @Override
            public void activateParallax(float slideOffset) {
                ivThumbnail.setTranslationY(slideOffset * -100.0f);
                titleView.setTranslationY(slideOffset * -200.0f);
            }
        });

        View view = getLayoutInflater().inflate(getLayoutRes(), null, false);
        contentView.addView(view);

    }
}
