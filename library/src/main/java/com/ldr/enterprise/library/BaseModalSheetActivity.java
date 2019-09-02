package com.ldr.enterprise.library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ldr.enterprise.library.helper.AppHelper;
import com.ldr.enterprise.library.interfaces.SimpleBottomSheetCallback;

public abstract class BaseModalSheetActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";

    @NonNull
    @LayoutRes
    protected abstract int getBackgroundLayoutRes();

    @NonNull
    @LayoutRes
    protected abstract int getForegroundLayoutRes();

    protected abstract void onViewAttached();

    protected int getPeekHeight() {
        return bottomSheetBehavior.getPeekHeight();
    }

    protected void onStateChangedObserver(@BottomSheetBehavior.State int newState) {
        //This space is for rent
    }

    protected void setExpandedOffset(int offset) {
        this.offset = offset;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setExpandedOffset(offset);
        }
    }

    protected void setOnCloseClickedListener(View.OnClickListener onClickListener) {
        findViewById(R.id.modal_sheet_button_close).setVisibility(View.VISIBLE);
        findViewById(R.id.modal_sheet_button_close).setOnClickListener(onClickListener);
    }

    protected void setModalTitle(CharSequence charSequence) {
        findViewById(R.id.modal_sheet_title).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.modal_sheet_title)).setText(charSequence);
    }

    protected void setBottomSheetBehaviorState(@BottomSheetBehavior.State int newState) {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(newState);
        }
    }

    private int offset = 0;

    LinearLayout bottomSheet;
    LinearLayout contentView;
    LinearLayout contentBackground;

    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_modal_sheet);

        bottomSheet = findViewById(R.id.bottomSheet);
        contentView = findViewById(R.id.contentView);
        contentBackground = findViewById(R.id.contentBackground);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setHalfExpandedRatio(0.6f);
        bottomSheetBehavior.setFitToContents(false);

        getWindow().getDecorView().setOnApplyWindowInsetsListener((view, windowInsets) -> {
            Log.d(TAG, "onApplyWindowInsets: " + windowInsets.getSystemWindowInsetTop());
            bottomSheetBehavior.setExpandedOffset(windowInsets.getSystemWindowInsetTop());
            return windowInsets;
        });

        bottomSheetBehavior.setExpandedOffset(offset != 0 ? offset : AppHelper.getStatusBarHeight(this));
        bottomSheetBehavior.setBottomSheetCallback(new SimpleBottomSheetCallback() {

            @Override
            public void onStateChanged(int newState) {
                super.onStateChanged(newState);
                findViewById(R.id.modal_sheet_indicator_container).setElevation(newState != BottomSheetBehavior.STATE_COLLAPSED ? 10 : 0);
                onStateChangedObserver(newState);
            }

            @Override
            public void activateParallax(float slideOffset) {
                contentBackground.setTranslationY(slideOffset * -100.0f);
            }
        });

        View foregroundView = getLayoutInflater().inflate(getForegroundLayoutRes(), null, false);
        contentView.addView(foregroundView);

        View backgroundView = getLayoutInflater().inflate(getBackgroundLayoutRes(), null, false);
        contentBackground.addView(backgroundView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        onViewAttached();

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setExpandedOffset(AppHelper.getStatusBarHeight(this));
        }
    }
}
