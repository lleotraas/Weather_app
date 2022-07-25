package fr.lleotraas.myapplication.retrofit

import fr.lleotraas.myapplication.model.Weather
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeatherFrom(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Response<Weather>
}