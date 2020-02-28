package com.corphish.keyvalueviewtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.corphish.widgets.ktx.BottomSheetAlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BottomSheetAlertDialog(this).apply {
            title = "Jorgo"
            message = "lahaptup"
        }.show()
    }

    val text: String
        get() = "Success"
}