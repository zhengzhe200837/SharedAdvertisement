package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.wind.adv.UserInfoActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mMainScan = null;
    private MapView mMapView = null;
    private boolean isFirstLoc = true;
    private LocationClient mLocClient;
    private MyLocationData locData;
    private float mCurrentAccracy;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.mipmap.map_ad_location_blue);
    private BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.mipmap.map_ad_location_green);
    private BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.mipmap.map_ad_location_pink);
    private BitmapDescriptor bdD = BitmapDescriptorFactory.fromResource(R.mipmap.map_ad_location_yellow);
    private Marker mMarkerA = null;
    private Marker mMarkerB = null;
    private Marker mMarkerC = null;
    private Marker mMarkerD = null;
    private Dialog mDialog;
    private ImageView mMainDialogClose = null;
    private TextView mMainDialogDetail = null;
    private TextView mMainDialogPublish = null;
    private SensorManager mSensorManager = null;
    private ImageView mUserInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserInfo = (ImageView)findViewById(R.id.user_info);
        mUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSubActivity(MainActivity.this, UserInfoActivity.class);
            }
        });
        // 地图初始化
        mMapView = (MapView)findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng ll = marker.getPosition();
                mTarget = marker.getPosition();
                if (marker == mMarkerA) {
                    moveMapCenter(ll);
                    showDialog();
                } else if (marker == mMarkerB) {
                    moveMapCenter(ll);
                    showDialog();
                } else if (marker == mMarkerC) {
                    moveMapCenter(ll);
                    showDialog();
                } else if (marker == mMarkerD) {
                    moveMapCenter(ll);
                    showDialog();
                }
                return true;
            }
        });

        setPadding();
        mMapView.post(new Runnable() {
            @Override
            public void run() {
                addViewForMapView();
            }
        });
        starLoc();

//        mMainScan = (TextView)findViewById(R.id.main_scan);
//        mMainScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startSubActivity(MainActivity.this, MessageAuthenticationActivity.class);
//            }
//        });
    }

    private LatLng mTarget;
    private LatLng mLastTarget;


    private void moveMapCenter(LatLng ll) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(
                MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    public void initOverlay() {
        LatLng llA = new LatLng(mCurrentLat + 0.001, mCurrentLon + 0.001);
        MarkerOptions ooA = new MarkerOptions().position(llA)
                .icon(bdA);
        LatLng llB = new LatLng(mCurrentLat + 0.001, mCurrentLon - 0.001);
        MarkerOptions ooB = new MarkerOptions().position(llB)
                .icon(bdB);
        LatLng llC = new LatLng(mCurrentLat - 0.001, mCurrentLon + 0.001);
        MarkerOptions ooC = new MarkerOptions().position(llC)
                .icon(bdC);
        LatLng llD = new LatLng(mCurrentLat - 0.001, mCurrentLon - 0.001);
        MarkerOptions ooD = new MarkerOptions().position(llD)
                .icon(bdD);
        mMarkerA = (Marker)mBaiduMap.addOverlay(ooA);
        mMarkerB = (Marker)mBaiduMap.addOverlay(ooB);
        mMarkerC = (Marker)mBaiduMap.addOverlay(ooC);
        mMarkerD = (Marker)mBaiduMap.addOverlay(ooD);
    }

    private void setPadding() {
        mBaiduMap.setViewPadding(0, 0, 0, 120);
    }

    private void addViewForMapView() {
        TextView mTextView = new TextView(this);
        mTextView.setText(getResources().getString(R.string.main_bottom_text));
        mTextView.setTextSize(20.0f);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor
                (Color.parseColor("#626262"));
        mTextView.setBackgroundColor
                (Color.parseColor("#B9F0F0F0"));
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSubActivity(MainActivity.this, MessageAuthenticationActivity.class);
            }
        });
        MapViewLayoutParams.Builder builder = new MapViewLayoutParams.Builder();
        builder.layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode);
        builder.width(mMapView.getWidth());
        builder.height(120);
        builder.point(new Point(0, mMapView.getHeight()));
        builder.align(MapViewLayoutParams.ALIGN_LEFT, MapViewLayoutParams.ALIGN_BOTTOM);
        mMapView.addView(mTextView, builder.build());
    }

    /**
     * 开始定位
     */
    private void starLoc(){
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListenner());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);  //打开gps
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mLastTarget = new LatLng(mCurrentLat, mCurrentLon);
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon)
                    .build();
            mBaiduMap.setMyLocationData(locData);

            mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(mLastTarget).zoom(18.0f);
            mBaiduMap.animateMapStatus(
                    MapStatusUpdateFactory.newMapStatus(builder.build()));

            initOverlay();
        }
    }

    private double lastX;
    private int mCurrentDirection = 0;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon)
                    .build();
            mBaiduMap.setMyLocationData(locData);
            if (mTarget != null) {
                moveMapCenter(mTarget);
            }
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
//        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        android.util.Log.d("zhengzhe", "---onresume()");
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }
    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mDialog != null) {
            closeDialog();
        }
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }


    private void startSubActivity(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    private void showDialog() {
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mTarget = null;
                moveMapCenter(mLastTarget);
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.main_dialog_layout, null);
        mMainDialogClose = (ImageView) view.findViewById(R.id.main_dialog_close);
        mMainDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        mMainDialogDetail = (TextView) view.findViewById(R.id.main_dialog_detail);
        mMainDialogDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSubActivity(MainActivity.this, AdvertisementBoardDetailInfoActivity.class);
            }
        });
        mMainDialogPublish = (TextView) view.findViewById(R.id.main_dialog_publish);
        mMainDialogPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;
        lp.height = 750;
        dialogWindow.setAttributes(lp);
        mDialog.show();
    }

    private void closeDialog() {
        mDialog.dismiss();
        mTarget = null;
        moveMapCenter(mLastTarget);
    }
}