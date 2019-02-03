package com.example.brendon.sistemadelogin.dal;

import com.example.brendon.sistemadelogin.Models.MyObjectBox;
import android.app.Application;
import io.objectbox.BoxStore;

public class App extends Application {

    public static final String TAG = "ObjectBoxExample";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}