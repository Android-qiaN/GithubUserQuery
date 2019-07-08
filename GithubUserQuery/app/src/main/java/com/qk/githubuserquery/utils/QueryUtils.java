package com.qk.githubuserquery.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Map;

/**
 * @描述: okhttp工具类
 * @包名: com.qk.githubuserquery.utils
 * @类名: QueryUtils
 * @日期: 2019/7/7
 * @作者: QianKun
 */
public class QueryUtils {

    /**
     * 检查网络状态
     *
     * @param context
     * @return true可用，false不可用
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 遍历获取使用频率最高的语言
     *
     * @param languageMap
     * @return 用户使用频率最高的语言
     */
    public static String getLanguageMax(Map<String, Integer> languageMap) {
        String languageMax = null;
        int max = 0;
        for (Map.Entry entry : languageMap.entrySet()) {
            if ((int) entry.getValue() > max) {
                max = (int) entry.getValue();
                languageMax = (String) entry.getKey();
            }
        }
        return languageMax;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
