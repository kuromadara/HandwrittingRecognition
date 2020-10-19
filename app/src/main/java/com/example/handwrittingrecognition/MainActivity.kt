package com.example.handwrittingrecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val languageMapToLangCode: HashMap<String, String> = hashMapOf("English" to "en", "Assamese" to "as", "Hindi" to "hi")
        val languages = resources.getStringArray(R.array.Languages)

        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    StrokeManager.lang = languageMapToLangCode[languages.get(position)].toString()
                    StrokeManager.download(this@MainActivity)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        var editText = findViewById(R.id.editTextText) as EditText

        StrokeManager.download(this@MainActivity)
        recognize.setOnClickListener {

            recognize.isClickable = false

            StrokeManager.recognize(this){result ->
                recognize.isClickable = true
                editText.setText(result)

            }

        }


        clear.setOnClickListener {

            drawingView.clear()
            StrokeManager.clear()
            editText.setText(null)
            Toast.makeText(applicationContext,"Canvas Cleared",Toast.LENGTH_SHORT).show()

        }


    }
}