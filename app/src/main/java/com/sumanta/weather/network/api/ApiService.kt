package com.sumanta.weather.network.api

import com.sumanta.weather.model.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCityData(
        @Query("q") q: String,
        @Query("appid") appid: String
    ):City
}