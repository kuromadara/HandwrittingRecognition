package com.example.handwrittingrecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.handwrittingrecognition.StrokeManager.res
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public lateinit var textview: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        StrokeManager.download()
        recognize.setOnClickListener {

            StrokeManager.recognize(this)

            res()
            textview = findViewById(R.id.textView) as TextView
            textview.setText(res())

        }


        clear.setOnClickListener {
            drawingView.clear()
            StrokeManager.clear()
            textview.setText(StrokeManager.recogResult)
        }


    }
}