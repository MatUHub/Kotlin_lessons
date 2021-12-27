package com.example.kotlin_lessons.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lessons.model.RepositoryImpl
import java.lang.Thread.sleep

//MutableLiveData озанчает изменяемая LiveData, mutable - изменчевый
class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun getWeatherFromServer() {
        liveData.postValue(AppState.Loading(100))
        Thread {
            sleep(2000)
            val rand = (1..10).random()
            if (rand < 5) liveData.postValue(AppState.Error("Ошибка"))
            else liveData.postValue(AppState.Success(repositoryImpl.getWeatherFromServer()))
            //.postValue синхронный поток
            //.value асинхронный поток
                //liveData.postValue(AppState.Success("Свежесть преобладает"))
        }.start()
    }
}