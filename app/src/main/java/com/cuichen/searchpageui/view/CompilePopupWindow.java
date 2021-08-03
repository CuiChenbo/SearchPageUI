package com.cuichen.searchpageui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.PopupWindow;

import com.cuichen.searchpageui.R;

public class CompilePopupWindow {
    private Context context;
    private PopupWindow popWindow;
    private View popup;
    private View anchor;

    public CompilePopupWindow(Context context , View anchor) {
        this.context = context;
        this.anchor = anchor;
        init();
    }

    private void init(){
        popup = LayoutInflater.from(context).inflate(R.layout.popup_compile, null);
        popWindow = new PopupWindow(popup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setOutsideTouchable(false);
        popWindow.setFocusable(true);
        popWindow.setAnimationStyle(R.style.anim_home_compile_popup);
        popWindow.setBackgroundDrawable(new ColorDrawable(0x99000000));
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(1.0f);
            }
        });


        View layoutArticle = popup.findViewById(R.id.layoutArticle);
        View layoutVideo = popup.findViewById(R.id.layoutVideo);
        View ivClose = popup.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        layoutArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CompilePopupWindow", "onClick: 图文");
                popWindow.dismiss();
            }
        });
        layoutVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("CompilePopupWindow", "onClick: 视频");
                popWindow.dismiss();
            }
        });
    }

    public void show(){
        popWindow.showAtLocation(anchor , Gravity.BOTTOM , 0 , 0);
        setWindowAlpha(0.5f);
    }

    /**
     * 页面灰度
     * @alpha 05表示半灰  1f 表示全亮
     */
    private void setWindowAlpha(Float alpha){
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = alpha;
            activity.getWindow().setAttributes(params);
        }
    }

}
