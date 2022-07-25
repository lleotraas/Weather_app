package fr.lleotraas.myapplication.fragment

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.lleotraas.myapplication.R
import fr.lleotraas.myapplication.Utils
import fr.lleotraas.myapplication.WeatherAdapter
import fr.lleotraas.myapplication.WeatherViewModel
import fr.lleotraas.myapplication.databinding.FragmentMainBinding
import fr.lleotraas.myapplication.databinding.FragmentWeatherBinding
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.retrofit.RetrofitInstance
import fr.lleotraas.myapplication.service.TimeService

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = -1.0
    private lateinit var listOfMessage: Array<String>
    private var index = 0
    private val viewModel = WeatherViewModel(RetrofitInstance.weatherApi)
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        configureListener()
        adapter = WeatherAdapter()
        listOfMessage = resources.getStringArray(R.array.sentence_array)
        serviceIntent = Intent(requireContext(), TimeService::class.java)
        setupRecyclerView()
        requireActivity().registerReceiver(updateTime, IntentFilter(TimeService.TIMER_UPDATED))
        startTimer()
        configureListeners()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun configureListener() {

    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimeService.TIME_EXTRA, 0.0)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.fragmentWeatherProgressBar.setProgress(Utils.convertTimeInPercent(time), true)
            } else {
                binding.fragmentWeatherProgressBar.progress = Utils.convertTimeInPercent(time)
            }
            binding.fragmentWeatherChronoTv.text = "${Utils.convertTimeInPercent(time)}%"
            event(time)
            stopTimer()
        }
    }

    private fun event(time: Double) {

        viewModel.addWeatherToList(Utils.getCityForRequest(time))

        if (time == 60.0) {
            binding.apply {
                showStopUi()
            }
            viewModel.getWeatherList().observe(this) {
                loadWeatherFromRecyclerView(it)
            }
        }

        if (time.toInt() % 6 == 0) {
            binding.fragmentWeatherSentenceTv.text = listOfMessage[index]
            index++
            if (index == 3) {
                index = 0
            }
        }
    }

    private fun configureListeners() {
        binding.apply {
            fragmentWeatherAgainBtn.setOnClickListener {
                showStartUi()
                viewModel.clearWeatherList()
                startTimer()
            }
        }

    }

    private fun startTimer() {
        serviceIntent.putExtra(TimeService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        if (time == 60.0) {
            requireActivity().stopService(serviceIntent)
            timerStarted = false
            time = -1.0
        }
    }

    private fun showStartUi() {
        binding.apply {
            fragmentWeatherWeatherRv.visibility = View.GONE
            fragmentWeatherAgainBtn.visibility = View.GONE
            fragmentWeatherSentenceTv.visibility = View.VISIBLE
            fragmentWeatherChronoTv.visibility = View.VISIBLE
            fragmentWeatherProgressBar.visibility = View.VISIBLE
        }
    }

    private fun showStopUi() {
        binding.apply {
            fragmentWeatherWeatherRv.visibility = View.VISIBLE
            fragmentWeatherAgainBtn.visibility = View.VISIBLE
            fragmentWeatherSentenceTv.visibility = View.GONE
            fragmentWeatherChronoTv.visibility = View.GONE
            fragmentWeatherProgressBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() = binding.fragmentWeatherWeatherRv.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun loadWeatherFromRecyclerView(listOfWeather: List<Weather>) {
        adapter.submitList(listOfWeather)
        binding.fragmentWeatherWeatherRv.adapter = adapter
    }
}