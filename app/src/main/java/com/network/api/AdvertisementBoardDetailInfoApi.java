package com.network.api;

import com.network.model.AdvertisementBoardDetailInfo;
import com.network.model.PostModelOfGetBillBoardDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import utils.Constants;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface AdvertisementBoardDetailInfoApi {
    @POST(Constants.GET_ADVERTISEMENT_BOARD_DETAIL_INFO_NETWORK_INTERFACE)
    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo(@Body PostModelOfGetBillBoardDetailInfo body);
//    @GET(Constants.GET_ADVERTISEMENT_BOARD_DETAIL_INFO_NETWORK_INTERFACE)
//    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo(@Query("tableName") String tableName, @Query("todo") String todo);
}
