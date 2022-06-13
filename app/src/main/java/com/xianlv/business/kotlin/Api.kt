package com.xianlv.business.kotlin

import com.xianlv.business.bean.request.RequestCode
import com.xianlv.business.http.BaseResult
import com.xianlv.business.http.HttpUrl
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import rx.Observable

interface Api {

    @POST(HttpUrl.get_code)
    fun getCode(@Query("query") query: String): Call<PlaceResponse>

}