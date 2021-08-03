package com.cuichen.searchpageui.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description 历史搜索
 */
public class SearchHistoryUtils {

    private static final int MAX_SAVE_NUM = 20;//最大保存数
    private static final String SAVA_TAG = "※,※";//分隔符标记

    // 保存搜索记录
    public static void saveSearchHistory(String inputText, Context context) {
        String longHistory = SPFactory.getSearchHistoryData(context); //获取之前保存的历史记录
        String[] tmpHistory = longHistory.split(SAVA_TAG); //逗号截取 保存在数组中
        List<String> historyList = new ArrayList<>(Arrays.asList(tmpHistory)); //将改数组转换成ArrayList
        if (historyList.size() > 0) {
            //1.移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            historyList.add(0, inputText); //将新输入的文字添加集合的第0位也就是最前面(2.倒序)
            if (historyList.size() > MAX_SAVE_NUM) {
                historyList.remove(historyList.size() - 1); //3.最多保存8条搜索记录 删除最早搜索的那一项
            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + SAVA_TAG);
            }
            //保存到sp
            SPFactory.setSearchHistoryData(context, sb.toString());
        } else {
            //之前未添加过
            SPFactory.setSearchHistoryData(context, inputText + SAVA_TAG);
        }
    }

    //获取搜索记录
    public static List<String> getSearchHistory(Context context) {
        String longHistory = SPFactory.getSearchHistoryData(context);
        String[] tmpHistory = longHistory.split(SAVA_TAG); //split后长度为1有一个空串对象
        List<String> historyList = new ArrayList<>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) { //如果没有搜索记录，split之后第0位是个空串的情况下
            historyList.clear();  //清空集合，这个很关键
        }
        return historyList;
    }

    //清除历史
    public static void clearALlSearchHistory(Context context) {
        SPFactory.setSearchHistoryData(context, "");
    }
}
