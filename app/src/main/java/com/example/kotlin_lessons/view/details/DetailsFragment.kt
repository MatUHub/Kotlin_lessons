package com.example.kotlin_lessons.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_lessons.databinding.FragmentDetailsBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.view_model.MainViewModel

const val BUNDLE_KEY = "bundleKey"

class DetailsFragment : Fragment() {

    //Создание переменной binding
    private var _binding: FragmentDetailsBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    //private val adapter = MainFragmentAdapter()
    private var isRussian = true

    //Создание ссылки на ViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_KEY)
        if (weather != null) {
            binding.cityName.text = weather.city.name
            binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
            binding.temperatureValue.text = "${weather.temperature}"
            binding.feelsLikeValue.text = "${weather.feelsLike}"
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
    }

    //companion задает static свойства
    companion object {
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}



