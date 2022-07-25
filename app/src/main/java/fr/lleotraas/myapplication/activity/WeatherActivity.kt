package fr.lleotraas.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.lleotraas.myapplication.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}