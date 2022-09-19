package fr.lleotraas.myapplication.retrofit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    const val BASE_URL_WEATHER = "https://api.openweathermap.org/"
    private const val BASE_URL_WEATHER_ICON = "https://openweathermap.org/"

    private val weatherIconApi: IconApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER_ICON)
            .build()
            .create(IconApi::class.java)
    }

    fun getBitmapFrom(iconId: String, onComplete: (Bitmap?) -> Unit) {
        weatherIconApi.getWeatherIcon(iconId).enqueue(object : retrofit2.Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() == null || !response.isSuccessful || response.errorBody() != null) {
                    onComplete(null)
                    return
                }
                val bytes = response.body()!!.bytes()
                onComplete(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onComplete(null)
            }

        })
    }
}