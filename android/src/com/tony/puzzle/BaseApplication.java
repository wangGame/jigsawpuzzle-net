package com.tony.puzzle;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.kw.gdx.constant.Configuration;
import java.util.Map;
public class BaseApplication extends AndroidApplication {
    private static BaseApplication instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        initDevice();
        instance = null;
        instance = getInstance();
    }

    private void initDevice() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        Configuration.screen_width = metrics.widthPixels;
        Configuration.screen_height = metrics.heightPixels;
        Configuration.device_name = Build.MODEL;
        Configuration.availableMem = (int) (outInfo.availMem / 1000000F);
        Configuration.APILevel = Build.VERSION.SDK_INT;
        Configuration.init_device();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
