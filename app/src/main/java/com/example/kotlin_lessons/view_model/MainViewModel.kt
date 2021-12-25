package com.example.kotlin_lessons.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//MutableLiveData озанчает изменяемая LiveData, mutable - изменчевый
class MainViewModel(val liveData: MutableLiveData<Any> = MutableLiveData()):ViewModel() {

    fun getLiveData():LiveData<Any>{
        return liveData
    }

}