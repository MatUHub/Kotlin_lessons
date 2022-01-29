package com.example.kotlin_lessons

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.kotlin_lessons.databinding.FragmentContentProviderBinding


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let {
            when {
                // запрос на разрешения доступа к контактам
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                //запрос рационализации
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showDialog()
                }

                else -> {
                    // команда запрос на разрешение
                    myRequestPermission()
                }
            }
        }
    }

    val REQUEST_CODE = 123
    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE){
            when{
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) ->  getContacts()

                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> showDialog()

                else -> Log.d("", "")
            }
        }
    }

    private fun getContacts() {
context?.let {
    // сущность стоящая над всеми базами данных
    val contentResolver = it.contentResolver
    //создаем запрос в базу данных
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        null,
        null,
        null,
        ContactsContract.Contacts.DISPLAY_NAME + " ASC"
    )
    cursor?.let { cursor ->
        for (i in 0 until cursor.count){
            cursor.moveToPosition(i)
            var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            // Добавлена проверка на null, если в списке контактов на телефоне не заполнена графа name, то выходит ошибка NullPointerExeption
            if(name == null) {name = "NoName"}
                addView(name)
        }
    }
    cursor?.close()
}
    }
    private fun addView(name : String){
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = name
            textSize = 30f
        })
    }

    private fun showDialog() {
AlertDialog.Builder(requireContext())
    .setTitle("Доступ к контактам")
    .setMessage("Предоставте доступ иначе ...")
    .setPositiveButton("Предоставить доступ"){_,_ ->
        // запрос на разрешение к доступу к контактам, при положительном ответе на сообщение
        myRequestPermission()
    }
    .setNegativeButton("Нет"){ dialog, _ -> dialog.dismiss()}
    .create()
    .show()
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


