package sharedadvertisement.wind.com.sharedadvertisement;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.Glide;
import com.network.Network;
import com.network.model.AdvertisementBoardDetailInfo;
import com.network.model.LocationPoint;
import com.network.model.MyOrderItemInfo;
import com.wind.adv.AdvancedOptionsActivity;
import com.wind.adv.UserInfoActivity;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private MapView mMapView = null;
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
    private double lastX;
    private int mCurrentDirection = 0;
    private ImageView mMoreInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoreInfo = (ImageView)findViewById(R.id.more_info);
        mMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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
//                network start
//                getCurrentLocationPoint(ll);
//                network end
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
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET},
                1);
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
            //network start
//            getMyLocationNearbyAdvertisementBoardLocation();
//            network end

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

    //network start
    private List<LocationPoint> mLocationPoints;
    private String mCurrentUrl;
//    private void getMyLocationNearbyAdvertisementBoardLocation() {
//        Network.getLocationInfoApi().getLocationInfo(mCurrentLat, mCurrentLon)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<LocationPoint>>() {
//                    @Override
//                    public void accept(List<LocationPoint> locationPoints) throws Exception {
//                        mLocationPoints = locationPoints;
//                        showMyLocationNearbyAdvertisementBoardLocation(locationPoints);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(MainActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
    private LocationPoint getCurrentLocationPoint(LatLng ll) {
        LocationPoint lp = null;
        for (int i = 0; i < mLocationPoints.size(); i++) {
            if (mLocationPoints.get(i).getLatitude() == ll.latitude &&
                    mLocationPoints.get(i).getLongitude() == ll.longitude) {
                lp = mLocationPoints.get(i);
                mCurrentUrl = lp.getDetail_url();
            }
        }
        return lp;
    }
    private void showMyLocationNearbyAdvertisementBoardLocation(List<LocationPoint> locationPoints) {
        for(int i = 0; i < locationPoints.size(); i++) {
            LatLng llA = new LatLng(locationPoints.get(i).getLatitude(), locationPoints.get(i).getLongitude());
            MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA);
            mBaiduMap.addOverlay(ooA);
        }
    }
//    network end

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
        starLoc();
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
//        network start
//        mDialogContent = view;
//        setDialogAdvertisementBoardDetailInfo(mCurrentUrl);
//        network end
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
//                network start
//                Intent intent = new Intent(MainActivity.this, AdvertisementBoardDetailInfoActivity.class);
//                if (mCurrentAdvertisementBoardDetailInfo != null) {
//                    intent.putExtra(CURRENTADVERTISEMENTBOARDDETAILINFO, mCurrentAdvertisementBoardDetailInfo);
//                }
//                if (mCurrentUrl != null) {
//                    intent.putExtra(CURRENTADVERTISEMENTBOARDDETAILINFOURL, mCurrentUrl);
//                }
//                startActivity(intent);
//                network end
                startSubActivity(MainActivity.this, AdvertisementBoardDetailInfoActivity.class);
            }
        });
        mMainDialogPublish = (TextView) view.findViewById(R.id.main_dialog_publish);
        mMainDialogPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSubActivity(MainActivity.this, AdvancedOptionsActivity.class);
            }
        });
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        mDialog.show();
    }

//    network start
    private AdvertisementBoardDetailInfo mCurrentAdvertisementBoardDetailInfo;
    public static final String CURRENTADVERTISEMENTBOARDDETAILINFO = "CURRENTADVERTISEMENTBOARDDETAILINFO";
    public static final String CURRENTADVERTISEMENTBOARDDETAILINFOURL = "CURRENTADVERTISEMENTBOARDDETAILINFOURL";
    private View mDialogContent;
    private void setDialogAdvertisementBoardDetailInfo(String url) {
        Network.getAdvertisementBoardDetailInfoApi(url).getAdvertisementBoardDetailInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AdvertisementBoardDetailInfo>() {
                    @Override
                    public void accept(AdvertisementBoardDetailInfo advertisementBoardDetailInfo) throws Exception {
                        mCurrentAdvertisementBoardDetailInfo = advertisementBoardDetailInfo;
                        fillDialogContent();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
    private void fillDialogContent() {
        if (mCurrentAdvertisementBoardDetailInfo != null && mDialogContent != null) {
            ((TextView)mDialogContent.findViewById(R.id.price)).setText(mCurrentAdvertisementBoardDetailInfo.getPrice());
            Glide.with(mDialogContent.getContext()).load(mCurrentAdvertisementBoardDetailInfo.getPicture_url())
                    .into((ImageView)mDialogContent.findViewById(R.id.picture));
        }
    }
//    network end

    private void closeDialog() {
        mDialog.dismiss();
        mTarget = null;
        moveMapCenter(mLastTarget);
    }
}
