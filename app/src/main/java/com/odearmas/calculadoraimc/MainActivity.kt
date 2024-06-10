package com.odearmas.calculadoraimc

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlin.math.pow
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {



    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var BMITextView: TextView
    private lateinit var BMICardView: CardView
    private lateinit var lessButton: Button
    private lateinit var moreButton: Button
    private lateinit var heightSeekBar: SeekBar
    private lateinit var calculateButton: Button
    private var currentHeight: Int = 170
    private var currentWeight: Int = 75

    private val handler = Handler(Looper.getMainLooper())
    private val weightDecreaseRunnable = object : Runnable {
        override fun run() {
            if (currentWeight > 35 && currentWeight <= 200) {
                --currentWeight
                setWeightText()
                handler.postDelayed(this, 100)
            }
        }
    }

    private val weightIncreaseRunnable = object : Runnable {
        override fun run() {
            if (currentWeight >= 35 && currentWeight < 200) {
                ++currentWeight
                setWeightText()
                handler.postDelayed(this, 100)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("INICIO_CREATE","Se inicia la ejecución del Create")
        initView()
        Log.d("FIN_CREATE","Finaliza la ejecución del Create")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        Log.d("INICIO_INIT","Se inicia la ejecución del Init")
        heightTextView = findViewById(R.id.heightTextView)
        weightTextView = findViewById(R.id.weightTextView)
        BMITextView = findViewById(R.id.BMITextView)
        BMICardView = findViewById(R.id.BMICardView)
        lessButton = findViewById(R.id.lessButton)
        moreButton = findViewById(R.id.moreButton)
        heightSeekBar = findViewById(R.id.heightSeekBar)
        calculateButton = findViewById(R.id.calculateButton)

        heightSeekBar.progress = currentHeight
        setHeightText()
        setWeightText()

        this.lessButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    handler.post(weightDecreaseRunnable)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacks(weightDecreaseRunnable)
                    true
                }
                else -> false
            }
        }

        this.moreButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    handler.post(weightIncreaseRunnable)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.removeCallbacks(weightIncreaseRunnable)
                    true
                }
                else -> false
            }
        }


//        lessButton.setOnClickListener {
//            if (currentWeight > 35 && currentWeight <= 200) {
//                --currentWeight
//                setWeightText()
//            }
//        }
//        moreButton.setOnClickListener {
//            if (currentWeight >= 35 && currentWeight < 200) {
//                ++currentWeight
//                setWeightText()
//            }
//        }

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
        calculateButton.setOnClickListener { BMITextView.text = calculateBMI() }

        Log.d("FIN_INIT","Finaliza la ejecución del Init")
    }


    fun setHeightText() {
        heightTextView.text = getString(R.string.mainActivity_height_Text, currentHeight.toString())
    }

    fun setWeightText() {
        weightTextView.text = getString(R.string.mainActivity_weight_Text, currentWeight.toString())
    }


    fun calculateBMI(): String {
        Log.d("INICIO_CALC","Se inicia la ejecución de Calculadora")

        val BMI: Float = (currentWeight / ((currentHeight.toFloat() / 100)).pow(2))
        val decimalFormat = DecimalFormat("#.##")
        val result: String = decimalFormat.format(BMI)
        when (BMI) {
            in 0.0f..18.5f -> {
                BMITextView.setTextColor(getColor(R.color.red))
                BMICardView.background.setTint(getColor(R.color.yellow))
                Log.d("FIN_CALC","Finaliza la ejecución de Calculadora")
                return getString(R.string.mainActivity_underweight_Text, result)
            }

            in 18.5f..24.9f -> {
                BMITextView.setTextColor(getColor(R.color.yellow))
                BMICardView.background.setTint(getColor(R.color.green))
                Log.d("FIN_CALC","Finaliza la ejecución de Calculadora")
                return getString(R.string.mainActivity_okWeight_Text, result)
            }

            in 24.9f..29.9f -> {
                BMITextView.setTextColor(getColor(R.color.green))
                BMICardView.background.setTint(getColor(R.color.orange))
                Log.d("FIN_CALC","Finaliza la ejecución de Calculadora")
                return getString(R.string.mainActivity_overweight_Text, result)
            }

            else -> {
                BMITextView.setTextColor(getColor(R.color.white))
                BMICardView.background.setTint(getColor(R.color.red))
                Log.d("FIN_CALC","Finaliza la ejecución de Calculadora")
                return getString(R.string.mainActivity_obeseWeight_Text, result)
            }
        }
    }
}