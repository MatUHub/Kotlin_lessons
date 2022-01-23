package com.example.kotlin_lessons.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.model.WeatherDTO
import com.example.kotlin_lessons.model.getDefaultCity
import com.example.kotlin_lessons.repository.RepositoryImpl
import com.example.kotlin_lessons.utils.YANDEX_API_END_POINT
import com.example.kotlin_lessons.utils.YANDEX_API_URL
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback

//MutableLiveData озанчает изменяемая LiveData, mutable - изменчевый
class DetailsViewModel(

    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    fun getLiveData() = liveData


    fun getWeatherFromRemoteServer(lat: Double,lon: Double) {
        liveData.postValue(AppState.Loading(0))
        repositoryImpl.getWeatherFromServer(YANDEX_API_URL + YANDEX_API_END_POINT + "?lat = ${lat}&lon = ${lon}", callback)
    }

    fun converterDTOtoModel(weatherDTO: WeatherDTO): List<Weather>{
        return listOf(Weather(getDefaultCity(), weatherDTO.fact.temp.toInt(), weatherDTO.fact.feelsLike.toInt()))
    }

   private val callback = object : Callback{
         fun onFailure(call: Call, e: IOException) {
            TODO("Not yet implemented")
        }

         fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                response.body()?.let {
                    val json = it.string()
               liveData.postValue(AppState.Success(converterDTOtoModel(Gson().fromJson(json, WeatherDTO::class.java))))
                    }
            } else {
//TODO HW
            }
        }
    }
}
