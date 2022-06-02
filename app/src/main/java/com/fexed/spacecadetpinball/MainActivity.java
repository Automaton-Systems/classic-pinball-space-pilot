package com.fexed.spacecadetpinball;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.libsdl.app.SDLActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends SDLActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File filesDir = getFilesDir();
        copyAssets(filesDir);
        initNative(filesDir.getAbsolutePath() + "/");

        View v = getLayoutInflater().inflate(R.layout.activity_main, mLayout, false);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mLayout.addView(v, layoutParams);

        v.bringToFront();

        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);
        Button plunger = findViewById(R.id.plunger);
        Button t_left = findViewById(R.id.tilt_left);
        Button t_right = findViewById(R.id.tilt_right);

        left.setOnTouchListener((v1, event) -> {
            v1.performClick();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                SDLActivity.onNativeKeyDown(KeyEvent.KEYCODE_Z);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SDLActivity.onNativeKeyUp(KeyEvent.KEYCODE_Z);
            }
            return false;
        });

        t_left.setOnTouchListener((v1, event) -> {
            v1.performClick();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                SDLActivity.onNativeKeyDown(KeyEvent.KEYCODE_X);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SDLActivity.onNativeKeyUp(KeyEvent.KEYCODE_X);
            }
            return false;
        });

        right.setOnTouchListener((v1, event) -> {
            v1.performClick();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                SDLActivity.onNativeKeyDown(KeyEvent.KEYCODE_SLASH);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SDLActivity.onNativeKeyUp(KeyEvent.KEYCODE_SLASH);
            }
            return false;
        });

        t_right.setOnTouchListener((v1, event) -> {
            v1.performClick();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                SDLActivity.onNativeKeyDown(KeyEvent.KEYCODE_PERIOD);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SDLActivity.onNativeKeyUp(KeyEvent.KEYCODE_PERIOD);
            }
            return false;
        });

        plunger.setOnTouchListener((v1, event) -> {
            v1.performClick();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                SDLActivity.onNativeKeyDown(KeyEvent.KEYCODE_SPACE);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SDLActivity.onNativeKeyUp(KeyEvent.KEYCODE_SPACE);
            }
            return false;
        });
    }

    private void copyAssets(File filesDir) {
        if (!new File(filesDir, "PINBALL.DAT").exists()) {
            AssetManager assetManager = getAssets();
            try {
                for (String asset : assetManager.list("")) {
                    Log.d(TAG, "Copying " + asset);
                    try (InputStream is = assetManager.open(asset)){
                        try (OutputStream os = new FileOutputStream(new File(filesDir, asset))) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = is.read(buffer)) != -1) {
                                os.write(buffer, 0, len);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getMainFunction() {
        return "main";
    }

    @Override
    protected String[] getLibraries() {
        return new String[] {
                "SDL2",
                "SpaceCadetPinball"
        };
    }

    private native void initNative(String dataPath);
}