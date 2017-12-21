package com.network.api;

import com.network.model.PostModelOfGetSelectedPlayTimeSegment;
import com.network.model.SelectedPlayTimeSegment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhengzhe on 2017/12/19.
 */

public interface GetSelectedPlayTimeSegmentApi {
//    @GET("/SharedAdvertisement/GetSelectedPlayTimeSegmentServlet")
//    Observable<List<SelectedPlayTimeSegment>> getSelectedPlayTimeSegment(@Query("year") String year, @Query("month") String month, @Query("day") String day);
    @POST("/SharedAdvertisement/GetSelectedPlayTimeSegmentServlet")
    Observable<List<SelectedPlayTimeSegment>> getSelectedPlayTimeSegment(@Body PostModelOfGetSelectedPlayTimeSegment body);
}
