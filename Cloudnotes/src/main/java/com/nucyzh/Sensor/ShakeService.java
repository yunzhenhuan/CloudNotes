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
    private Vibrator mVibrator;// ������
    private ShakeListener mShakeListener = null;

    private SoundPool soundPool;// ��Ƶ��
    private int hitOkSfx;
    private int hitOkSfx1;
    @Override
    public void onCreate() {
        mVibrator = (Vibrator) getApplication().getSystemService(
                VIBRATOR_SERVICE);
        // ����ָ�������ص������Ƶ����ĿΪ10��
        // ����Ʒ��Ϊ5�����Լ����Ը�����Ч��
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        // ������Ƶ��
        hitOkSfx = soundPool.load(this, R.raw.shake_sound_male, 0);
        hitOkSfx1 = soundPool.load(this, R.raw.shake_match, 0);
        mShakeListener = new ShakeListener(this);
        setListener();
    }

    /**
     * ���ü�����
     */
    private void setListener() {
        // ����ҡ��
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

            @Override
            public void onShake() {
                setShark();
            }
        });
    }

    /**
     * ҡһҡ
     */
    private void setShark() {
        mShakeListener.stop();
        startVibrato();//��ʼ��
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mVibrator.cancel();
                mShakeListener.start();
                // ������Ƶ�����Զ����������ֱ����ã��������������ȼ���ѭ�������Լ�����
                // �������0.5���Ϊ2��1���� �����ٶ�
                soundPool.play(hitOkSfx1, 1, 1, 0, 0, 1);
                Intent intent = new Intent();
                intent.setClass(ShakeService.this, Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ShakeService.this.startActivity(intent);
            }
        }, 2000);
    }


    /**
     * ������
     */
    public void startVibrato() {
        // ��һ�����������ǽ������飬 �ڶ����������ظ�������-1Ϊ���ظ�����-1���մ�pattern��ָ���±꿪ʼ�ظ�
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1);
        // ������Ƶ�����Զ����������ֱ����ã��������������ȼ���ѭ�������Լ�����
        // �������0.5���Ϊ2��1���� �����ٶ�
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
