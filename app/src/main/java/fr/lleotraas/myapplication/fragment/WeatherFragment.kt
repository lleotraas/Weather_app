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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.lleotraas.myapplication.*
import fr.lleotraas.myapplication.Utils.Companion.BUNDLE_STATE_INDEX
import fr.lleotraas.myapplication.Utils.Companion.BUNDLE_STATE_TIME
import fr.lleotraas.myapplication.databinding.FragmentWeatherBinding
import fr.lleotraas.myapplication.dependencies.WeatherApplication
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.service.TimeService
import fr.lleotraas.myapplication.service.TimeService.Companion.TIME_EXTRA

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private var serviceIntent: Intent? = null
    private var time = -1.0
    private lateinit var listOfMessage: Array<String>
    private var index = 0
    private lateinit var adapter: WeatherAdapter
    private var broadcastTimer: Intent? = null
    private val viewModel: WeatherViewModel by viewModels {
        ViewModelFactory(
            (requireActivity().application as WeatherApplication).weatherRepository
        )
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        configureListeners()
        initVariables()
        checkSavedInstance(savedInstanceState)
        setupRecyclerView()
        if (time < 60.0) {
            startTimer()
        } else {
            showStopUi()
        }
        configureListeners()
        return binding.root
    }

    private fun initVariables() {
        adapter = WeatherAdapter()
        listOfMessage = resources.getStringArray(R.array.sentence_array)
        serviceIntent = Intent(requireContext(), TimeService::class.java)
        broadcastTimer = requireActivity().registerReceiver(updateTime, IntentFilter(TimeService.TIMER_UPDATED))
    }

    private fun checkSavedInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            time = savedInstanceState.getDouble(BUNDLE_STATE_TIME)
            index = savedInstanceState.getInt(BUNDLE_STATE_INDEX)
            binding.fragmentWeatherSentenceTv.text = listOfMessage[index]
        }
    }

    private fun event(time: Double) {

        viewModel.addWeatherToList(Utils.getCityForRequest(time))

        if (time == 60.0) {
            binding.apply {
                showStopUi()
            }
        }

        if (time.toInt() % 6 == 0) {
            index++
            if (index == 3) {
                index = 0
            }
            binding.fragmentWeatherSentenceTv.text = listOfMessage[index]
        }
    }

    private fun configureListeners() {
        binding.apply {
            fragmentWeatherAgainBtn.setOnClickListener {
                showStartUi()
                viewModel.clearWeatherList()
                time = -1.0
                startTimer()
            }
        }
    }

    private fun startTimer() {
        serviceIntent?.putExtra(TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
    }

    private fun stopTimer() {
        if (time == 60.0) {
            requireActivity().stopService(serviceIntent)
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
            viewModel.getWeatherList().observe(viewLifecycleOwner) {
                loadWeatherFromRecyclerView(it)
            }
        }
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TIME_EXTRA, 0.0)

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

    private fun setupRecyclerView() = binding.fragmentWeatherWeatherRv.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun loadWeatherFromRecyclerView(listOfWeather: List<Weather>) {
        adapter.submitList(listOfWeather)
        binding.fragmentWeatherWeatherRv.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(BUNDLE_STATE_TIME, time)
        outState.putInt(BUNDLE_STATE_INDEX, index)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().stopService(serviceIntent)
        requireActivity().unregisterReceiver(updateTime)
    }
}