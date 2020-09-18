package com.corphish.keyvalueviewtest

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.corphish.widgets.ktx.dialogs.SingleChoiceAlertDialog
import com.corphish.widgets.ktx.dialogs.properties.IconProperties
import com.corphish.widgets.ktx.extensions.asColor

/**
 * Activity for demonstration
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iconColor = Color.WHITE
        val background = DrawableCompat.wrap(ContextCompat.getDrawable(this, com.corphish.widgets.ktx.R.drawable.circle)!!)
        DrawableCompat.setTint(background, Color.RED)

        SingleChoiceAlertDialog(this).apply {
            titleString = "Test"
            iconProperties = IconProperties(
                    iconColor = iconColor,
                    backgroundDrawable = background
            )
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