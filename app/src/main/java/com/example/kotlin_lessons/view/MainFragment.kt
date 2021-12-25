package com.example.kotlin_lessons.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lessons.databinding.FragmentMainBinding
import com.example.kotlin_lessons.view_model.MainViewModel

class MainFragment : Fragment() {

    //Создание переменной binding
 var _binding: FragmentMainBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

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
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<Any> { renderData(it) })
    }
//requireContext() при вызове данно фнукции происходи поверка на null
    fun renderData(data:Any){
        Toast.makeText(requireContext(), "Work", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
}
