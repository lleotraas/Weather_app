package fr.lleotraas.myapplication.dependencies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import fr.lleotraas.myapplication.repository.WeatherRepository
import fr.lleotraas.myapplication.retrofit.RetrofitInstance
@HiltAndroidApp
class WeatherApplication : Application()
