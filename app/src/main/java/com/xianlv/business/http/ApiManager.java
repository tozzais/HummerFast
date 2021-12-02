package com.xianlv.business.http;



import com.xianlv.business.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager mInstance;
    private ApiService mApiService;

    private static ApiManager weatherInstance;
    private ApiService weatherService;


    public ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtils.getInstance())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }
    public static ApiService getService() {
        if (mInstance == null) {
            mInstance = new ApiManager();
        }
        return mInstance.mApiService;
    }

    public ApiManager(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtils.getInstance())
                .build();
        weatherService = retrofit.create(ApiService.class);
    }
    public static ApiService getService1() {
        if (weatherInstance == null) {
            weatherInstance = new ApiManager(HttpUrl.weather_url);
        }
        return weatherInstance.weatherService;
    }


}
