package fr.lleotraas.myapplication.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IconApi {

    @GET("img/wn/{icon}@2x.png")
    fun getWeatherIcon(
        @Path("icon") iconId: String
    ): Call<ResponseBody>

}