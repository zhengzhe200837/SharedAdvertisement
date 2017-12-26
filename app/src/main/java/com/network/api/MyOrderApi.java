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
import utils.Constants;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface MyOrderApi {
//    @GET(Constants.GET_MY_ORDER_NETWORK_INTERFACE)
//    Observable<List<MyOrderItemInfo>> getMyOrder();
    @POST(Constants.GET_MY_ORDER_NETWORK_INTERFACE)
    Observable<List<MyOrderItemInfo>> getMyOrder(@Body PostModelOfGetMyOrder body);
}
