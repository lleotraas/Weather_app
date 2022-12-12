package fr.lleotraas.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import fr.lleotraas.myapplication.R
import fr.lleotraas.myapplication.databinding.ActivityWeatherBinding

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.activityWeatherToolbar.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val backArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        supportActionBar!!.setHomeAsUpIndicator(backArrow)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}