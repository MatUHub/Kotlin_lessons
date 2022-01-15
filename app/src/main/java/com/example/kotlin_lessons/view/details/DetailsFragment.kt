package com.example.kotlin_lessons.view.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_lessons.databinding.FragmentDetailsBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.model.WeatherDTO
import com.example.kotlin_lessons.utils.WeatherLoader

const val BUNDLE_KEY = "bundleKey"

class DetailsFragment : Fragment(), WeatherLoader.OnWeatherLoaded {

    //Создание переменной binding
    private var _binding: FragmentDetailsBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                localWeather = it
                WeatherLoader(it.city.lat,it.city.lon,this).loadWeather()
            }
        }
    }

    private fun setWeatherData(weatherDTO: WeatherDTO){

        with(binding) {
            cityName.text = localWeather.city.name
            cityCoordinates.text = "${ localWeather.city.lat} ${ localWeather.city.lon}"
            temperatureValue.text = "${ weatherDTO.fact.temp}"
            feelsLikeValue.text = "${ weatherDTO.fact.feelsLike}"
        }
    }

    lateinit var localWeather: Weather
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
    }

    //companion задает static свойства
    companion object {
        fun newInstance(bundle: Bundle) = DetailsFragment().apply {
            arguments = bundle
        }
    }

    override fun onLoaded(weatherDTO: WeatherDTO?) {
        weatherDTO?.let {
            setWeatherData(weatherDTO)
        }
        Log.d("","")
    }

    override fun onFailed() {
        TODO("Not yet implemented")
    }
}




