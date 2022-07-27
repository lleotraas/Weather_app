package fr.lleotraas.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.lleotraas.myapplication.repository.WeatherRepository

class ViewModelFactory(
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}