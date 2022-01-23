package com.example.kotlin_lessons.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lessons.repository.RepositoryImpl
import java.lang.Thread.sleep

//MutableLiveData озанчает изменяемая LiveData, mutable - изменчевый
class MainViewModel(

    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    fun getLiveData() = liveData

    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(true)
    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(false)
    fun getWeatherFromRemoteSource() = getWeatherFromLocalServer(true)

    private fun getWeatherFromLocalServer(isRussia: Boolean) {
        liveData.postValue(AppState.Loading(100))
        Thread {
            sleep(1000)
            liveData.postValue(
                AppState.Success(
                    with(repositoryImpl) {
                        if (isRussia)
                            getWeatherFromLocalStorageRus()
                        else getWeatherFromLocalStorageWorld()
                    })
            )
            //.postValue синхронный поток
            //.value асинхронный поток
        }.start()
    }
}
