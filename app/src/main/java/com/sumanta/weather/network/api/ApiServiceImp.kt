package com.sumanta.weather.network.api

import com.sumanta.weather.model.City
import javax.inject.Inject

class ApiServiceImp
@Inject
constructor(private val apiService: ApiService) {
    suspend fun getCityData(city: String, appId: String) : City =
        apiService.getCityData(city,appId)
}