package fr.lleotraas.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.lleotraas.myapplication.BuildConfig.API_KEY
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.repository.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor (
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun addWeatherToList(cityName:String) {
        viewModelScope.launch {
            val response = try {

                weatherRepository.api.getCurrentWeatherFrom(
                    cityName,
                    "metric",
                    API_KEY
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