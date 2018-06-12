package com.ansen.screensaver;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ScreenSaverActivity extends Activity {
    protected static final String TAG = "ScreenSaverActivity";
    private static PowerManager.WakeLock mWakeLock;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        // lp.userActivityTimeout = USER_ACTIVITY_TIMEOUT_WHEN_NO_PROX_SENSOR;
        getWindow().setAttributes(lp);

        setContentView(R.layout.activity_screen_saver);
        mVideoView = (VideoView) findViewById(R.id.vv);
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "ScreenSaver");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
        play();
    }

    private void play() {
        SharedPreferences sp = getSharedPreferences(PickVideo.SP_NAME, Context.MODE_PRIVATE);
        String videoPath = sp.getString(PickVideo.SP_Path, null);
        Log.e(TAG, "videoPath:" + videoPath);
        if (videoPath == null) {
            Toast.makeText(this, "Please select one video", Toast.LENGTH_LONG).show();
            return;
        }

        Uri uri = Uri.parse(videoPath);
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        mVideoView.setMediaController(mc);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setVideoURI(uri);
        //mVideoView.setVideoPath(files[0].getAbsolutePath());
        mVideoView.start();
        mVideoView.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
        mVideoView.requestFocus();
    }

    @Override
    protected void onPause() {
        mWakeLock.release();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_HOME
                || keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d(TAG, "onKeyDown: " + keyCode);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {    // 沉浸式模式
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
