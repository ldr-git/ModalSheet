package com.ldr.enterprise.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ldr.enterprise.library.helper.AppHelper;
import com.ldr.enterprise.library.interfaces.SimpleBottomSheetCallback;

public abstract class BaseModalSheetFragmentDialog extends BottomSheetDialogFragment {

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @NonNull
    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onViewAttached();

    protected void onStateChangedObserver(@BottomSheetBehavior.State int newState) {
        //This space is for rent
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

    private FrameLayout indicatorView;
    private LinearLayout bottomSheet;
    private NestedScrollView parentScrollView;
    private LinearLayout contentView;
    private BottomSheetBehavior sheetBehavior;
    private int peekHeight = 0;
    private boolean halfExpandedEnabled = false;
    private boolean hideable = true;
    private boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;

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


        indicatorView = view.findViewById(R.id.indicatorView);
        bottomSheet = view.findViewById(R.id.bottomSheet);
        parentScrollView = view.findViewById(R.id.parentScrollView);
        contentView = view.findViewById(R.id.contentView);

        View foregroundView = getLayoutInflater().inflate(getLayoutRes(), null, false);
        contentView.addView(foregroundView);

        parentScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (scrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            indicatorView.setElevation(scrollView.canScrollVertically(-1) ? 10 : 0);
        });

        onViewAttached();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        FrameLayout bottomSheetView = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        if (sheetBehavior != null) {
            Log.d("SHEET", "BEHAVIOR FOUND");
            if (halfExpandedEnabled) {
                sheetBehavior.setHalfExpandedRatio(0.6f);
                sheetBehavior.setFitToContents(false);
            }
            sheetBehavior.setHideable(hideable);
            sheetBehavior.setExpandedOffset(AppHelper.getStatusBarHeight(getActivity()));
            sheetBehavior.setPeekHeight(peekHeight != 0 ? peekHeight : AppHelper.dp(getActivity(), 200));
            sheetBehavior.setBottomSheetCallback(new SimpleBottomSheetCallback() {
                @Override
                public void onStateChanged(int newState) {
                    super.onStateChanged(newState);
                    onStateChangedObserver(newState);
                }
            });
        } else {
            Log.d("SHEET", "BEHAVIOR NOT FOUND");
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