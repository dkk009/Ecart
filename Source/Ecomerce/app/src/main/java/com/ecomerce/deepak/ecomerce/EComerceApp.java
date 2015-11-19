package com.ecomerce.deepak.ecomerce;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;

import com.ecomerce.deepak.ecomerce.network.ImageDownloader;

/**
 *
 */
public class EComerceApp extends Application {

    private static EComerceApp instance;
    public static EComerceApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //registerComponentCallbacks(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL) {
            ImageDownloader.getInstance().getImageCache().evictAll();
        }
    }

}
