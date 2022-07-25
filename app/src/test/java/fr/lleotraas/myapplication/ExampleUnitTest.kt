package fr.lleotraas.myapplication

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun temperature_isCorrect() {
        val temperatureExpected = 37.5
        val temperatureToTest = 310.65
        assertEquals(temperatureExpected, Utils.convertKelvinToCelsius(temperatureToTest), 0.10)
    }

    @Test
    fun convertTimeInPercent_isCorrect() {
        val percentExpected = 75
        val timeToTest = 45.0
        assertEquals(percentExpected, Utils.convertTimeInPercent(timeToTest))
    }

    @Test
    fun getWeatherIcon_isCorrect() {

    }

    @Test
    fun getCityForRequest_isCorrect() {
        var cityExpected = "rennes,35000,fr"
        var timeToTest = 0.0

        assertEquals(cityExpected, Utils.getCityForRequest(timeToTest))

        cityExpected = "paris,fr"
        timeToTest = 10.0

        assertEquals(cityExpected, Utils.getCityForRequest(timeToTest))

        cityExpected = "nantes,fr"
        timeToTest = 20.0

        assertEquals(cityExpected, Utils.getCityForRequest(timeToTest))

        cityExpected = "bordeaux,fr"
        timeToTest = 30.0

        assertEquals(cityExpected, Utils.getCityForRequest(timeToTest))

        cityExpected = "lyon,69000,fr"
        timeToTest = 40.0

        assertEquals(cityExpected, Utils.getCityForRequest(timeToTest))

        cityExpected = ""
        timeToTest = 50.0

        assertEquals(cityExpected, Utils.getCityForRequest(timeToTest))
    }
}