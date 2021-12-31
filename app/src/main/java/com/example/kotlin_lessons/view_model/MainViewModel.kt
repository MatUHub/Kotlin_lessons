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

    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(true)
    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(false)
    fun getWeatherFromRemoteSource() = getWeatherFromLocalServer(true)

    private fun getWeatherFromLocalServer(isRussia: Boolean) {
        liveData.postValue(AppState.Loading(100))
        Thread {
            sleep(1000)
            liveData.postValue(AppState.Success(if (isRussia) repositoryImpl.getWeatherFromLocalStorageRus() else repositoryImpl.getWeatherFromLocalStorageWorld()))
            //.postValue синхронный поток
            //.value асинхронный поток
        }.start()
    }
}