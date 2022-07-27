package fr.lleotraas.myapplication

class Utils {

    companion object {

        const val BUNDLE_STATE_TIME = "bundle_state_time"
        const val BUNDLE_STATE_INDEX = "bundle_state_index"
        const val BUNDLE_STATE_URI = "bundle_state_uri"

        fun convertTimeInPercent(seconds: Double): Int {
            return (seconds / 60.0 * 100.0).toInt()
        }

        fun getCityForRequest(time: Double): String {
           return when (time) {
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
               else -> {
                   ""
               }
            }
        }
    }
}