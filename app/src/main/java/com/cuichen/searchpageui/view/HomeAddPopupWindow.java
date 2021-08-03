package com.cuichen.searchpageui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.cuichen.searchpageui.R;

public class HomeAddPopupWindow {
    private Context context;
    private PopupWindow popWindow;
    private View popup;
    private View anchor;

    public HomeAddPopupWindow(Context context , View anchor) {
        this.context = context;
        this.anchor = anchor;
        init();
    }

    private void init(){
        popup = LayoutInflater.from(context).inflate(R.layout.popup_home_add, null);
        popWindow = new PopupWindow(popup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.anim_home_add_popup);
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));


    }

    public void show(){
        popWindow.showAsDropDown(anchor , -dp2px(context , 90) , 10);
        View compile = popup.findViewById(R.id.layout_compile);
        View layout_scan = popup.findViewById(R.id.layout_scan);
        compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CompilePopupWindow(context , anchor).show();
                popWindow.dismiss();
            }
        });
        layout_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //扫一扫
                popWindow.dismiss();
            }
        });
    }

    public int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
