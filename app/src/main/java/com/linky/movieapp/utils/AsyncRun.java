package com.linky.movieapp.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by linky on 4/28/17.
 */

public class AsyncRun {

    public static void runOnUiThread(Runnable action) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(action);
    }

    public static void runDelayOnUiThread(Runnable action, int delay) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(action, delay);
    }
}
