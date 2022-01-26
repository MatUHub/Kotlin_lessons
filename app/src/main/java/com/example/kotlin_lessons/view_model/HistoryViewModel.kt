package com.example.kotlin_lessons.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lessons.repository.RepositoryLocalImpl

class HistoryViewModel(

    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    private val repositoryLocalImpl: RepositoryLocalImpl by lazy {
        RepositoryLocalImpl()
    }

    fun getLiveData() = liveData

    fun getAllHistory() {
        Thread {
            val listWeather = repositoryLocalImpl.getAllHistoryWeather()
            liveData.postValue(AppState.Success(listWeather))
        }.start()
    }
}