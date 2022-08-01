package fr.lleotraas.myapplication

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun convertTimeInPercent_isCorrect() {
        val percentExpected = 75
        val timeToTest = 45.0
        assertEquals(percentExpected, convertTimeInPercent(timeToTest))
    }

    @Test
    fun getCityForRequest_isCorrect() {
        var cityExpected = "rennes,35000,fr"
        var timeToTest = 0.0

        assertEquals(cityExpected, getCityForRequest(timeToTest))

        cityExpected = "paris,fr"
        timeToTest = 10.0

        assertEquals(cityExpected, getCityForRequest(timeToTest))

        cityExpected = "nantes,fr"
        timeToTest = 20.0

        assertEquals(cityExpected, getCityForRequest(timeToTest))

        cityExpected = "bordeaux,fr"
        timeToTest = 30.0

        assertEquals(cityExpected, getCityForRequest(timeToTest))

        cityExpected = "lyon,69000,fr"
        timeToTest = 40.0

        assertEquals(cityExpected, getCityForRequest(timeToTest))

        cityExpected = ""
        timeToTest = 50.0

        assertEquals(cityExpected, getCityForRequest(timeToTest))
    }
}