package com.network.api;

import com.network.model.UploadMyOrderInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import utils.Constants;

/**
 * Created by zhengzhe on 2017/12/12.
 */

public interface UploadMyOrderInfoApi {
    @POST(Constants.UPLOAD_MY_ORDER_INFO_NETWORK_INTERFACE)
    Observable<String> uploadMyOrderInfo(@Body UploadMyOrderInfo orderInfo);
}
