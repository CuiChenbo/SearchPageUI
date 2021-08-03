package com.cuichen.searchpageui.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * description: SharedPreferences 工厂类
 */
public class SPFactory {

    private static final String PDACLIENT = "PDACLIENT";

    private static final String SEARCH_HISTORY = "search_history";//历史搜索

    private static SharedPreferences mShare;
    private static SharedPreferences.Editor mEdit;

    public static void setSearchHistoryData(Context context, String data) {
        if (mShare == null) {
            mShare = context.getSharedPreferences(PDACLIENT, Context.MODE_PRIVATE);
        }
        mEdit = mShare.edit();
        mEdit.putString(SEARCH_HISTORY, data);
        mEdit.apply();
    }

    public static String getSearchHistoryData(Context context) {
        if (mShare == null) {
            mShare = context.getSharedPreferences(PDACLIENT, Context.MODE_PRIVATE);
        }
        return mShare.getString(SEARCH_HISTORY, "");
    }
}
