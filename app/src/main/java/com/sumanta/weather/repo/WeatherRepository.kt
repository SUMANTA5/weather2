package com.sumanta.weather.repo

import com.sumanta.weather.model.City
import com.sumanta.weather.network.api.ApiServiceImp
import com.sumanta.weather.network.constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository
@Inject
constructor(private val apiServiceImp: ApiServiceImp){

    fun getCityData(city: String): Flow<City> = flow {
        val response = apiServiceImp.getCityData(city,constants.API_KYE)
        emit(response)
    }.flowOn(Dispatchers.IO)
        .conflate()
}