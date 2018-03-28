package com.coderschool.walmart_android;

import android.app.Application;

import com.google.gson.Gson;

/**
 * Created by tringo on 3/28/18.
 */

public class App extends Application {

    private static App mSelf;
    private Gson mGson;

    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        mGson = new Gson();
    }

    public Gson getGson() {
        return mGson;
    }

}
