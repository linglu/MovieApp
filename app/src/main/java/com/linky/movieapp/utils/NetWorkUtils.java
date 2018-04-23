package com.linky.movieapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by oeasy on 2018/1/8.
 */

public class NetWorkUtils {
    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    public static boolean isNetConnected(Context paramContext) {
        boolean i = false;
        NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
            return true;
        return false;
    }
}
