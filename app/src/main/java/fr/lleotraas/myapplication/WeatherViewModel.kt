package fr.lleotraas.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.repository.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun addWeatherToList(cityName:String) {
        viewModelScope.launch {
            val response = try {

                weatherRepository.api.getCurrentWeatherFrom(
                    cityName,
                    "metric",
                    BuildConfig.API_KEY
                )
            } catch (exception: IOException) {
                Log.e(
                    "MainViewModel",
                    "getWeather: you may have internet connection, ${exception.message}"
                )
                return@launch
            } catch (exception: HttpException) {
                Log.e(
                    "MainViewModel",
                    "getWeather: HttpException, unexpected response ${exception.message}"
                )
                return@launch
            }
            weatherRepository.addWeatherToList(response)
        }
    }

    fun getWeatherList(): LiveData<ArrayList<Weather>> {
        return weatherRepository.getWeatherList()
    }

    fun clearWeatherList() {
        weatherRepository.clearWeatherList()
    }
}