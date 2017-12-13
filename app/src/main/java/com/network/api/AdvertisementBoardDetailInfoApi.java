package com.network.api;

import com.network.model.AdvertisementBoardDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface AdvertisementBoardDetailInfoApi {
//    @GET("/simpleDemo/HandleDataBaseServlet")
//    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo(@Query("tableName") String tableName, @Query("todo") String todo);
    @GET("/SharedAdvertisement/SharedAdvertisement")
    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo();
}
