package com.example.kotlin_lessons.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin_lessons.databinding.FragmentDetailsBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.utils.BROADCAST_ACTION
import com.example.kotlin_lessons.utils.BUNDLE_KEY
import com.example.kotlin_lessons.utils.BUNDLE_KEY_WEATHER
import com.example.kotlin_lessons.view_model.AppState
import com.example.kotlin_lessons.view_model.DetailsViewModel



class DetailsFragment : Fragment() {

    //Создание переменной binding
    private var _binding: FragmentDetailsBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<Weather>(BUNDLE_KEY_WEATHER)?.let {
                    setWeatherData(it)
                }
            }
        }
    }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

   private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                }
                is AppState.Loading -> {
                    Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()
                }
                is AppState.Success -> {
                    val weather = appState.weatherData[0]
                    setWeatherData(weather)
                }
            }
        }
    }

    private lateinit var localWeather: Weather
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                viewModel.getWeatherFromRemoteServer(localWeather.city.lat, localWeather.city.lon)
            }
        }
        requireActivity().registerReceiver(receiver, IntentFilter(BROADCAST_ACTION))
    }

    private fun setWeatherData(weather: Weather) {



        with(binding) {

         /*   headerIcon.setOnClickListener {
            viewModel.saveWeather(weather)
        }*/
            cityName.text = localWeather.city.name
            cityCoordinates.text = "${localWeather.city.lat} ${localWeather.city.lon}"
            temperatureValue.text = "${weather.temperature}"
            feelsLikeValue.text = "${weather.feelsLike}"

            Glide.with(requireActivity())
                .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png").into(headerIcon)

            // Загрузка картинки с помощью других библиотек

            /*Picasso.get().load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                .into(headerIcon)

            headerIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")*/
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализация binding в проекте
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().unregisterReceiver(receiver)
    }

    //companion задает static свойства
    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply {
            arguments = bundle
        }
    }

}




