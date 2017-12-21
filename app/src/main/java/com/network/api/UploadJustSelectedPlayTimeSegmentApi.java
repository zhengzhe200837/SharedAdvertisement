package com.network.api;

import com.network.model.SelectedPlayTimeSegment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zhengzhe on 2017/12/19.
 */

public interface UploadJustSelectedPlayTimeSegmentApi {
    @POST()
    Observable<String> uploadJustSelectedPlayTimeSegment(@Body List<SelectedPlayTimeSegment> justSelectedPlayTimeSegment);
}
