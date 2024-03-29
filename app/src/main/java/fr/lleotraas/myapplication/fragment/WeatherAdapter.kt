package fr.lleotraas.myapplication.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.lleotraas.myapplication.databinding.WeatherRowBinding
import fr.lleotraas.myapplication.model.Weather
import fr.lleotraas.myapplication.retrofit.RetrofitInstance

class WeatherAdapter : ListAdapter<Weather, WeatherAdapter.MainViewHolder>(Companion) {

    inner class MainViewHolder(val binding: WeatherRowBinding) : RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(WeatherRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val weather = currentList[position]

        holder.binding.apply {
            weatherRowCityTv.text = weather.name
            weatherRowTemperatureTv.text = String.format("%2.0f", weather.main.temp)

            RetrofitInstance.getBitmapFrom(weather.weather[0].icon) {
                Glide.with(holder.binding.root)
                    .load(it)
                    .into(weatherRowWeatherImg)
            }
        }
    }


}