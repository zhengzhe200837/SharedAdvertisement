package com.network.api;

import com.network.model.MyOrderItemInfo;
import com.network.model.PostModelOfGetMyOrder;
import com.network.model.VideoUrl;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface MyOrderApi {
//    @GET("/SharedAdvertisement/MyOrderServlet")
//    Observable<List<MyOrderItemInfo>> getMyOrder();
    @POST("/SharedAdvertisement/MyOrderServlet")
    Observable<List<MyOrderItemInfo>> getMyOrder(@Body PostModelOfGetMyOrder body);
}
