package com.example.kotlin_lessons

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val carFirst = Car("red", "sedan")
        val carCopy = carFirst.copy("yellow", "van")

        val button: Button = findViewById(R.id.button)
        val buttonCopy: Button = findViewById(R.id.button_copy)

        val textViewFirst: TextView = findViewById(R.id.text_view_first)
        val textViewSecond: TextView = findViewById(R.id.text_view_second)

        val textViewFirstCopy: TextView = findViewById(R.id.text_view_first_copy)
        val textViewSecondCopy: TextView = findViewById(R.id.text_view_second_copy)

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                textViewFirst.setText(carFirst.color)
                textViewSecond.setText(carFirst.type)

                Toast.makeText(
                    this@MainActivity,
                    " the first button is activated",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        buttonCopy.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                textViewFirstCopy.setText(carCopy.color)
                textViewSecondCopy.setText(carCopy.type)

                Toast.makeText(
                    this@MainActivity,
                    " the copy button is activated",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    data class Car(var color: String = "", var type: String = "")
}