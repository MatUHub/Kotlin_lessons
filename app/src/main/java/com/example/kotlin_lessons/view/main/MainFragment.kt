package com.example.kotlin_lessons.view.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_lessons.R
import com.example.kotlin_lessons.model.Settings
import com.example.kotlin_lessons.databinding.FragmentMainBinding
import com.example.kotlin_lessons.model.City
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.utils.BUNDLE_KEY
import com.example.kotlin_lessons.view.details.DetailsFragment
import com.example.kotlin_lessons.view_model.AppState
import com.example.kotlin_lessons.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), OnMyItemClickListener {

    //Создание переменной binding
    private var _binding: FragmentMainBinding? = null

    //Схема для унчитожения binding при лквидации Activity
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val adapter: CitiesAdapter by lazy { CitiesAdapter(this) }

    private var isRussian = true

    //Создание ссылки на ViewModel
    private lateinit var viewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModelProvider хранилище для всех ViewModel, контролирует поуляцию ViewModel во всем проекте, каждая ViewModel в одном экземпляре
        //MainViewModel::class.java тоже самое что и MainViewModel.class в Java
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //Избежание утечек памяти
        // it - имя по умолчанию для одного параметра
        initView()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })

        isRussian =
            requireActivity().getSharedPreferences(Settings.SHARED_PREF, Context.MODE_PRIVATE)
                .getBoolean(Settings.SETTING_RUS, Settings.settingRus)

        if (isRussian) {
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            viewModel.getWeatherFromLocalSourceRus()
        } else {
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
            viewModel.getWeatherFromLocalSourceWorld()
        }

    }

    private fun initView() {
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener {
                sentRequest()
            }
            mainFragmentFABLocation.setOnClickListener() {
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        context?.let {
            when {
                // запрос на разрешения доступа к контактам
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                //запрос рационализации
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showDialogRacion()
                }

                else -> {
                    // команда запрос на разрешение
                    myRequestPermission()
                }
            }
        }
    }

    private val MIN_DISTANCE = 100f
    private val REFRESH_PERIOD = 60000L

    private fun showAddressDialog(address: String, location: Location) {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к геолокации")
            .setMessage(address)
            .setPositiveButton("Узнать погоду") { _, _ ->
                // запрос на разрешение к доступу к контактам, при положительном ответе на сообщение
                toDetails(Weather(City(address, location.latitude, location.longitude)))
            }
            .setNegativeButton("Нет") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun getAddress(location: Location) {

        Thread {
            val geocoder = Geocoder(requireContext())
            val listAddress = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            requireActivity().runOnUiThread {
                showAddressDialog(listAddress[0].getAddressLine(0), location)
            }
        }.start()

    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            getAddress(location)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }
    }

    private fun getLocation() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val locationManager =
                    it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val providerGPS = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    providerGPS?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MIN_DISTANCE,
                            locationListener
                        )
                    }
                } else {
                    val lastLocation =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lastLocation?.let {
                        getAddress(it)
                    }
                }
            }
        }
    }

    private fun showDialog() {

    }


    val REQUEST_CODE = 123
    private fun myRequestPermission() {
        // разрешение на доступ к определению местоположения
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> getLocation()

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> showDialogRacion()

                else -> Log.d("", "")
            }
        }
    }

    private fun showDialogRacion() {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к геолокации")
            .setMessage("Предоставте доступ иначе ...")
            .setPositiveButton("Предоставить доступ") { _, _ ->
                // запрос на разрешение к доступу к контактам, при положительном ответе на сообщение
                myRequestPermission()
            }
            .setNegativeButton("Нет") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun sentRequest() {

        isRussian = !isRussian
        val sharedPreferences =
            requireActivity().getSharedPreferences(Settings.SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        with(binding) {
            if (isRussian) {
                Settings.settingRus = isRussian
                mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                viewModel.getWeatherFromLocalSourceRus()
                editor.putBoolean(Settings.SETTING_RUS, Settings.settingRus)
                editor.apply()
            } else {
                editor.putBoolean(Settings.SETTING_RUS, Settings.settingRus)
                Settings.settingRus = isRussian
                mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                viewModel.getWeatherFromLocalSourceWorld()
                editor.apply()
            }
        }

    }

    //requireContext() при вызове данно фнукции происходи поверка на null
    private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    Snackbar.make(root, "Error", Snackbar.LENGTH_LONG)
                        .setAction("Try again") { sentRequest() }.show()

                }
                is AppState.Loading -> {
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    mainFragmentLoadingLayout.visibility = View.GONE

                    adapter.setWeather(appState.weatherData)

                    Snackbar.make(
                        root,
                        "Success",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Инициализация binding в проекте
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //companion задает static свойства
    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onItemClick(weather: Weather) {

        toDetails(weather)
    }

    private fun toDetails(weather: Weather) {
        activity?.run {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_main,
                    DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(BUNDLE_KEY, weather)
                    }
                    ))
                .addToBackStack("").commit()

        }
    }
}

