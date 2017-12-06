package com.network.api;

import com.network.model.AdvertisementBoardDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface AdvertisementBoardDetailInfoApi {
    @GET("")
    Observable<AdvertisementBoardDetailInfo> getAdvertisementBoardDetailInfo();
}
