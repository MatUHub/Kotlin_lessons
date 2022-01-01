package com.example.kotlin_lessons.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lessons.R
import com.example.kotlin_lessons.databinding.FragmentMainBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.view.details.BUNDLE_KEY
import com.example.kotlin_lessons.view.details.DetailsFragment
import com.example.kotlin_lessons.view_model.AppState
import com.example.kotlin_lessons.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnMyItemClickListener {

    //Создание переменной binding
    private var _binding: FragmentMainBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val adapter: MainFragmentAdapter by lazy { MainFragmentAdapter(this) }
    private var isRussian = true

    //Создание ссылки на ViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModelProvider хранилище для всех ViewModel, контролирует поуляцию ViewModel во всем проекте, каждая ViewModel в одном экземпляре
        //MainViewModel::class.java тоже самое что и MainViewModel.class в Java
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //Избежание утечек памяти
        // it - имя по умолчанию для одного параметра
        initView()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun initView() {
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener {
                sentRequest()
            }
        }
    }

    private fun sentRequest() {
        isRussian = !isRussian

        with(binding) {
            if (isRussian) {
                mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                viewModel.getWeatherFromLocalSourceRus()
            } else {
                mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                viewModel.getWeatherFromLocalSourceWorld()
            }
        }

    }

    //requireContext() при вызове данно фнукции происходи поверка на null
    fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    Snackbar.make(root, "Error", Snackbar.LENGTH_LONG)
                        .setAction("Try again") { sentRequest() }.show()

                }
                is AppState.Loading -> {
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    mainFragmentLoadingLayout.visibility = View.GONE

                    adapter.setWeather(appState.weatherData)

                    Snackbar.make(
                        root,
                        "Success",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Инициализация binding в проекте
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //companion задает static свойства
    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onItemClick(weather: Weather) {

        activity?.run {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main,
                    DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(BUNDLE_KEY, weather)
                    }
                    ))
                .addToBackStack("").commit()

        }
    }
}

