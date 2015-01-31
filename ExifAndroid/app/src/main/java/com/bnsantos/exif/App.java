package com.bnsantos.exif;

import android.app.Application;

/**
 * Created by bruno on 11/11/14.
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public synchronized static App getInstance() {
        return sInstance;
    }

}
