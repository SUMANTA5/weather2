package com.sumanta.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.sumanta.weather.databinding.ActivityMainBinding
import com.sumanta.weather.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherViewModel.getCityData()
        initListener()
        weatherViewModel.weatherResponse.observe(this, Observer { response ->

            Log.d("Kolkata", response.name)

            if (response.weather.description == "clear sky" ||
                response.weather.description == "mist") {
                Glide.with(this)
                    .load(R.drawable.clouds)
                    .into(binding.image)
            } else
                if (response.weather.description == "haze" ||
                    response.weather.description == "overcast clouds" ||
                    response.weather.description == "fog") {
                    Glide.with(this)
                        .load(R.drawable.haze)
                        .into(binding.image)
                } else
                    if (response.weather.description == "rain") {
                        Glide.with(this)
                            .load(R.drawable.rain)
                            .into(binding.image)
                    }
            binding.description.text = response.weather.description
            binding.name.text = response.name
            binding.degree.text = response.wind.deg.toString()
            binding.speed.text = response.wind.speed.toString()
            binding.temp.text = response.main.temp.toString()
            binding.humidity.text = response.main.humidity.toString()
        })
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    private fun initListener()
    {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { weatherViewModel.setSearchQuery(it) }
                Log.d("main", "onQueryTextChange: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })
    }
}