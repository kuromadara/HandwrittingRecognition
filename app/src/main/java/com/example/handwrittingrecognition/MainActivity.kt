package com.example.handwrittingrecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public lateinit var editText: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        StrokeManager.download()
        recognize.setOnClickListener {

            recognize.isClickable = false

            StrokeManager.recognize(this){result ->
                recognize.isClickable = true

                editText = findViewById(R.id.editTextText) as EditText
                editText.setText(result)
            }

        }


        clear.setOnClickListener {
            drawingView.clear()
            StrokeManager.clear()
            editText.setText(null)
        }


    }
}