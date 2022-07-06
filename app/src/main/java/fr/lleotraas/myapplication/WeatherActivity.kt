package fr.lleotraas.myapplication

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.lleotraas.myapplication.Utils.Companion.convertTimeInPercent
import fr.lleotraas.myapplication.databinding.ActivityWeatherBinding
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.retrofit.RetrofitInstance

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = -1.0
    private lateinit var listOfMessage: Array<String>
    private var index = 0
    private val viewModel = WeatherViewModel(RetrofitInstance.weatherApi)
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureListeners()
        adapter = WeatherAdapter()
        listOfMessage = resources.getStringArray(R.array.sentence_array)
        serviceIntent = Intent(applicationContext, TimeService::class.java)
        setupRecyclerView()
        registerReceiver(updateTime, IntentFilter(TimeService.TIMER_UPDATED))
        startTimer()
        configureListeners()
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimeService.TIME_EXTRA, 0.0)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.activityWeatherProgressBar.setProgress(convertTimeInPercent(time), true)
            } else {
                binding.activityWeatherProgressBar.progress = convertTimeInPercent(time)
            }
            binding.activityWeatherChronoTv.text = "${convertTimeInPercent(time)}%"
            event(time)
            stopTimer()
        }
    }

    private fun event(time: Double) {
        when (time) {
            0.0 -> {
                viewModel.addWeatherToList("rennes,35000,fr")
            }
            10.0 -> {
                viewModel.addWeatherToList("paris,fr")
            }
            20.0 -> {
                viewModel.addWeatherToList("nantes,fr")
            }
            30.0 -> {
                viewModel.addWeatherToList("bordeaux,fr")
            }
            40.0 -> {
                viewModel.addWeatherToList("lyon,69000,fr")
            }

            60.0 -> {
                binding.apply {
                    activityWeatherWeatherRv.visibility = View.VISIBLE
                    activityWeatherAgainBtn.visibility = View.VISIBLE
                    activityWeatherSentenceTv.visibility = View.GONE
                    activityWeatherChronoTv.visibility = View.GONE
                    activityWeatherProgressBar.visibility = View.GONE
                }


                viewModel.getWeatherList().observe(this) {
                    loadWeatherFromRecyclerView(it)
                }
            }
        }

        if (time.toInt() % 6 == 0) {
            binding.activityWeatherSentenceTv.text = listOfMessage[index]
            index++
            if (index == 3) {
                index = 0
            }
        }

    }



    private fun configureListeners() {
        binding.apply {
            activityWeatherAgainBtn.setOnClickListener {
                activityWeatherWeatherRv.visibility = View.GONE
                activityWeatherAgainBtn.visibility = View.GONE
                activityWeatherSentenceTv.visibility = View.VISIBLE
                activityWeatherChronoTv.visibility = View.VISIBLE
                activityWeatherProgressBar.visibility = View.VISIBLE
                viewModel.clearWeatherList()
                startTimer()
            }
        }

    }

    private fun startTimer() {
        serviceIntent.putExtra(TimeService.TIME_EXTRA, time)
        startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        if (time == 60.0) {
            stopService(serviceIntent)
            timerStarted = false
            time = -1.0
        }
    }

    private fun setupRecyclerView() = binding.activityWeatherWeatherRv.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun loadWeatherFromRecyclerView(listOfWeather: List<Weather>) {
        adapter.submitList(listOfWeather)
        binding.activityWeatherWeatherRv.adapter = adapter
    }

}