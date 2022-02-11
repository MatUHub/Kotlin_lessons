package com.example.kotlin_lessons.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lessons.databinding.FragmentDetailsBinding
import com.example.kotlin_lessons.databinding.FragmentHistoryBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.view.BaseFragment
import com.example.kotlin_lessons.view.main.OnMyItemClickListener
import com.example.kotlin_lessons.view_model.AppState
import com.example.kotlin_lessons.view_model.HistoryViewModel

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate), OnMyItemClickListener {



    private val adapter: CitiesHistoryAdapter by lazy { CitiesHistoryAdapter(this) }

    //Создание ссылки на ViewModel
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModelProvider хранилище для всех ViewModel, контролирует поуляцию ViewModel во всем проекте, каждая ViewModel в одном экземпляре
        //MainViewModel::class.java тоже самое что и MainViewModel.class в Java
        //Избежание утечек памяти
        // it - имя по умолчанию для одного параметра
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getAllHistory()
        binding.historyFragmentRecyclerView.adapter = adapter
    }

    //requireContext() при вызове данно фнукции происходи поверка на null
    fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                is AppState.Loading -> {}
                is AppState.Success -> {
                    adapter.setWeather(appState.weatherData)
                }
            }
        }
    }

    //companion задает static свойства
    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onItemClick(weather: Weather) {


    }
}

