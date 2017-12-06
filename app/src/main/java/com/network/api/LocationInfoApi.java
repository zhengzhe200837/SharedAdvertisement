package com.network.api;

import com.network.model.LocationPoint;
import com.network.model.VideoUrl;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface LocationInfoApi {
    @GET("")   //信息接口
    Observable<List<LocationPoint>> getLocationInfo(@Query("latitude") double latitude, @Query("longitude") double longitude);  //查询参数、路径  上传我的定位信息
}
