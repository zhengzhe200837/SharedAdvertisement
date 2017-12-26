package com.network.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import utils.Constants;

/**
 * Created by zhengzhe on 2017/11/30.
 */

public interface UploadMyVideoApi {
    @Multipart
    @POST(Constants.UPLOAD_VIDEO_FILE_NETWORK_INTERFACE)
    Observable<String> uploadMyVideo(@Part MultipartBody.Part videoFile);
}
