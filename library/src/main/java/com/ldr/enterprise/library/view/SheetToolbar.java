package com.ldr.enterprise.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.ldr.enterprise.library.R;
import com.ldr.enterprise.library.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SheetToolbar extends LinearLayout {

    @BindView(R2.id.sheet_indicator)
    View sheetIndicator;
    @BindView(R2.id.sheet_title)
    TextView sheetTitle;
    @BindView(R2.id.sheet_button_close)
    ImageButton sheetClose;
    @BindView(R2.id.sheet_divider)
    View sheetDivider;

    @OnClick(R2.id.sheet_button_close)
    void onCloseClicked(View v) {
        if (onCloseClickListener != null) {
            onCloseClickListener.onClick(v);
        }
    }

    private OnClickListener onCloseClickListener;

    public void setOnCloseClickListener(OnClickListener onCloseClickListener) {
        this.onCloseClickListener = onCloseClickListener;
    }

    public void setSheetTitle(String title) {
        if (sheetTitle != null) {
            sheetTitle.setText(title);
        }
    }

    public void setSheetTitleVisible(boolean visible) {
        sheetTitle.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setSheetCloseVisible(boolean visible) {
        sheetClose.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setSheetIndicatorVisible(boolean visible) {
        sheetIndicator.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setSheetDividerVisible(boolean visible) {
        sheetDivider.setVisibility(visible ? VISIBLE : GONE);
    }

    public SheetToolbar(Context context) {
        super(context);
        init();
    }

    public SheetToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initiateAttribute(attrs);
    }

    public SheetToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initiateAttribute(attrs);
    }

    public SheetToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initiateAttribute(attrs);
    }

    private void init() {
        setOrientation(VERTICAL);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        View.inflate(getContext(), R.layout.widget_sheet_toolbar, this);
        ButterKnife.bind(this, this);
    }

    private void initiateAttribute(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                    R.styleable.SheetToolbar, 0, 0);
            try {
                //with the text and colors specified using the names in attrs.xml
                String title = a.getString(R.styleable.SheetToolbar_sheetTitle);//0 is default
                boolean isCloseVisible = a.getBoolean(R.styleable.SheetToolbar_sheetCloseVisible, true);
                boolean isTitleVisible = a.getBoolean(R.styleable.SheetToolbar_sheetTitleVisible, true);
                boolean isIndicatorVisible = a.getBoolean(R.styleable.SheetToolbar_sheetIndicatorVisible, true);
                boolean isDividerVisible = a.getBoolean(R.styleable.SheetToolbar_sheetDividerVisible, false);
                if (title != null) {
                    sheetTitle.setText(title);
                }
                sheetClose.setVisibility(isCloseVisible ? VISIBLE : GONE);
                sheetTitle.setVisibility(isTitleVisible ? VISIBLE : GONE);
                sheetIndicator.setVisibility(isIndicatorVisible ? VISIBLE : GONE);
                sheetDivider.setVisibility(isDividerVisible ? VISIBLE : GONE);
            } finally {
                a.recycle();
            }
        }
    }

}
