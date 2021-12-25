package com.example.kotlin_lessons.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_lessons.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    //Создание переменной binding
 var _binding: FragmentMainBinding? = null
    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentMainBinding
    get(){
        return _binding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    companion object {
        fun newInstance() = MainFragment()
    }
}
