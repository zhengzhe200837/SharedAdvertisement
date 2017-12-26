package com.network;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.network.api.AdvertisementBoardDetailInfoApi;
import com.network.api.DownloadVideoApi;
import com.network.api.GetSelectedPlayTimeSegmentApi;
import com.network.api.LocationInfoApi;
import com.network.api.MyOrderApi;
import com.network.api.UploadJustSelectedPlayTimeSegmentApi;
import com.network.api.UploadMyOrderInfoApi;
import com.network.api.UploadMyVideoApi;
import com.network.mapper.NetworkVideoResponseBodyMapper;
import com.network.model.SelectedPlayTimeSegment;
import com.network.model.UploadMyOrderInfo;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Constants;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public class Network {
    private static LocationInfoApi mLocationInfoApi;
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory mRxjavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static AdvertisementBoardDetailInfoApi mAdvertisementBoardDetailInfoApi;
    private static MyOrderApi mMyOrderApi;
    private static UploadMyVideoApi mUploadMyVideoApi;
    private static UploadMyOrderInfoApi mUploadMyOrderInfoApi;

    /**
     * 上传我的订单
     * @param orderInfo
     */
    public static void uploadMyOrderInfo(UploadMyOrderInfo orderInfo) {
        if (mUploadMyOrderInfoApi == null) {
            GsonBuilder gb = new GsonBuilder();
            Gson gson = gb.setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            mUploadMyOrderInfoApi = retrofit.create(UploadMyOrderInfoApi.class);
        }
        mUploadMyOrderInfoApi.uploadMyOrderInfo(orderInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        android.util.Log.d("zz", "Network + uploadMyOrderInfo + s = " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        android.util.Log.d("zz", "Network + uploadMyOrderInfo + error = " + throwable.toString());
                    }
                });
    }

    /**
     * 获取上传视频api对象
     * @return
     */
    private static UploadMyVideoApi getUploadMyVideoApi() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.setLenient().create();  //返回数据不是gson格式的处理方法

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new CreateInterceptor());
//        OkHttpClient okHttpClient = builder.build();  //错误码拦截器

        if (mUploadMyVideoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mUploadMyVideoApi = retrofit.create(UploadMyVideoApi.class);
        }
        return mUploadMyVideoApi;
    }

    /**
     * 上传视频文件
     * @param context
     * @param file
     * @param fileName
     */
    public static void uploadVideoFile(final Context context, File file, String fileName) {
        Toast.makeText(context, "开始上传", Toast.LENGTH_SHORT).show();
        //MediaType 为全部类型
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName,
                requestFile);
        getUploadMyVideoApi().uploadMyVideo(body)
//                .retryWhen(new CreateInterceptor.RetryWhen202Happen(3, 2000))  //重试
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        android.util.Log.d("zz", "Network + uploadVideoFile + s = " + s);
                        if ("success".equals(s)) {
                            Toast.makeText(context, "上传完成", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        android.util.Log.d("zz", "Network + uploadVideoFile + throwable = " + throwable.toString());
                    }
                });
    }

    /**
     * 获取我的订单api对象
     * @return
     */
    public static MyOrderApi getMyOrder() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5, TimeUnit.MINUTES)
//                .writeTimeout(5, TimeUnit.MINUTES)
//                .readTimeout(5, TimeUnit.MINUTES);
//        mOkHttpClient = builder.build();    //设置超时策略
        if (mMyOrderApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)  // http://192.168.31.233:8080   http://192.168.31.109:8080
                    .client(mOkHttpClient)
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mMyOrderApi = retrofit.create(MyOrderApi.class);
        }
        return mMyOrderApi;
    }

    /**
     * 获取广告牌位置信息api对象
     * @return
     */
    public static LocationInfoApi getLocationInfoApi() {
        if (mLocationInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(Constants.BASE_URL)  //服务器地址
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mLocationInfoApi = retrofit.create(LocationInfoApi.class);
        }
        return mLocationInfoApi;
    }

    /**
     * 获取广告牌详细信息api对象
     * @return
     */

    public static AdvertisementBoardDetailInfoApi getAdvertisementBoardDetailInfoApi() {
        if (mAdvertisementBoardDetailInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(Constants.BASE_URL)  // http://192.168.31.109:8080
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mAdvertisementBoardDetailInfoApi = retrofit.create(AdvertisementBoardDetailInfoApi.class);
        }
        return mAdvertisementBoardDetailInfoApi;
    }

    private static DownloadVideoApi mDownloadVideoApi;

    /**
     * 下载视频并保存到本地
     */
    public static void downloadVideo() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.setLenient().create();
        if (mDownloadVideoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.31.109:8080")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .client(mOkHttpClient)
                    .build();
            mDownloadVideoApi = retrofit.create(DownloadVideoApi.class);
        }
        mDownloadVideoApi.downVideo("http://192.168.31.109:8080/SharedAdvertisement/Video1.mp4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new NetworkVideoResponseBodyMapper())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        android.util.Log.d("zz", "Network + downloadVideo() + s = " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        android.util.Log.d("zz", "Network + downloadVideo() + error = " + throwable.toString());
                    }
                });
    }

    private static GetSelectedPlayTimeSegmentApi mGetSelectedPlayTimeSegmentApi;

    /**
     * 获取已经被选择的播放时间片段api对象
     * @return
     */
    public static GetSelectedPlayTimeSegmentApi getSelectedPlayTimeSegment() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.setLenient().create();
        if (mGetSelectedPlayTimeSegmentApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mGetSelectedPlayTimeSegmentApi = retrofit.create(GetSelectedPlayTimeSegmentApi.class);
        }
        return mGetSelectedPlayTimeSegmentApi;
    }

    private static UploadJustSelectedPlayTimeSegmentApi mUploadJustSelectedPlayTimeSegmentApi;

    /**
     * 上传移动端选择的播放时间片段
     * @param justSelectedPlayTimeSegment
     */
    public static void uploadJustSelectedPlayTimeSegment(List<SelectedPlayTimeSegment> justSelectedPlayTimeSegment) {
        if (mUploadJustSelectedPlayTimeSegmentApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.31.109:8080")
                    .client(mOkHttpClient)
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mUploadJustSelectedPlayTimeSegmentApi = retrofit.create(UploadJustSelectedPlayTimeSegmentApi.class);
        }
        mUploadJustSelectedPlayTimeSegmentApi.uploadJustSelectedPlayTimeSegment(justSelectedPlayTimeSegment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        android.util.Log.d("zz", "Network + uploadJustSelectedPlayTimeSegment() + s = " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        android.util.Log.d("zz", "Network + uploadJustSelectedPlayTimeSegment() + error = " + throwable.toString());
                    }
                });

    }
}
