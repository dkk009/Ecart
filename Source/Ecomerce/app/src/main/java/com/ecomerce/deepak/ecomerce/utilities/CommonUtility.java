package com.ecomerce.deepak.ecomerce.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.ecomerce.deepak.ecomerce.Constants.Constants;
import com.ecomerce.deepak.ecomerce.EComerceApp;

/**
 *
 */
public class CommonUtility {
    public static int getScreenWidth() {
        DisplayMetrics metrics = EComerceApp.getInstance().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics metrics = EComerceApp.getInstance().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
    public static int getDeviceDpType() {
        DisplayMetrics metrics = EComerceApp.getInstance().getResources().getDisplayMetrics();
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
            case DisplayMetrics.DENSITY_MEDIUM:
                return Constants.LOW_END_DEVICE;
            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_XHIGH:
                return Constants.MEDIUM_DEVICE;
            case DisplayMetrics.DENSITY_XXHIGH:
                return Constants.HIGH_END_DEVICE;
            case DisplayMetrics.DENSITY_XXXHIGH:
                return Constants.HIGH_END_DEVICE;
            default:
                return Constants.MEDIUM_DEVICE;

        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) EComerceApp.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    public static float convertDpToPixel(float dp){
        Resources resources = EComerceApp.getInstance().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
