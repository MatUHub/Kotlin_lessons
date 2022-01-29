package com.example.kotlin_lessons.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lessons.databinding.FragmentHistoryBinding
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.view.main.OnMyItemClickListener
import com.example.kotlin_lessons.view_model.AppState
import com.example.kotlin_lessons.view_model.HistoryViewModel

class HistoryFragment : Fragment(), OnMyItemClickListener {

    //Создание переменной binding
    private var _binding: FragmentHistoryBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentHistoryBinding
        get() {
            return _binding!!
        }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Инициализация binding в проекте
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //companion задает static свойства
    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onItemClick(weather: Weather) {


    }
}

