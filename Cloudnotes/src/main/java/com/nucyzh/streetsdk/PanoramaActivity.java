package com.nucyzh.streetsdk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nucyzh.R;
import com.tencent.tencentmap.streetviewsdk.Marker;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaFinishListner;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaCamera;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaView;

public class PanoramaActivity extends Activity implements
        OnStreetViewPanoramaChangeListener, OnStreetViewPanoramaFinishListner,
        OnStreetViewPanoramaCameraChangeListener {

    private StreetViewPanoramaView mPanoramaView;
    private StreetViewPanorama mPanorama;

    private RelativeLayout mControlPanel;
    private CheckBox cbGuidance;
    private CheckBox cbGestures;
    private CheckBox cbStreet;
    private CheckBox cbZoom;
    private CheckBox cbNavigation;
    private CheckBox cbGallery;
    private CheckBox cbScenceName;
    private CheckBox cbIndoorLink;

    private Animation controlAppear;
    private Animation controlVanish;

    private boolean streetViewPanoramaState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panarama_activity);
        init();
    }

    private void init() {
        mPanoramaView = (StreetViewPanoramaView) findViewById(R.id.panorama_view);
        mPanorama = mPanoramaView.getStreetViewPanorama();
        //初始化地点为中北大学
        mPanorama.setPosition(38.0142792507,112.4540698528);
        mPanorama.setOnStreetViewPanoramaChangeListener(this);
        mPanorama.setOnStreetViewPanoramaFinishListener(this);
        mPanorama.setOnStreetViewPanoramaCameraChangeListener(this);
        mControlPanel = (RelativeLayout) findViewById(R.id.layout_orientation_panel);
        mControlPanel.setAnimationCacheEnabled(true);
        mControlPanel.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
        cbGestures = (CheckBox) findViewById(R.id.cb_gestures);
        cbGuidance = (CheckBox) findViewById(R.id.cb_guidance);
        cbStreet = (CheckBox) findViewById(R.id.cb_streetname);
        cbZoom = (CheckBox) findViewById(R.id.cb_zoom);
        cbNavigation = (CheckBox) findViewById(R.id.cb_navigation);
        cbGallery = (CheckBox) findViewById(R.id.cb_gallery);
        cbScenceName = (CheckBox) findViewById(R.id.cb_scence_name);
        cbIndoorLink = (CheckBox) findViewById(R.id.cb_indoor_link);

        cbGestures.setChecked(mPanorama.isPanningGesturesEnabled());
        cbGuidance.setChecked(mPanorama.isIndoorGuidanceEnabled());
        cbStreet.setChecked(mPanorama.isStreetNamesEnabled());
        cbZoom.setChecked(mPanorama.isZoomGesturesEnabled());
        cbNavigation.setChecked(mPanorama.isUserNavigationEnabled());
        cbGallery.setChecked(mPanorama.isStreetGalleryEnabled());
        cbScenceName.setChecked(mPanorama.isScenceNameEnabled());
        cbIndoorLink.setChecked(mPanorama.isIndoorLinkPoiEnabled());

        addMarker();
        bindPanleListener();
        bindCheckBoxListener();
        setAnimation();
    }

    private void addMarker() {
        final Marker marker1 = new Marker(38.0142031755, 112.4494349957, convertViewToBitmap(getMarkerLayout("知行广场"))) {
            @Override
            public void onClick(float arg0, float arg1) {
                // TODO Auto-generated method stub
                mPanorama.setPosition(38.0142031755, 112.4494349957);
                ToastUtil.showLongToast(PanoramaActivity.this, "到达知行广场");
                super.onClick(arg0, arg1);
            }
        };
        mPanorama.addMarker(marker1);
        Marker marker2 = new Marker(38.0153020313,112.4477505684, convertViewToBitmap(getMarkerLayout("图书馆"))) {
            @Override
            public void onClick(float arg0, float arg1) {
                // TODO Auto-generated method stub
                mPanorama.setPosition(38.0153020313,112.4477505684);
                //使用街景id进入街景
                //mPanorama.setPosition("10011009120328110539700");
                //marker1.updateIcon(convertViewToBitmap(getMarkerLayout("点我到知行广场")));
                ToastUtil.showLongToast(PanoramaActivity.this, "到达图书馆");
                super.onClick(arg0, arg1);
            }

            @Override
            public float onGetItemScale(double arg0, float arg1) {
                // TODO Auto-generated method stub
                //Log.i("marker", "distance:" + arg0 +"; angleScale:" + arg1);
                return super.onGetItemScale(0.2 * arg0, arg1);
            }
        };
        mPanorama.addMarker(marker2);
        final Marker marker3 = new Marker(38.0093511074,112.4426972866, convertViewToBitmap(getMarkerLayout("一道门"))) {
            @Override
            public void onClick(float arg0, float arg1) {
                // TODO Auto-generated method stub
                mPanorama.setPosition(38.0093511074,112.4426972866);
                ToastUtil.showLongToast(PanoramaActivity.this, "到达一道门");
                super.onClick(arg0, arg1);
            }
        };
        mPanorama.addMarker(marker3);

        final Marker marker4 = new Marker(38.0141862699,112.4560546875, convertViewToBitmap(getMarkerLayout("田园"))) {
            @Override
            public void onClick(float arg0, float arg1) {
                // TODO Auto-generated method stub
                mPanorama.setPosition(38.0141862699,112.4560546875);
                ToastUtil.showLongToast(PanoramaActivity.this, "到达田园");
                super.onClick(arg0, arg1);
            }
        };
        mPanorama.addMarker(marker4);
        final Marker marker5 = new Marker(38.0122167405,112.4435234070, convertViewToBitmap(getMarkerLayout("二道门"))) {
            @Override
            public void onClick(float arg0, float arg1) {
                // TODO Auto-generated method stub
                mPanorama.setPosition(38.0122167405,112.4435234070);
                ToastUtil.showLongToast(PanoramaActivity.this, "到达二道门");
                super.onClick(arg0, arg1);
            }
        };
        mPanorama.addMarker(marker5);
        final Marker marker6 = new Marker(38.0148920755,112.4449396133, convertViewToBitmap(getMarkerLayout("工商银行"))) {
            @Override
            public void onClick(float arg0, float arg1) {
                // TODO Auto-generated method stub
                mPanorama.setPosition(38.0148920755,112.4449396133);
                ToastUtil.showLongToast(PanoramaActivity.this, "到达工商银行");
                super.onClick(arg0, arg1);
            }
        };
        mPanorama.addMarker(marker6);
    }

    private void bindPanleListener() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                controlVanish.reset();
                controlVanish.start();
            }
        };
    }

    private void bindCheckBoxListener() {
        OnCheckedChangeListener listener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                switch (buttonView.getId()) {
                    case R.id.cb_gestures:
                        mPanorama.setPanningGesturesEnabled(isChecked);
                        break;
                    case R.id.cb_guidance:
                        mPanorama.setIndoorGuidanceEnabled(isChecked);
                        break;
                    case R.id.cb_streetname:
                        mPanorama.setStreetNamesEnabled(isChecked);
                        break;
                    case R.id.cb_zoom:
                        mPanorama.setZoomGesturesEnabled(isChecked);
                        break;
                    case R.id.cb_navigation:
                        mPanorama.setUserNavigationEnabled(isChecked);
                        break;
                    case R.id.cb_gallery:
                        mPanorama.setStreetGalleryEnabled(isChecked);
                        break;
                    case R.id.cb_scence_name:
                        mPanorama.setScenceNameEnabled(isChecked);
                        break;
                    case R.id.cb_indoor_link:
                        mPanorama.setIndoorLinkPoiEnabled(isChecked);
                        break;
                    default:
                        break;
                }
            }
        };
        cbGestures.setOnCheckedChangeListener(listener);
        cbGuidance.setOnCheckedChangeListener(listener);
        cbStreet.setOnCheckedChangeListener(listener);
        cbZoom.setOnCheckedChangeListener(listener);
        cbNavigation.setOnCheckedChangeListener(listener);
        cbGallery.setOnCheckedChangeListener(listener);
        cbScenceName.setOnCheckedChangeListener(listener);
        cbIndoorLink.setOnCheckedChangeListener(listener);
    }

    private void setAnimation() {
        controlAppear = AnimationUtils.loadAnimation(this, R.anim.control_appear);
        controlAppear.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                mControlPanel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (isStreetViewPanoramaFinish()) {
                    mControlPanel.setAnimation(controlVanish);
                }
            }
        });

        controlVanish = AnimationUtils.loadAnimation(PanoramaActivity.this,
                R.anim.control_vanish);
        controlVanish.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                mControlPanel.setVisibility(View.GONE);
            }
        });

    }

    private LinearLayout getMarkerLayout(String title) {
        LayoutInflater layInflater = getLayoutInflater();
        LinearLayout markerLayout = (LinearLayout) layInflater.inflate(R.layout.marker, null);
        TextView tvMarkerTitle = (TextView) markerLayout.findViewById(R.id.marker_title);
        tvMarkerTitle.setText(title);
        return markerLayout;
    }

    private Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    private void setStreetViewPanoramaState(boolean state) {
        streetViewPanoramaState = state;
    }

    private boolean isStreetViewPanoramaFinish() {
        return streetViewPanoramaState;
    }

    @Override
    public void OnStreetViewPanoramaFinish(boolean arg0) {
        // TODO Auto-generated method stub
        Log.i("change", "panorama finish");
        setStreetViewPanoramaState(true);
        mControlPanel.startAnimation(controlVanish);
    }

    @Override
    public void onStreetViewPanoramaChange(String arg0) {
        // TODO Auto-generated method stub
        setStreetViewPanoramaState(false);
        if (mControlPanel.getVisibility() != View.GONE) {
            return;
        }
        mControlPanel.startAnimation(controlAppear);
    }

    @Override
    public void onStreetViewPanoramaCameraChange(final StreetViewPanoramaCamera arg0) {
        // TODO Auto-generated method stub
//		Log.i("camera", "camera zoom:" + arg0.zoom + 
//				"\ncamera tilt:" + arg0.tilt + "\ncamera bearing:" + arg0.bearing);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (mControlPanel.getVisibility() == View.GONE) {
                    mControlPanel.startAnimation(controlAppear);
                } else {
                    controlVanish.reset();
                    controlVanish.start();
                }
            }
        });
    }
}
