package com.nucyzh.flipperactivity.activity_test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import com.nucyzh.Sensor.ShakeService;
import com.nucyzh.Settings.Settings;
import com.nucyzh.flipperactivity.expandgroup.About;
import com.nucyzh.flipperactivity.expandgroup.LoginAndLogout;
import com.nucyzh.flipperactivity.expandgroup.Text1_2;
import com.nucyzh.flipperactivity.expandgroup.Text1_3;
import com.nucyzh.flipperactivity.expandgroup.Text2_1;
import com.nucyzh.flipperactivity.expandgroup.Text2_2;
import com.nucyzh.flipperactivity.expandgroup.Text2_3;
import com.nucyzh.flipperactivity.expandgroup.Text3_1;
import com.nucyzh.flipperactivity.expandgroup.Text3_2;
import com.nucyzh.flipperactivity.expandgroup.Text3_3;
import com.nucyzh.flipperactivity.expandgroup.Text4_1;
import com.nucyzh.flipperactivity.expandgroup.Text4_2;
import com.nucyzh.flipperactivity.expandgroup.Text4_3;
import com.nucyzh.login_third.LoginActivity;
import com.nucyzh.notes.NotesActivity;


public class Main extends Activity implements FlipperLayout.OnOpenListener {
    private static String TAG = "Main";
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_EDIT_NOTE = 2;
    private FlipperLayout mRoot;
    private Desktop mDesktop;
    private About mAbout;

    private Text1_2 text2;
    private Text1_3 text3;
    private Text2_1 text4;
    private Text2_2 text5;
    private Text2_3 text6;
    private Text3_1 text7;
    private Text3_2 text8;
    private Text3_3 text9;
    private Text4_1 text10;
    private Text4_2 text11;
    private Text4_3 text12;
    private LoginAndLogout loginAndLogout;
    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.v(TAG, "onServiceConnected");
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.v(TAG, "onServiceDisconnected");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRoot = new FlipperLayout(Main.this);
        mDesktop = new Desktop(Main.this);

        text2 = new Text1_2(this);
        text3 = new Text1_3(this);
        text4 = new Text2_1(this);
        text5 = new Text2_2(this);
        text6 = new Text2_3(this);
        text7 = new Text3_1(this);
        text8 = new Text3_2(this);
        text9 = new Text3_3(this);
        text10 = new Text4_1(this);
        text11 = new Text4_2(this);
        text12 = new Text4_3(this);
        loginAndLogout = new LoginAndLogout(this);
        mAbout = new About(this);

        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mRoot.setLayoutParams(params);
        mRoot.addView(mDesktop.getView(), params);
        mRoot.addView(mAbout.getView(), params);
        setContentView(mRoot);
        mDesktop.setOnChangeViewListener(new Desktop.OnChangeViewListener() {
            @Override
            public void onChangeView(int args) {
                switch (args) {
                    case 1:
                        Intent intent = new Intent(Main.this, NotesActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        mRoot.close(text2.getView());
                        break;
                    case 3:
                        mRoot.close(text3.getView());
                        break;
                    case 4:
                        mRoot.close(text4.getView());
                        break;
                    case 5:
                        mRoot.close(text5.getView());
                        break;
                    case 6:
                        mRoot.close(text6.getView());
                        break;
                    case 7:
                        mRoot.close(text7.getView());
                        break;
                    case 8:
                        mRoot.close(text8.getView());
                        break;
                    case 9:
                        mRoot.close(text9.getView());
                        break;
                    case 10:
                        mRoot.close(text10.getView());
                        break;
                    case 11:
                        mRoot.close(text11.getView());
                        break;
                    case 12:
                        mRoot.close(text12.getView());
                        break;
                    case 21:
                        Intent settings = new Intent(Main.this, Settings.class);
                        startActivity(settings);
                        Log.i("Settings", "test2");
                        break;
                    case 22:
                        // mRoot.close(loginAndLogout.getView());
                        Intent loginAndLogout = new Intent(Main.this, LoginActivity.class);
                        startActivity(loginAndLogout);
                        break;
                    default:
                        break;
                }
            }

        });
        bindService(new Intent(ShakeService.ACTION), conn, BIND_AUTO_CREATE);//启动服务
    }

    public void open() {
        if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
            mRoot.open();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
