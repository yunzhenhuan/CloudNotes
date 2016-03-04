package com.nucyzh.streetsdk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nucyzh.R;
import com.nucyzh.streetsdkfragment.PanoramaFragment;
import com.nucyzh.streetsdkfragment.PanoramaFragmentController;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.map.TencentMap.OnMapClickListener;

public class PanoramaFragActivity extends FragmentActivity {

    private PanoramaFragment mPanoramaFragment;
    private PanoramaFragmentController mPanoramaFragmentController;
    private SensorManager sensorManager;
    private Sensor aSensor;
    private Sensor mSensor;
    private Sensor oSensor;

    private MapView mMapView;
    private TencentMap mTencentMap;
    private Marker location;
    private TencentLocationManager tLocationMan;
    private TencentLocationRequest tLocationReq;
    private TencentLocationListener tLocationListener;

    private Button btnLocationMan;
    private Button btnSomatosensory;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.pana_frag_activity);
        init();
    }

    private void init() {

        FragmentManager fragemtManager = getSupportFragmentManager();
        mPanoramaFragment = (PanoramaFragment) fragemtManager
                .findFragmentById(R.id.panorama_fragment);
        mPanoramaFragmentController = mPanoramaFragment
                .getPanoramaFragmentController();
        mMapView = (MapView) findViewById(R.id.mapview);
        mTencentMap = mMapView.getMap();
        LatLng latLng = new LatLng(38.0151667884,112.4467474222);//定义地图中心初始坐标
        mTencentMap.setCenter(latLng);
        mTencentMap.setSatelliteEnabled(true);
        mTencentMap.setZoom(18);//设置缩放级别

        btnLocationMan = (Button) findViewById(R.id.btn_location_manager);
        btnSomatosensory = (Button) findViewById(R.id.brn_somatosensory);
        //获取传感器管理器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //加速传感器
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //磁场传感器
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        oSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        tLocationListener = new TencentLocationListener() {

            @Override
            public void onStatusUpdate(String arg0, int arg1, String arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(TencentLocation arg0, int arg1, String arg2) {
                // TODO Auto-generated method stub
                if (arg1 == TencentLocation.ERROR_OK) {
                    LatLng latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    setPanoramaPosition(latLng);
                    tLocationMan.removeUpdates(tLocationListener);
                }
            }
        };
        tLocationMan = TencentLocationManager.getInstance(this);
        tLocationReq = TencentLocationRequest.create().setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_GEO);
        setPanoramaPosition(new LatLng(38.0153527473,112.4477344751));//设置fragment中的初始界面
        btnLocationMan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int res;
                // TODO Auto-generated method stub
                if (btnLocationMan.getText().toString().equals("开始定位")) {
                    res = tLocationMan.requestLocationUpdates(tLocationReq, tLocationListener);
                    if (res == 0) {
                        btnLocationMan.setText("停止定位");
                        ToastUtil.showLongToast(PanoramaFragActivity.this, "开始定位");
                    } else if (res == 1) {
                        ToastUtil.showLongToast(PanoramaFragActivity.this, "定位条件不满足或系统不允许定位");
                    } else if (res == 2) {
                        ToastUtil.showLongToast(PanoramaFragActivity.this, "key错误");
                    }
                } else {
                    tLocationMan.removeUpdates(tLocationListener);
                    btnLocationMan.setText("开始定位");
                }
            }
        });

        btnSomatosensory.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (btnSomatosensory.getText().toString().equals("开始互动")) {
                    setSomatosensory(true);
                    btnSomatosensory.setText("停止互动");
                } else {
                    setSomatosensory(false);
                    btnSomatosensory.setText("开始互动");
                }
            }
        });
        mTencentMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                setPanoramaPosition(arg0);
            }
        });
        ToastUtil.showLongToast(this, "点击地图或定位获取街景");
    }

    protected void setPanoramaPosition(LatLng latLng) {
        if (location == null) {
            location = mTencentMap.addMarker(new MarkerOptions().
                    position(latLng).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
        } else {
            location.setPosition(latLng);
        }
        mPanoramaFragmentController.setPosition(
                latLng.getLatitude(), latLng.getLongitude());
    }

    SensorEventListener sensorEventListner = new SensorEventListener() {
        float tAccZ = -999;
        float tPintch;
        float tBearing = -999;
        float accZ;
        float bearing;
        float pintch;

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    pintch = event.values[1];
                    if (Math.abs(tPintch - pintch) > 0.1f) {
                        mPanoramaFragmentController.setPanoramaTilt(-90 - pintch);
                    }
                    bearing = event.values[0];
                    if (Math.abs(tBearing - bearing) > 0.1f) {
                        mPanoramaFragmentController.setPanoramaBearing(bearing);
                    }
                    Log.d("orientation sensor", "bearing:" + bearing + " pintch:" + pintch);
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
            if (accuracy == SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
                switch (sensor.getType()) {
                    case Sensor.TYPE_ORIENTATION:
                        tPintch = pintch;
                        tBearing = bearing;
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                        tAccZ = accZ;
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        tBearing = bearing;
                        break;

                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
        tLocationMan.removeUpdates(tLocationListener);
        sensorManager.unregisterListener(sensorEventListner, oSensor);
        sensorManager.unregisterListener(sensorEventListner, aSensor);
        sensorManager.unregisterListener(sensorEventListner, mSensor);
    }

    private void setSomatosensory(Boolean boolean1) {
        if (boolean1) {
            sensorManager.registerListener(sensorEventListner, oSensor,
                    SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(sensorEventListner, aSensor,
                    SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(sensorEventListner, mSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        } else {
            sensorManager.unregisterListener(sensorEventListner, oSensor);
            sensorManager.unregisterListener(sensorEventListner, aSensor);
            sensorManager.unregisterListener(sensorEventListner, mSensor);
        }
    }
}