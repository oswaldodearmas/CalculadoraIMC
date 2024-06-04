package com.odearmas.calculadoraimc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.odearmas.calculadoraimc.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    lateinit var welcomeTextView: TextView
    lateinit var clickButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        clickButton = findViewById(R.id.clickButton)

        clickButton.setOnClickListener {
            welcomeTextView.text = getString(R.string.mainActivity_byeTextView)
            clickButton.text = getString(R.string.mainActivity_clickedButton)
        }
    }
}