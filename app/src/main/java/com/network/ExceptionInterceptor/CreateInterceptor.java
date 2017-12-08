package com.network.ExceptionInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by zhengzhe on 2017/12/7.
 * 当放回错误状态码（非200）时，重新请求服务器
 */

public class CreateInterceptor implements Interceptor {
    private static final int HTTP_CODE_ACCEPT = 202;    //服务器已接受请求，但尚未处理

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        android.util.Log.d("zz", "CreateInterceptor + intercept + code = " + response.code());
        if (response.code() == HTTP_CODE_ACCEPT) {
            CreateInterceptorExceptioin interceptorExceptioin = new CreateInterceptorExceptioin();
            throw  interceptorExceptioin;
        }
        return response;
    }

    public class CreateInterceptorExceptioin extends Error{
        private int errorCode;
        private String retry_after;

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getRetry_after() {
            return retry_after;
        }

        public void setRetry_after(String retry_after) {
            this.retry_after = retry_after;
        }
    }

    public static final class RetryWhen202Happen implements Function<Observable<? extends Throwable>, Observable<?>> {

        private final int _maxRetries;
        private final int _retryDelayMillis;
        private       int _retryCount;

        public RetryWhen202Happen(final int maxRetries, final int retryDelayMillis) {
            _maxRetries = maxRetries;
            _retryDelayMillis = retryDelayMillis;
            _retryCount = 0;
        }

        @Override
        public Observable<?> apply(Observable<? extends Throwable> inputObservable) {
            android.util.Log.d("zz", "RetryWhen202Happen + apply");
            return inputObservable.flatMap(new Function<Throwable, Observable<?>>() {
                @Override
                public Observable<?> apply(Throwable throwable) {
                    android.util.Log.d("zz", "flatMap + _retryCount = " + _retryCount);
                    android.util.Log.d("zz", "flatMap + boolean = " + String.valueOf(throwable instanceof CreateInterceptor.CreateInterceptorExceptioin));
                    if (++_retryCount < _maxRetries && throwable instanceof CreateInterceptor.CreateInterceptorExceptioin) {
                        // When this Observable calls onNext, the original
                        // Observable will be retried (i.e. re-subscribed)
                        return Observable.timer(_retryCount * _retryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(throwable);
                }
            });
        }
    }
}
