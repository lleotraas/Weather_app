package fr.lleotraas.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.lleotraas.myapplication.repository.WeatherRepository
import fr.lleotraas.myapplication.retrofit.RetrofitInstance
import fr.lleotraas.myapplication.utils_instrumented_test.CITY
import fr.lleotraas.myapplication.utils_instrumented_test.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        weatherRepository = WeatherRepository(RetrofitInstance.weatherApi)
        viewModel = WeatherViewModel(weatherRepository)
    }

    @Test
    @Throws(Exception::class)
    fun addWeatherToListAndGetList() = runBlocking {
        viewModel.addWeatherToList(CITY)
        val listExpected = viewModel.getWeatherList().getOrAwaitValue()
        assertEquals(1, listExpected.size)
    }

    @Test
    @Throws(Exception::class)
    fun clearWeatherList() = runBlocking {
        viewModel.addWeatherToList(CITY)
        var listExpected = viewModel.getWeatherList().getOrAwaitValue()
        assertEquals(1, listExpected.size)
        viewModel.clearWeatherList()
        listExpected = viewModel.getWeatherList().getOrAwaitValue()
        assertEquals(0, listExpected.size)
    }
}