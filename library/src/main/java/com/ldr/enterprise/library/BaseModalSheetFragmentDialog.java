package com.ldr.enterprise.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ldr.enterprise.library.helper.LibraryHelper;

public abstract class BaseModalSheetFragmentDialog extends BottomSheetDialogFragment {

    private static final String TAG = BaseModalSheetFragmentDialog.class.getSimpleName();

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @NonNull
    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onViewAttached(View view);

    protected void onStateChangedObserver(@BottomSheetBehavior.State int newState) {
        //This space is for rent
    }

    protected void setOnCloseClickedListener(View.OnClickListener onClickListener) {
        closeView.setVisibility(View.VISIBLE);
        closeView.setOnClickListener(onClickListener);
    }

    protected void setModalTitle(CharSequence charSequence) {
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(charSequence);
    }

    protected void setBottomSheetBehaviorState(@BottomSheetBehavior.State int newState) {
        if (sheetBehavior != null) {
            sheetBehavior.setState(newState);
        }
    }

    protected void setPeekHeight(int peekHeight) {
        this.peekHeight = peekHeight;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    protected void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    protected void setHalfExpandedEnabled() {
        halfExpandedEnabled = true;
    }

    protected void setHideable(boolean hideable) {
        this.hideable = hideable;
    }

    public void setOffsetExpanded(boolean offsetExpanded) {
        isOffsetExpanded = offsetExpanded;
    }

    private ConstraintLayout indicatorView;
    private NestedScrollView parentScrollView;
    private LinearLayout contentView;
    private BottomSheetBehavior sheetBehavior;
    private int peekHeight = 0;
    private boolean halfExpandedEnabled = false;
    private boolean hideable = true;
    private boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;
    private boolean isOffsetExpanded = false;

    private TextView titleView;
    private ImageButton closeView;

    public BaseModalSheetFragmentDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_modal_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indicatorView = view.findViewById(R.id.modal_sheet_indicator_container);
        titleView = view.findViewById(R.id.modal_sheet_title);
        closeView = view.findViewById(R.id.modal_sheet_button_close);
        parentScrollView = view.findViewById(R.id.parentScrollView);
        contentView = view.findViewById(R.id.contentView);

        View foregroundView = getLayoutInflater().inflate(getLayoutRes(), null, false);
        contentView.addView(foregroundView);

        parentScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (scrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            indicatorView.setElevation(scrollView.canScrollVertically(-1) ? 10 : 0);
        });

        onViewAttached(view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            FrameLayout bottomSheetView = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheetView != null) {
                sheetBehavior = BottomSheetBehavior.from(bottomSheetView);
                if (sheetBehavior != null) {
                    Log.d("SHEET", "BEHAVIOR FOUND");
                    if (halfExpandedEnabled) {
                        sheetBehavior.setHalfExpandedRatio(0.6f);
                        sheetBehavior.setFitToContents(false);
                    }
                    sheetBehavior.setHideable(hideable);
                    sheetBehavior.setExpandedOffset(isOffsetExpanded ? LibraryHelper.getStatusBarHeight(getActivity()) : 0);
                    sheetBehavior.setPeekHeight(peekHeight != 0 ? peekHeight : LibraryHelper.dp(getActivity(), 200));
                } else {
                    Log.d("SHEET", "BEHAVIOR NOT FOUND");
                }
            } else {
                Log.d("SHEET", "SHEET NOT FOUND");
            }
        } else {
            Log.d("SHEET", "DIALOG NOT FOUND");
        }
    }

    public void show(Context context) {
        AppCompatActivity activity = (AppCompatActivity) context;
        if (activity != null)
            show(activity.getSupportFragmentManager(), "Modal Sheet");
    }

    public void show(View context) {
        AppCompatActivity activity = (AppCompatActivity) context.getContext();
        if (activity != null)
            show(activity.getSupportFragmentManager(), "Modal Sheet");
    }

    public void show(Fragment context) {
        AppCompatActivity activity = (AppCompatActivity) context.getActivity();
        if (activity != null)
            show(activity.getSupportFragmentManager(), "Modal Sheet");
    }

}
