package com.odearmas.calculadoraimc

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.odearmas.calculadoraimc.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var BMITextView: TextView
    private lateinit var lessButton: Button
    private lateinit var moreButton: Button
    private lateinit var heightSeekBar: SeekBar
    private lateinit var calculate: Button
    private var currentHeight: Int = 170
    private var currentWeight: Int = 75

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    fun initView() {
        heightTextView = findViewById(R.id.heightTextView)
        weightTextView = findViewById(R.id.weightTextView)
        BMITextView = findViewById(R.id.BMITextView)
        lessButton = findViewById(R.id.lessButton)
        moreButton = findViewById(R.id.moreButton)
        heightSeekBar = findViewById(R.id.heightSeekBar)
        calculate = findViewById(R.id.calculateButton)

        heightSeekBar.progress = currentHeight
        setHeightText()
        setWeightText()

        lessButton.setOnClickListener {
            if (currentWeight > 35 && currentWeight <= 200) {
                --currentWeight
                setWeightText()
            }
        }
        moreButton.setOnClickListener {
            if (currentWeight >= 35 && currentWeight < 200) {
                ++currentWeight
                setWeightText()
            }
        }

        heightSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    currentHeight = progress
                    setHeightText()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            },
        )
        calculate.setOnClickListener { BMITextView.text = calculateBMI() }

    }


    fun setHeightText() {
        heightTextView.text = getString(R.string.height_Text, currentHeight.toString())
    }

    fun setWeightText() {
        weightTextView.text = getString(R.string.weight_Text, currentWeight.toString())
    }

    fun calculateBMI(): String {
        var BMI: Float = (currentWeight / (currentHeight * currentHeight / 10000)).toFloat()
        when (BMI) {
            in 0.0..18.5 -> {
                return "BMI: $BMI\nBajo de peso"
            }

            in 18.5..24.9 -> {
                return "BMI: $BMI\nBien de peso"
            }

            in 24.9..29.9 -> {
                return "BMI: $BMI\nAlto peso"
            }

            else -> {
                return "BMI: $BMI\nDemasiado peso"
            }


        }
    }
}