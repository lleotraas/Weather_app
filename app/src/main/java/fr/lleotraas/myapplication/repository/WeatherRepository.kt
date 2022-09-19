package fr.lleotraas.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.retrofit.WeatherApi
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor (
    val api: WeatherApi
    ) {

    private val weatherList = MutableLiveData<ArrayList<Weather>>()

    fun addWeatherToList(response: Response<Weather>) {
        if (response.isSuccessful && response.body() != null) {
            val list = weatherList.value ?: ArrayList()
            list.add(response.body()!!)
            println("weather size:${response.body()!!.name} weather icon:${response.body()!!.weather[0].icon}")
            weatherList.postValue(list)
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