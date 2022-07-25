package fr.lleotraas.myapplication

class Utils {

    companion object {

        fun convertKelvinToCelsius(temperature: Double): Double {
            return temperature - 273.15
        }

        fun convertTimeInPercent(seconds: Double): Int {
            return (seconds / 60.0 * 100.0).toInt()
        }

        fun getCityForRequest(time: Double): String {
            when (time) {
                0.0 -> {
                    return "rennes,35000,fr"
                }
                10.0 -> {
                    return "paris,fr"
                }
                20.0 -> {
                    return "nantes,fr"
                }
                30.0 -> {
                    return "bordeaux,fr"
                }
                40.0 -> {
                    return "lyon,69000,fr"
                }
            }
            return ""
        }
    }
}