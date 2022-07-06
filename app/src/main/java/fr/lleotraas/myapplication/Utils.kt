package fr.lleotraas.myapplication

import android.graphics.drawable.Drawable

class Utils {

    companion object {
        fun convertKelvinToCelsius(temperature: Double): Double {
            return temperature - 273.15
        }

        fun convertTimeInPercent(seconds: Double): Int {
            return (seconds / 60.0 * 100.0).toInt()
        }

        fun getWeatherIcon(iconId: String): Int {
            return when(iconId) {
                "01d" -> { R.drawable.clear_sky }
                "02d" -> { R.drawable.few_cloud }
                "03d" -> { R.drawable.scattered_cloud }
                "04d" -> { R.drawable.broken_clouds }
                "09d" -> { R.drawable.shower_rain }
                "10d" -> { R.drawable.rain }
                "11d" -> { R.drawable.thunderstorm }
                "13d" -> { R.drawable.snow }
                else -> { R.drawable.mist }
            }
        }
    }
}