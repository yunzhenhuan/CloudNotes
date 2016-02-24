package com.nucyzh.Sensor;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.nucyzh.R;
import com.nucyzh.flipperactivity.activity_test.Main;


public class ShakeActivity extends Activity {
	private ImageView shark_img;// 摇一摇图片
	private Button shark_button;// 摇一摇按钮

	private Vibrator mVibrator;// 开启震动
	private ShakeListener mShakeListener = null;

	private SoundPool soundPool;// 音频池
	private int hitOkSfx;
	private int hitOkSfx1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.shake_activity);

		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);
		// 这里指定声音池的最大音频流数目为10，
		// 声音品质为5可以自己测试感受下效果
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		// 载入音频流
		//hitOkSfx = soundPool.load(this, R.raw.shake, 0);
		hitOkSfx = soundPool.load(this, R.raw.shake_sound_male, 0);
		hitOkSfx1 = soundPool.load(this, R.raw.shake_match, 0);
		initView();
	}

	/**
	 * 初始化UI
	 */
	private void initView() {
		shark_img = (ImageView) findViewById(R.id.img);
		shark_button = (Button) findViewById(R.id.button);
		mShakeListener = new ShakeListener(this);

		setListener();
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		// 摇一摇按钮
		shark_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setShark();
			}
		});
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
		setAnim();
		mShakeListener.stop();
		startVibrato();//开始振动

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mVibrator.cancel();
				mShakeListener.start();
				shark_img.clearAnimation();

			}
		}, 2000);
		Intent intent = new Intent();
		intent.setClass(ShakeActivity.this, Main.class);
		ShakeActivity.this.startActivity(intent);
		// 播放音频，可以对左右音量分别设置，还可以设置优先级，循环次数以及速率
		// 速率最低0.5最高为2，1代表 正常速度
		soundPool.play(hitOkSfx1, 1, 1, 0, 0, 1);
	}

	/**
	 * 动画设置
	 */
	private void setAnim() {
		Animation operatingAnim = AnimationUtils.loadAnimation(this,
				R.anim.shark_anim);
		shark_img.startAnimation(operatingAnim);
	}

	/**
	 * 定义振动
	 */
	public void startVibrato() {
		// 第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
		// 播放音频，可以对左右音量分别设置，还可以设置优先级，循环次数以及速率
		// 速率最低0.5最高为2，1代表 正常速度
		soundPool.play(hitOkSfx, 1, 1, 0, 0, 1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

}
