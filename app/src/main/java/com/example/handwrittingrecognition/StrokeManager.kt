package com.example.handwrittingrecognition

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.*



object StrokeManager {
    private var inkBuilder = Ink.builder()
    private var strokeBuilder = Ink.Stroke.builder()
    private lateinit var model: DigitalInkRecognitionModel

    public var recogResult : String? = null

    fun addNewTouchEvent(event: MotionEvent) {
        val action = event.actionMasked
        val x = event.x
        val y = event.y
        val t = System.currentTimeMillis()

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> strokeBuilder.addPoint(
                Ink.Point.create(
                    x,
                    y,
                    t
                )
            )
            MotionEvent.ACTION_UP -> {
                strokeBuilder.addPoint(Ink.Point.create(x, y, t))
                inkBuilder.addStroke(strokeBuilder.build())
                strokeBuilder = Ink.Stroke.builder()

            }
            else -> {
                // Action not relevant for ink construction
            }
        }
    }

    fun download() {
        var modelIdentifier: DigitalInkRecognitionModelIdentifier? = null
        try {
            modelIdentifier =
                DigitalInkRecognitionModelIdentifier.fromLanguageTag("en") 
        } catch (e: MlKitException) {
            // language tag failed to parse, handle error.
        }

        model =
            DigitalInkRecognitionModel.builder(modelIdentifier!!).build()

        val remoteModelManager = RemoteModelManager.getInstance()
        remoteModelManager.download(model, DownloadConditions.Builder().build())
            .addOnSuccessListener {
                Log.i("StrokeManager", "Model downloaded")
            }
            .addOnFailureListener { e: Exception ->
                Log.e("StrokeManager", "Error while downloading a model: $e")
            }
    }

    fun recognize(context: Context) {
        val recognizer: DigitalInkRecognizer =
            DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model).build()
            )


        val ink = inkBuilder.build()

        recognizer.recognize(ink)
            .addOnSuccessListener { result: RecognitionResult ->
                recogResult = result.candidates[0].text
            }
            .addOnFailureListener { e: Exception ->
                Log.e("StrokeManager", "Error during recognition: $e")
            }
    }

    fun res(): String? {
        var resultToText = recogResult

        return resultToText
    }


    fun clear() {
        inkBuilder = Ink.builder()
        recogResult = null
    }

}