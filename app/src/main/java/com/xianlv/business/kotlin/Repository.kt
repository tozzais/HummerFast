package com.xianlv.business.kotlin

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun serach(string: String) = liveData(Dispatchers.IO) {
        val result = try {
            val response = SunnyWeatherNetwork.search(string)
            if (response.status === "OK"){
                Result.success(response.place)
            }else{
                Result.failure(RuntimeException("失败${response.status}"))
            }
        }catch (ex:Exception){
            Result.failure(ex)
        }
        emit(result)
    }
}