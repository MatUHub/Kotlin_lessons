package com.example.kotlin_lessons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_lessons.databinding.FragmentContentProviderBinding
import com.example.kotlin_lessons.databinding.FragmentMainBinding


class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentContentProviderBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

     _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentProviderFragment()
    }
}

