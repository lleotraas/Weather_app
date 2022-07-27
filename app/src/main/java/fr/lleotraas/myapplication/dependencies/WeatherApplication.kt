package fr.lleotraas.myapplication.dependencies

import android.app.Application
import fr.lleotraas.myapplication.repository.WeatherRepository
import fr.lleotraas.myapplication.retrofit.RetrofitInstance

class WeatherApplication : Application() {
    private val retrofitInstance by lazy { RetrofitInstance.weatherApi }
    val weatherRepository by lazy { WeatherRepository(retrofitInstance) }
}