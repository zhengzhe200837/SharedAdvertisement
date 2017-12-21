package com.network.api;

import com.network.model.AdvertisementBoardDetailInfo;
import com.network.model.PostModelOfGetBillBoardDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface AdvertisementBoardDetailInfoApi {
    @POST("/SharedAdvertisement/GetBillBoardDetailInfoServlet")
    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo(@Body PostModelOfGetBillBoardDetailInfo body);
//    @GET("/simpleDemo/HandleDataBaseServlet")
//    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo(@Query("tableName") String tableName, @Query("todo") String todo);
//    @GET("/SharedAdvertisement/SharedAdvertisement")
//    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo();
}
