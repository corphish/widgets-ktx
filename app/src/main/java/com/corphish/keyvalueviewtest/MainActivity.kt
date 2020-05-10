package com.corphish.keyvalueviewtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.corphish.widgets.ktx.adapters.Adapters
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countryList = listOf("USA", "Spain", "Italy", "Russia", "France")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapters.singleItemAdapterWith(countryList)
    }

    val text: String
        get() = "Success"
}