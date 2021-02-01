package com.example.brushingtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var index :Int = 1

    private lateinit var textViewTimer : TextView
    private lateinit var textViewGuide : TextView
    private lateinit var btnControl : Button
    private lateinit var btnReset : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textViewTimer = findViewById(R.id.textViewTimer)
        btnControl = findViewById(R.id.btnControl)
        btnReset = findViewById(R.id.btnReset)
        textViewGuide = findViewById(R.id.textViewGuide)

        btnControl.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) start() else pause()
        }

        btnReset.setOnClickListener {
            reset()
        }
    }

    private fun start() {
        btnControl.text = "Pause"

        timerTask = kotlin.concurrent.timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100

            runOnUiThread {
                textViewTimer.text = "$sec:$milli"
                if(sec == 0 || sec % 30 == 0) {
                    when (sec) {
                        0 -> {
                            textViewGuide.text = "어금니 씹는면을 닦아주세요."
                        }
                        30 -> {
                            textViewGuide.text = "어금니의 안쪽을 닦아주세요."
                        }
                        60 -> {
                            textViewGuide.text = "어금니의 바깥쪽을 닦아주세요."
                        }
                        90 -> {
                            textViewGuide.text = "앞니의 안쪽을 닦아주세요."
                        }
                        120 -> {
                            textViewGuide.text = "앞니의 바깥쪽을 닦아주세요."
                        }
                        150 -> {
                            textViewGuide.text = "혀와 볼을 닦아주세요."
                        }
                        180 -> {
                            textViewGuide.text = "양치 끝!"
                            pause()
                        }
                    }
                }
            }
        }
    }

    private fun pause() {
        btnControl.text = "Start"
        timerTask?.cancel();
    }

    private fun reset() {
        if(isRunning)
            pause()

        time = 0
        isRunning = false
        textViewTimer.text = "0:00"
        textViewGuide.text = "양치를 시작하세요!"

        index = 1
    }
}