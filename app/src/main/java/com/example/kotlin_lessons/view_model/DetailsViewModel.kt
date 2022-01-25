package com.example.kotlin_lessons.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.model.WeatherDTO
import com.example.kotlin_lessons.model.getDefaultCity
import com.example.kotlin_lessons.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//MutableLiveData озанчает изменяемая LiveData, mutable - изменчивый
class DetailsViewModel(

    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    fun getLiveData() = liveData


    fun getWeatherFromRemoteServer(lat: Double, lon: Double) {
        liveData.postValue(AppState.Loading(0))
        repositoryImpl.getWeatherFromServer(lat, lon, callback)
    }

    fun converterDTOtoModel(weatherDTO: WeatherDTO): List<Weather> {
        return listOf(
            Weather(
                getDefaultCity(),
                weatherDTO.fact.temp.toInt(),
                weatherDTO.fact.feelsLike.toInt()
            )
        )
    }

    private val callback = object : Callback<WeatherDTO> {
        override fun onFailure(call: Call<WeatherDTO>, e: Throwable) {
            Log.d("Error", "Server not found")

        }

        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(AppState.Success(converterDTOtoModel(it)))
                }
            } else {
                response.body()?.let {
                    liveData.postValue(AppState.Error(it))
                }
            }
        }
    }
}
