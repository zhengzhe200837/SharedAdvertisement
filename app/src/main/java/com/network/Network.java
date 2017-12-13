package com.network;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.network.ExceptionInterceptor.CreateInterceptor;
import com.network.api.AdvertisementBoardDetailInfoApi;
import com.network.api.LocationInfoApi;
import com.network.api.MyOrderApi;
import com.network.api.UploadMyOrderInfoApi;
import com.network.api.UploadMyVideoApi;
import com.network.model.UploadMyOrderInfo;
import com.network.model.VideoUrl;

import java.io.File;

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

    public static void uploadMyOrderInfo(UploadMyOrderInfo orderInfo) {
        if (mUploadMyOrderInfoApi == null) {
            GsonBuilder gb = new GsonBuilder();
            Gson gson = gb.setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.31.109:8080")  // http://192.168.31.233:8080/simpleDemo/serv
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

    private static UploadMyVideoApi getUploadMyVideoApi() {
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.setLenient().create();  //返回数据不是gson格式的处理方法

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new CreateInterceptor());
//        OkHttpClient okHttpClient = builder.build();  //错误码拦截器

        if (mUploadMyVideoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.31.109:8080")
                    .client(mOkHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mUploadMyVideoApi = retrofit.create(UploadMyVideoApi.class);
        }
        return mUploadMyVideoApi;
    }

    public static void uploadVideoFile(final Context context, File file) {
        Toast.makeText(context, "开始上传", Toast.LENGTH_SHORT).show();
        //MediaType 为全部类型
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
                requestFile);
//        getUploadMyVideoApi().uploadMyVideo(body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<UploadMyVideoResult>() {
//                    @Override
//                    public void accept(UploadMyVideoResult uploadMyVideoResult) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
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

    public static MyOrderApi getMyOrder() {
        if (mMyOrderApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.31.109:8080")
                    .client(mOkHttpClient)
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mMyOrderApi = retrofit.create(MyOrderApi.class);
        }
        return mMyOrderApi;
    }

    public static LocationInfoApi getLocationInfoApi() {
        if (mLocationInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl("http://192.168.31.109:8080")  //服务器地址
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mLocationInfoApi = retrofit.create(LocationInfoApi.class);
        }
        return mLocationInfoApi;
    }

    public static AdvertisementBoardDetailInfoApi getAdvertisementBoardDetailInfoApi() {
        if (mAdvertisementBoardDetailInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl("http://192.168.31.109:8080")  //http://192.168.31.109:8080  http://192.168.31.233:8080/simpleDemo/HandleDataBaseServlet
                    .addConverterFactory(mGsonConverterFactory)
                    .addCallAdapterFactory(mRxjavaCallAdapterFactory)
                    .build();
            mAdvertisementBoardDetailInfoApi = retrofit.create(AdvertisementBoardDetailInfoApi.class);
        }
        return mAdvertisementBoardDetailInfoApi;
    }
}
