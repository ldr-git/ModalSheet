package com.ldr.enterprise.library;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.ldr.enterprise.library.helper.LibraryHelper;
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

    public void setPeekHeight(int peekHeight) {
        this.peekHeight = peekHeight;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setPeekHeight(peekHeight);
        }
    }

    public void setHalfExpandedEnabled(boolean halfExpandedEnabled) {
        this.halfExpandedEnabled = halfExpandedEnabled;
        if (bottomSheetBehavior != null) {
            if (halfExpandedEnabled) {
                bottomSheetBehavior.setHalfExpandedRatio(0.6f);
                bottomSheetBehavior.setFitToContents(false);
            } else {
                bottomSheetBehavior.setFitToContents(true);
            }
        }
    }

    public void setHideable(boolean hideable) {
        this.hideable = hideable;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setHideable(hideable);
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

    private int peekHeight = 0;
    private boolean halfExpandedEnabled = true;
    private boolean hideable = false;

    private int offset = 0;

    ConstraintLayout indicatorView;
    LinearLayout bottomSheet;
    LinearLayout contentView;
    NestedScrollView parentScrollView;
    LinearLayout contentBackground;

    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_modal_sheet);

        indicatorView = findViewById(R.id.modal_sheet_indicator_container);
        bottomSheet = findViewById(R.id.bottomSheet);
        contentView = findViewById(R.id.contentView);
        parentScrollView = findViewById(R.id.parentScrollView);
        contentBackground = findViewById(R.id.contentBackground);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        if (halfExpandedEnabled) {
            bottomSheetBehavior.setHalfExpandedRatio(0.6f);
            bottomSheetBehavior.setFitToContents(false);
        } else {
            bottomSheetBehavior.setFitToContents(true);
        }

        if (peekHeight != 0) {
            bottomSheetBehavior.setPeekHeight(peekHeight);
        }

        bottomSheetBehavior.setHideable(hideable);

        bottomSheetBehavior.setExpandedOffset(offset != 0 ? offset : LibraryHelper.getStatusBarHeight(this));
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

        parentScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (scrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            indicatorView.setElevation(scrollView.canScrollVertically(-1) ? 10 : 0);
        });

        View foregroundView = getLayoutInflater().inflate(getForegroundLayoutRes(), null, false);
        contentView.addView(foregroundView);

        View backgroundView = getLayoutInflater().inflate(getBackgroundLayoutRes(), null, false);
        contentBackground.addView(backgroundView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        onViewAttached();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setExpandedOffset(offset != 0 ? offset : LibraryHelper.getStatusBarHeight(this));
        }
    }
}
