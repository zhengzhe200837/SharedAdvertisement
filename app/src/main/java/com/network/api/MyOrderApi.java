package com.network.api;

import com.network.model.MyOrderItemInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface MyOrderApi {
    @GET("/SharedAdvertisement/MyOrderServlet")
    Observable<List<MyOrderItemInfo>> getMyOrder();
}
