package com.example.tjsid.cry

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.tjsid.cry.Date.Dates
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var date: Dates = Dates()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        down_bar.text = "Última atualização " + date.getAllHour()

    }
}
