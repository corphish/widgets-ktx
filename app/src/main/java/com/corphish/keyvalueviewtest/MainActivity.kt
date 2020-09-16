package com.corphish.keyvalueviewtest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.corphish.widgets.ktx.adapters.PrebuiltAdapters
import com.corphish.widgets.ktx.dialogs.SingleChoiceAlertDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity for demonstration
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SingleChoiceAlertDialog(this).apply {
            titleString = "Test"
            messageString = "This is a test dialog which shows few options and also defines the behavior when options are clicked."
            choiceList = listOf(
                    SingleChoiceAlertDialog.ChoiceItem(
                            titleString = "Option 1",
                            iconResId = R.drawable.ic_sentiment_dissatisfied_black_128dp,
                            action = {
                                Toast.makeText(this@MainActivity, "Option 1", Toast.LENGTH_LONG).show()
                            }
                    ),
                    SingleChoiceAlertDialog.ChoiceItem(
                            titleString = "Option 2",
                            iconResId = R.drawable.ic_sentiment_dissatisfied_black_128dp,
                            action = {
                                Toast.makeText(this@MainActivity, "Option 2", Toast.LENGTH_LONG).show()
                            }
                    )
            )
        }.show()
    }

    val text: String
        get() = "Success"
}