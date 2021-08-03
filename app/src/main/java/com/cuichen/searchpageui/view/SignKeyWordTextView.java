package com.cuichen.searchpageui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;


import com.cuichen.searchpageui.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignKeyWordTextView extends AppCompatTextView {
    //关键字
    private String signText;
    //关键字颜色
    private int signTextColor;

    private int changeIndex = 0;

    public SignKeyWordTextView(Context context) {
        super(context);
    }

    public SignKeyWordTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeAttrs(attrs);
    }

    public SignKeyWordTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttrs(attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        changeIndex = 0;
        if (text == null) {
            text = "";
        }
        super.setText(matcherSignText(text.toString()), type);
    }

    //初始化自定义属性
    private void initializeAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SignKeyWordTextView);
        //获取关键字
        signText = typedArray.getString(R.styleable.SignKeyWordTextView_signText);
        //获取关键字颜色
        signTextColor = typedArray.getColor(R.styleable.SignKeyWordTextView_signTextColor, getTextColors().getDefaultColor());
        typedArray.recycle();
    }

    public void setSignText(String signText) {
        this.signText = signText;
        if (!TextUtils.isEmpty(getText())) {
            setText(getText());
        }
    }

    public void setSignTextColor(int signTextColor) {
        this.signTextColor = signTextColor;
        if (!TextUtils.isEmpty(getText())) {
            setText(getText());
        }
    }

    // 高亮显示
    public SpannableStringBuilder matcherSignText(CharSequence mOriginalText) {
        changeIndex++;
        int change = 0;
        if (TextUtils.isEmpty(mOriginalText)) {
            return new SpannableStringBuilder("");
        }
        if (TextUtils.isEmpty(signText)) {
            return new SpannableStringBuilder(mOriginalText);
        }
        //关键代码
        SpannableStringBuilder builder = new SpannableStringBuilder(mOriginalText);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(signTextColor);
        Pattern p = Pattern.compile(signText);
        Matcher m = p.matcher(mOriginalText);
        while (m.find()) {
            change++;
            int start = m.start();
            int end = m.end();
            if (changeIndex == change) {
                builder.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return matcherSignText(builder);
            }
        }
        return builder;
    }
}
