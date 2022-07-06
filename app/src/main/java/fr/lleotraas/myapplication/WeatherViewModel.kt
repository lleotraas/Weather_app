package fr.lleotraas.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.retrofit.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel(
    private val api: WeatherApi) : ViewModel() {

    private val weatherList = MutableLiveData<ArrayList<Weather>>()

    fun addWeatherToList(cityName: String) {

        viewModelScope.launch {
            val response = try {
                api.getCurrentWeatherFrom(
                    cityName,
                    BuildConfig.API_KEY
                )
            } catch (exception: IOException) {
                Log.e("MainViewModel", "getWeather: you may have internet connection, ${exception.message}", )
                return@launch
            } catch (exception: HttpException) {
                Log.e("MainViewModel", "getWeather: HttpException, unexpected response ${exception.message}", )
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
}