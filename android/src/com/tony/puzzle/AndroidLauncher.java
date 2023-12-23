package com.tony.puzzle;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kw.gdx.constant.Configuration;
import com.kw.gdx.constant.Constant;

import kw.artpuzzle.JigSawPuzzle;
import kw.artpuzzle.listener.GameListener;

public class AndroidLauncher extends BaseApplication{
    private Vibrator vibrator;
    private JigSawPuzzle jigSawPuzzle;
    public static boolean isDebug = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isTaskRoot()){
            finish();
            return;
        }

        initVibrationEffect();
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        initImmersiveMode();
        if (Configuration.device_state != Configuration.DeviceState.poor) {
            configuration.r = 8;
            configuration.g = 8;
            configuration.b = 8;
        }
        //指南针
        configuration.useCompass = false;
        //加速度
        configuration.useAccelerometer = false;
        configuration.useWakelock = true;
        configuration.numSamples = 2;
        Constant.realseDebug = isDebug;
        jigSawPuzzle = new JigSawPuzzle(
                new AndroidDownLoad(this),
                new GameListener() {
                    @Override
                    public void changeLocalPath() {

                    }

                    @Override
                    public void toastShow() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AndroidLauncher.this,"No more coin",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
        initialize(jigSawPuzzle,configuration);
    }


    private void initVibrationEffect(){
        if (vibrator == null){
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }
    }

    @SuppressLint("NewApi")
    private void initImmersiveMode() {
        if (Build.VERSION.SDK_INT >= 19) {
            View.OnSystemUiVisibilityChangeListener listener = new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        enterImmersiveMode();
                    }
                }
            };
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(listener);
            enterImmersiveMode();
        }
    }

    @SuppressLint("NewApi")
    private void enterImmersiveMode() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initImmersiveMode();
        NotificationUtil.cancelAll(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotificationUtil.add(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        graphics.getView().requestFocus();
        return super.dispatchKeyEvent(event);
    }
}
