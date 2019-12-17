package com.ldr.enterprise.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.FloatRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ldr.enterprise.library.helper.LibraryHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @NonNull
    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void onViewCreated(View view);

    private BottomSheetListener bottomSheetListener;

    public void setBottomSheetListener(BottomSheetListener bottomSheetListener) {
        this.bottomSheetListener = bottomSheetListener;
    }

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;

    public void setBottomSheetCallback(BottomSheetBehavior.BottomSheetCallback bottomSheetCallback) {
        this.bottomSheetCallback = bottomSheetCallback;
    }

    private Unbinder binder;
    private BottomSheetBehavior sheetBehavior;

    private int peekHeight = 0;
    private int expandedOffset = 0;
    private boolean halfExpandedEnabled = false;
    private boolean isFitToContents = true;
    private boolean isDraggable = true;
    private boolean hideable = true;
    private boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;
    private boolean skipCollapsed = true;
    private boolean fullScreen = false;

    private float dimAmount = 0.6f;//Default dim amount

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public void setPeekHeight(int peekHeight) {
        this.peekHeight = peekHeight;
    }

    public void setExpandedOffset(int expandedOffset) {
        this.expandedOffset = expandedOffset;
    }

    public void setHalfExpandedEnabled(boolean halfExpandedEnabled) {
        this.halfExpandedEnabled = halfExpandedEnabled;
    }

    public void setDimAmount(@FloatRange float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public void setFitToContents(boolean fitToContents) {
        isFitToContents = fitToContents;
    }

    public void setHideable(boolean hideable) {
        this.hideable = hideable;
    }

    public void setDraggable(boolean draggable) {
        isDraggable = draggable;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public void setSkipCollapsed(boolean skipCollapsed) {
        this.skipCollapsed = skipCollapsed;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        binder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewCreated(view);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {

            if (dimAmount != 0.6f) {
                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams windowParams = window.getAttributes();
                    windowParams.dimAmount = dimAmount;
                    Log.d("DIM", "amount: " + windowParams.dimAmount);
                    windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(windowParams);
                }
            }

            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            dialog.setOnShowListener(d -> {
                if (sheetBehavior != null) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (bottomSheetListener != null) {
                    bottomSheetListener.onShow();
                }
            });
            dialog.setOnCancelListener(d -> {
                if (bottomSheetListener != null) {
                    bottomSheetListener.onHide();
                }
            });
            dialog.setOnDismissListener(d -> {
                if (bottomSheetListener != null) {
                    bottomSheetListener.onHide();
                }
            });
            FrameLayout bottomSheetView = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheetView != null) {
                if (bottomSheetView.getLayoutParams() != null && fullScreen) {
                    bottomSheetView.getLayoutParams().height = LibraryHelper.getDeviceHeightByPercentage(getActivity(), 95);
                }
                sheetBehavior = BottomSheetBehavior.from(bottomSheetView);
                if (sheetBehavior != null) {
                    if (halfExpandedEnabled) {
                        sheetBehavior.setHalfExpandedRatio(0.6f);
                        sheetBehavior.setFitToContents(false);
                    } else {
                        sheetBehavior.setFitToContents(isFitToContents);
                    }
                    if (peekHeight != 0) {
                        sheetBehavior.setPeekHeight(peekHeight);
                    }
                    if (expandedOffset != 0) {
                        sheetBehavior.setExpandedOffset(expandedOffset);
                    }
                    sheetBehavior.setHideable(hideable);
                    sheetBehavior.setSkipCollapsed(skipCollapsed);
                    sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {
                            if (bottomSheetCallback != null) {
                                bottomSheetCallback.onStateChanged(bottomSheet, newState);
                            }
                            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                                if (bottomSheetListener != null) {
                                    bottomSheetListener.onHide();
                                }
                                dismiss();
                            }
                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                            if (bottomSheetCallback != null) {
                                bottomSheetCallback.onSlide(bottomSheet, slideOffset);
                            }
                        }
                    });

                }
            }
        }
    }

    public void hideUponCall() {
        if (sheetBehavior != null) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            if (bottomSheetListener != null) {
                bottomSheetListener.onHide();
            }
            dismiss();
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

    public class SimpleBottomSheetListener implements BottomSheetListener {

        @Override
        public void onShow() {

        }

        @Override
        public void onHide() {

        }
    }

    public interface BottomSheetListener {

        void onShow();

        void onHide();

    }

}