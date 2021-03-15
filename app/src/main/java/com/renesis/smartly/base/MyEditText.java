package com.renesis.smartly.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
import com.renesis.smartly.R;

public class MyEditText extends TextInputEditText {

    //    private static final int DEFAULT_BACKGROUND = R.drawable.bg_solid_rounded_gray;
//    private static final int DEFAULT_ERROR_BACKGROUND = R.drawable.bg_edit_text_error;
    private static final int DEFAULT_BACKGROUND = R.drawable.bg_solid_rectangle_gray_1;
    private static final int DEFAULT_ERROR_BACKGROUND = R.drawable.bg_solid_rectangle_red_1;

    private int defaultBackground;
    private int defaultErrorBackground;


    private String errorText = "";
    private ErrorChangeListener errorChangedListener = null;
    private ValidationListener validationListener = null;

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }


    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.MyEditText, 0, 0);
        if (attr == null) {
            return;
        }

        try {
            defaultBackground = attr.getResourceId(R.styleable.MyEditText_defaultBackground, DEFAULT_BACKGROUND);
            defaultErrorBackground = attr.getResourceId(R.styleable.MyEditText_errorBackground, DEFAULT_ERROR_BACKGROUND);
        } finally {
            attr.recycle();
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        resetError();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if (!focused && validationListener != null) {
            validationListener.callValidator();
        }
    }

    protected void resetError() {
        if (errorText != null) {
            setErrorText(null);
            if (errorChangedListener != null) {
                errorChangedListener.onErrorChanged(null);
            }
        }

    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;

        if (errorText == null || errorText.isEmpty()) {
            setBackgroundResource(defaultBackground);
        } else {
            setBackgroundResource(defaultErrorBackground);
        }
    }

    public void setErrorChangedListener(ErrorChangeListener errorChangedListener) {
        this.errorChangedListener = errorChangedListener;
    }

    public void setValidationListener(ValidationListener validationListener) {
        this.validationListener = validationListener;
    }

    public interface ErrorChangeListener {
        void onErrorChanged(String error);
    }

    public interface ValidationListener {
        void callValidator();
    }

}
