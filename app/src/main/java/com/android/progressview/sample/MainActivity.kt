package com.android.progressview.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abhinav.progress_view.ProgressData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            progressView.setData(ProgressData("",30f,120f,R.color.rally_orange))
            progressView2.setData(ProgressData("",50f,120f,R.color.rally_green_500))
            progressView3.setData(ProgressData("",90f,120f,R.color.rally_blue_700))
            progressView4.setData(ProgressData("",40f,120f,R.color.colorAccent))
        }
    }
}