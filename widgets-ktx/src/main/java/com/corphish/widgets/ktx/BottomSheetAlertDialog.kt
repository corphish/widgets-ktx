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

package com.corphish.widgets.ktx

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog


/**
 * Bottom sheet alert dialog, kinda material replacement for alert dialogs
 * You can use it to replace alert dialog messages
 * It wraps up only the basic alert dialog, which contains title, message, and positive, negative and neutral buttons
 */
class BottomSheetAlertDialog(private val context: Context,
                             @StyleRes themeId: Int = 0) {
    @StyleRes
    var themeId = 0

    // Dialog content
    var title: String = ""
    var message: String = ""
    var positiveButtonText: String? = null
    var negativeButtonText: String? = null
    var neutralButtonText: String? = null
    var positiveButtonClickListener: DialogInterface.OnClickListener? = null
    var negativeButtonClickListener: DialogInterface.OnClickListener? = null
    var neutralButtonListener: DialogInterface.OnClickListener? = null

    fun setTitle(@StringRes titleId: Int): BottomSheetAlertDialog {
        title = context.getString(titleId)
        return this
    }

    fun setMessage(@StringRes messageId: Int): BottomSheetAlertDialog {
        message = context.getString(messageId)
        return this
    }

    fun setPositiveButton(message: String?, onClickListener: DialogInterface.OnClickListener?): BottomSheetAlertDialog {
        positiveButtonText = message
        positiveButtonClickListener = onClickListener
        return this
    }

    fun setPositiveButton(@StringRes messageId: Int, onClickListener: DialogInterface.OnClickListener?): BottomSheetAlertDialog {
        positiveButtonText = context.getString(messageId)
        positiveButtonClickListener = onClickListener
        return this
    }

    fun setNegativeButton(message: String?, onClickListener: DialogInterface.OnClickListener?): BottomSheetAlertDialog {
        negativeButtonText = message
        negativeButtonClickListener = onClickListener
        return this
    }

    fun setNegativeButton(@StringRes messageId: Int, onClickListener: DialogInterface.OnClickListener?): BottomSheetAlertDialog {
        negativeButtonText = context.getString(messageId)
        negativeButtonClickListener = onClickListener
        return this
    }

    fun setNeutralButton(message: String?, onClickListener: DialogInterface.OnClickListener?): BottomSheetAlertDialog {
        neutralButtonText = message
        neutralButtonListener = onClickListener
        return this
    }

    fun setNeutralButton(@StringRes messageId: Int, onClickListener: DialogInterface.OnClickListener?): BottomSheetAlertDialog {
        neutralButtonText = context.getString(messageId)
        neutralButtonListener = onClickListener
        return this
    }

    fun show() {
        val bottomSheetDialog = BottomSheetDialog(context, themeId)
        bottomSheetDialog.setContentView(LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_alert_dialog, null))
        bottomSheetDialog.show()
    }
}