package com.example.kotlin_lessons.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

//MutableLiveData озанчает изменяемая LiveData, mutable - изменчевый
class MainViewModel(val liveData: MutableLiveData<Any> = MutableLiveData()):ViewModel() {

    fun getLiveData():LiveData<Any>{
        return liveData
    }

    fun getWeatherFromServer(){
        Thread{
            sleep(2000)
            //.postValue синхронный поток
            //.value асинхронный поток
            liveData.postValue(Any())
        }.start()
    }
}