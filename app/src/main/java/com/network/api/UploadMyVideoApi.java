package com.network.api;

import com.network.model.UploadMyVideoResult;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface UploadMyVideoApi {
    @Multipart
    @POST("/SharedAdvertisement/UploadVideoServlet")
//    Observable<UploadMyVideoResult> uploadMyVideo(@Part MultipartBody.Part videoFile);
    Observable<String> uploadMyVideo(@Part MultipartBody.Part videoFile);
}
