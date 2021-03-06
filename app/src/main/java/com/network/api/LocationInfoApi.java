package com.network.api;

import com.network.model.AdvertisementBoardDetailInfo;
import com.network.model.LocationPoint;
import com.network.model.PostModelOfGetLocationInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import utils.Constants;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface LocationInfoApi {
//    @GET(Constants.GET_LOCATION_INFO_NETWORK_INTERFACE)   //信息接口
//    Observable<List<LocationPoint>> getLocationInfo(@Query("latitude") double latitude, @Query("longitude") double longitude);  //查询参数、路径  上传我的定位信息
    @POST(Constants.GET_LOCATION_INFO_NETWORK_INTERFACE)
    Observable<List<LocationPoint>> getLocationInfo(@Body PostModelOfGetLocationInfo body);
}
