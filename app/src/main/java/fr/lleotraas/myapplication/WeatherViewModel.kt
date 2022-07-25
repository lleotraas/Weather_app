package fr.lleotraas.myapplication

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.retrofit.RetrofitInstance
import fr.lleotraas.myapplication.retrofit.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Body
import java.io.IOException

class WeatherViewModel(
    private val api: WeatherApi) : ViewModel() {

    private val weatherList = MutableLiveData<ArrayList<Weather>>()

    fun addWeatherToList(cityName: String) {

        viewModelScope.launch {
            val response = try {
                api.getCurrentWeatherFrom(
                    cityName,
                    "metric",
                    BuildConfig.API_KEY
                )
            } catch (exception: IOException) {
                Log.e("MainViewModel", "getWeather: you may have internet connection, ${exception.message}")
                return@launch
            } catch (exception: HttpException) {
                Log.e("MainViewModel", "getWeather: HttpException, unexpected response ${exception.message}")
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                var list = ArrayList<Weather>()
                if (weatherList.value != null) {
                    list = weatherList.value!!
                }
                list.add(response.body()!!)
                println("weather size:${response.body()!!.name} weather icon:${response.body()!!.weather[0].icon}")
                weatherList.postValue(list)
            }

        }
    }

    fun getWeatherList(): LiveData<ArrayList<Weather>> {
        return weatherList
    }

    fun clearWeatherList() {
        if (weatherList.value != null) {
            weatherList.value!!.clear()
        }
    }

    fun getWeatherIcon(iconId: String, view: View, icon: ImageView) {
        RetrofitInstance.getBitmapFrom(iconId) {
            Glide.with(view)
                .load(it)
                .into(icon)
        }
    }
}