package com.example.kotlin_lessons.view

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

class MainFragment : Fragment(), OnItemClickListener {

    //Создание переменной binding
    private var _binding: FragmentMainBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val adapter = MainFragmentAdapter()
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
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            sentRequest()
        }
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun sentRequest() {
        isRussian = !isRussian
        if (isRussian) {
            viewModel.getWeatherFromLocalSourceRus()
        } else {
            viewModel.getWeatherFromLocalSourceWorld()
        }
    }


    //requireContext() при вызове данно фнукции происходи поверка на null
    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Try again") { sentRequest() }.show()

            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE

                adapter.setWeather(appState.weatherData)


                Snackbar.make(
                    binding.root,
                    "Success",
                    Snackbar.LENGTH_SHORT
                ).show()
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
        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_KEY, weather)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.container_main, DetailsFragment.newInstance(bundle))
            .addToBackStack("")
            .commit()

    }
}

