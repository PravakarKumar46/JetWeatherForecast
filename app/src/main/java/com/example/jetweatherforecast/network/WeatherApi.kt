package com.example.jetweatherforecast.network

import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    /**
     *  weather api
     *  BASE_URL = "https://api.openweathermap.org/"
     */
    @GET(value = "/data/2.5/weather")
    suspend fun getWeather(
        @Query(value = "q") query: String,
        @Query(value = "units") units: String = "metric",
        @Query(value = "appid") appid: String = Constants.API_KEY
    ): Weather

}