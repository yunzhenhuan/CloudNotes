package com.nucyzh.Sensor;

import android.app.Activity;
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

public class ShakeActivity extends Activity {
	private ImageView shark_img;// ҡһҡͼƬ
	private Button shark_button;// ҡһҡ��ť

	private Vibrator mVibrator;// ������
	private ShakeListener mShakeListener = null;

	private SoundPool soundPool;// ��Ƶ��
	private int hitOkSfx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);

		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);
		// ����ָ�������ص������Ƶ����ĿΪ10��
		// ����Ʒ��Ϊ5��ҿ����� �����Ը�����Ч��
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		// ������Ƶ��
		hitOkSfx = soundPool.load(this, R.raw.shake, 0);

		initView();
	}

	/**
	 * ��ʼ��UI
	 */
	private void initView() {
		shark_img = (ImageView) findViewById(R.id.img);
		shark_button = (Button) findViewById(R.id.button);
		mShakeListener = new ShakeListener(this);

		setListener();
	}

	/**
	 * ���ü�����
	 */
	private void setListener() {
		// ҡһҡ��ť
		shark_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setShark();
			}
		});
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
		setAnim();
		mShakeListener.stop();
		startVibrato();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mVibrator.cancel();
				mShakeListener.start();
				shark_img.clearAnimation();
			}
		}, 2000);
	}

	/**
	 * ��������
	 */
	private void setAnim() {
		Animation operatingAnim = AnimationUtils.loadAnimation(this,
				R.anim.shark_anim);
		shark_img.startAnimation(operatingAnim);
	}

	/**
	 * ������
	 */
	public void startVibrato() {
		// ��һ�����������ǽ������飬 �ڶ����������ظ�������-1Ϊ���ظ�����-1���մ�pattern��ָ���±꿪ʼ�ظ�
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
		// ������Ƶ�����Զ����������ֱ����ã��������������ȼ���ѭ�������Լ�����
		// �������0.5���Ϊ2��1���� �����ٶ�
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
