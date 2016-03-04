package com.nucyzh.Sensor;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nucyzh.R;
import com.nucyzh.flipperactivity.activity_test.Main;

public class ShakeService extends Service {
    private static final String TAG = "ShakeService" ;
    public static final String ACTION = "ShakeService";
    private Vibrator mVibrator;// 开启震动
    private ShakeListener mShakeListener = null;

    private SoundPool soundPool;// 音频池
    private int hitOkSfx;
    private int hitOkSfx1;
    @Override
    public void onCreate() {
        mVibrator = (Vibrator) getApplication().getSystemService(
                VIBRATOR_SERVICE);
        // 这里指定声音池的最大音频流数目为10，
        // 声音品质为5可以自己测试感受下效果
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        // 载入音频流
        hitOkSfx = soundPool.load(this, R.raw.shake_sound_male, 0);
        hitOkSfx1 = soundPool.load(this, R.raw.shake_match, 0);
        mShakeListener = new ShakeListener(this);
        setListener();
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        // 监听摇晃
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

            @Override
            public void onShake() {
                setShark();
            }
        });
    }

    /**
     * 摇一摇
     */
    private void setShark() {
        mShakeListener.stop();
        startVibrato();//开始振动
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mVibrator.cancel();
                mShakeListener.start();
                // 播放音频，可以对左右音量分别设置，还可以设置优先级，循环次数以及速率
                // 速率最低0.5最高为2，1代表 正常速度
                soundPool.play(hitOkSfx1, 1, 1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(ShakeService.this, Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ShakeService.this.startActivity(intent);
            }
        }, 2000);
    }


    /**
     * 定义振动
     */
    public void startVibrato() {
        // 第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1);
        // 播放音频，可以对左右音量分别设置，还可以设置优先级，循环次数以及速率
        // 速率最低0.5最高为2，1代表 正常速度
        soundPool.play(hitOkSfx, 1, 1, 0, 0, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "ServiceDemo onBind");
        return null;
    }
    @Override
    public void onStart(Intent intent, int startId) {
        Log.v(TAG, "ServiceDemo onStart");
        super.onStart(intent, startId);
    }
}
