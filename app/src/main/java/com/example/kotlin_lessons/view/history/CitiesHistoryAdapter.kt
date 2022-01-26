package com.example.kotlin_lessons.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_lessons.R
import com.example.kotlin_lessons.databinding.FragmentHistoryRecyclerItemBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.view.main.OnMyItemClickListener

class CitiesHistoryAdapter(val listener: OnMyItemClickListener) :
    RecyclerView.Adapter<CitiesHistoryAdapter.HistoryCityViewHolder>() {

    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCityViewHolder {
        return HistoryCityViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryCityViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class HistoryCityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            with(FragmentHistoryRecyclerItemBinding.bind(itemView)) {
                cityName.text = weather.city.name
                temperature.text = "${weather.temperature}"
                feelsLike.text = "${weather.feelsLike}"

                root.setOnClickListener {
                    listener.onItemClick(weather)
                }
            }
        }
    }
}