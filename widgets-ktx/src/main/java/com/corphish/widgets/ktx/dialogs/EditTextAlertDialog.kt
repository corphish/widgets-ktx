/*
 *
 * Copyright (C) 2020 Avinaba Dalal <d97.avinaba@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.corphish.widgets.ktx.dialogs

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.corphish.widgets.ktx.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EditTextAlertDialog(context: Context) : BaseAlertDialog(context) {
    /**
     * Title of this dialog.
     */
    var titleString = ""

    @StringRes
    var titleResId = 0

    /**
     * Message of this dialog.
     */
    var messageString = ""

    @StringRes
    var messageResId = 0

    /**
     * Positive button handling.
     */
    var positiveButtonProperties = ButtonProperties()

    /**
     * Negative button handling.
     */
    var negativeButtonProperties = ButtonProperties()

    /**
     * Neutral button handling.
     */
    var neutralButtonProperties = ButtonProperties()

    /**
     * Edit text handler
     */
    var editTextInitialText = ""
    var editTextHandling: (TextInputLayout, CharSequence?, Int, Int, Int) -> Unit =
        { _, _, _, _, _ -> }

    override fun show() {
        // Inflate specific view
        val view = LayoutInflater.from(context).inflate(R.layout.layout_alert_edit_text, null)

        // Hook views
        val title = view.findViewById<TextView>(R.id.alertTitle)
        val message = view.findViewById<TextView>(R.id.alertMessage)
        val positiveButton = view.findViewById<MaterialButton>(R.id.positiveButton)
        val negativeButton = view.findViewById<MaterialButton>(R.id.negativeButton)
        val neutralButton = view.findViewById<MaterialButton>(R.id.neutralButton)
        val editTextLayout = view.findViewById<TextInputLayout>(R.id.editTextLayout)
        val editText = view.findViewById<TextInputEditText>(R.id.editText)

        if (titleResId == 0) {
            title.text = titleString
        } else {
            title.setText(titleResId)
        }

        if (messageResId == 0) {
            message.text = messageString
        } else {
            message.setText(messageResId)
        }

        if (positiveButtonProperties.buttonTitle.isNotEmpty() || positiveButtonProperties.buttonTitleResId != 0) {
            positiveButton.visibility = View.VISIBLE
            if (positiveButtonProperties.buttonTitleResId == 0) {
                positiveButton.text = positiveButtonProperties.buttonTitle
            } else {
                positiveButton.setText(positiveButtonProperties.buttonTitleResId)
            }

            positiveButton.setOnClickListener {
                positiveButtonProperties.buttonAction(editTextLayout, editText.editableText.toString())

                if (positiveButtonProperties.dismissDialogOnButtonClick) {
                    dismissDialog()
                }
            }

            if (positiveButtonProperties.buttonIcon != 0) {
                positiveButton.setIconResource(positiveButtonProperties.buttonIcon)
            }

            if (positiveButtonProperties.useCustomButtonColor) {
                val color = getButtonColorFromProperty(positiveButtonProperties)
                positiveButton.setBackgroundColor(color)
            }
        }

        if (negativeButtonProperties.buttonTitle.isNotEmpty() || negativeButtonProperties.buttonTitleResId != 0) {
            negativeButton.visibility = View.VISIBLE
            if (negativeButtonProperties.buttonTitleResId == 0) {
                negativeButton.text = negativeButtonProperties.buttonTitle
            } else {
                negativeButton.setText(negativeButtonProperties.buttonTitleResId)
            }

            negativeButton.setOnClickListener {
                negativeButtonProperties.buttonAction(editTextLayout, editText.editableText.toString())

                if (negativeButtonProperties.dismissDialogOnButtonClick) {
                    dismissDialog()
                }
            }

            if (negativeButtonProperties.buttonIcon != 0) {
                negativeButton.setIconResource(negativeButtonProperties.buttonIcon)
            }

            if (negativeButtonProperties.useCustomButtonColor) {
                val color = getButtonColorFromProperty(negativeButtonProperties)
                negativeButton.strokeColor = ColorStateList.valueOf(color)
                negativeButton.setTextColor(color)
                negativeButton.iconTint = ColorStateList.valueOf(color)
            }
        }

        if (neutralButtonProperties.buttonTitle.isNotEmpty() || neutralButtonProperties.buttonTitleResId != 0) {
            neutralButton.visibility = View.VISIBLE
            if (neutralButtonProperties.buttonTitleResId == 0) {
                neutralButton.text = neutralButtonProperties.buttonTitle
            } else {
                neutralButton.setText(neutralButtonProperties.buttonTitleResId)
            }

            neutralButton.setOnClickListener {
                neutralButtonProperties.buttonAction(editTextLayout, editText.editableText.toString())

                if (neutralButtonProperties.dismissDialogOnButtonClick) {
                    dismissDialog()
                }
            }

            if (neutralButtonProperties.buttonIcon != 0) {
                neutralButton.setIconResource(neutralButtonProperties.buttonIcon)
            }

            if (neutralButtonProperties.useCustomButtonColor) {
                val color = getButtonColorFromProperty(neutralButtonProperties)
                neutralButton.setBackgroundColor(color)
            }
        }

        if (editTextInitialText.isNotEmpty()) {
            editText.setText(editTextInitialText)
        }

        editText.doOnTextChanged { text, start, before, count ->
            editTextHandling(editTextLayout, text, start, before, count)
        }

        super.contentView = view

        super.show()
    }

    /**
     * Gets button color from button properties.
     *
     * @param buttonProperties Button Properties.
     */
    private fun getButtonColorFromProperty(buttonProperties: ButtonProperties) =
        if (buttonProperties.buttonColorResId == 0) {
            if (buttonProperties.buttonColor == Int.MIN_VALUE) {
                Color.parseColor(buttonProperties.buttonColorHex)
            } else {
                buttonProperties.buttonColor
            }
        } else {
            ResourcesCompat.getColor(context.resources, buttonProperties.buttonColorResId, context.theme)
        }


    /**
     * Button properties data class.
     */
    data class ButtonProperties(
        /**
         * Button title.
         */
        val buttonTitle: String = "",

        /**
         * Button title resource id.
         * This preferred over string.
         */
        @StringRes val buttonTitleResId: Int = 0,

        /**
         * Action to be performed when this button is clicked.
         */
        val buttonAction: (TextInputLayout, String) -> Unit = { _, _ -> },

        /**
         * Behavior which defines whether dialog is dismissed
         * when this button is clicked.
         */
        val dismissDialogOnButtonClick: Boolean = false,

        /**
         * Button icon.
         */
        @DrawableRes val buttonIcon: Int = 0,

        /**
         * Boolean indicating whether custom button color
         * must be used or not.
         */
        val useCustomButtonColor: Boolean = false,

        /**
         * Color resource id for button color.
         * This preferred over the rest 2 color properties.
         */
        @ColorRes val buttonColorResId: Int = 0,

        /**
         * Color integer value for button color.
         * This is preferred over hex value.
         */
        @ColorInt val buttonColor: Int = Int.MIN_VALUE,

        /**
         * Button ccc hex value.
         */
        val buttonColorHex: String = "#000000"
    )
}