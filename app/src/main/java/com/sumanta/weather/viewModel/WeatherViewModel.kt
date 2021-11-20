package com.sumanta.weather.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumanta.weather.model.City
import com.sumanta.weather.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val weatherRepository: WeatherRepository) : ViewModel() {
    val weatherResponse:MutableLiveData<City> = MutableLiveData()
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    private val searchChannel = ConflatedBroadcastChannel<String>()

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun setSearchQuery(search:String)
    {
        searchChannel.offer(search)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getCityData()
    {
        viewModelScope.launch {
            searchChannel.asFlow()
                .flatMapLatest { search->
                    weatherRepository.getCityData(search)
                }.catch {e->
                    Log.d("main", "${e.message}")
                }.collect { response->
                    weatherResponse.value=response
                }
        }
    }




}