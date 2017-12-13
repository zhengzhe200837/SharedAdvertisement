package com.network.api;

import com.network.model.UploadMyOrderInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zhengzhe on 2017/12/12.
 */

public interface UploadMyOrderInfoApi {
//    @POST("/SharedAdvertisement/SharedAdvertisement")
//    Observable<String> uploadMyOrderInfo(@Body UploadMyOrderInfo orderInfo);
    @POST("/simpleDemo/HandleDataBaseServlet")
    Observable<String> uploadMyOrderInfo(@Body UploadMyOrderInfo orderInfo);
}
