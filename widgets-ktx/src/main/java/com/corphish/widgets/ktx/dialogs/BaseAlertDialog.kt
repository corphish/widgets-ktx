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
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RawRes
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.corphish.widgets.ktx.R

/**
 * Base alert dialog.
 * This class provides base for other alert dialogs.
 */
open class BaseAlertDialog(val context: Context) {
    /**
     * Lottie animation view layout
     */
    @RawRes var animationResourceLayout: Int = 0

    /**
     * Content view.
     * Depending on the type of the dialog, the content view
     * will define what is shown in dialog body
     */
    lateinit var contentView: View

    /**
     * Dialog instance.
     * Content will be shown in this dialog.
     */
    private lateinit var dialog: AlertDialog

    /**
     * Cancelable property.
     */
    var cancelable = true

    /**
     * Method to safely dismiss dialog
     */
    fun dismissDialog() {
        if (this::dialog.isInitialized) {
            dialog.dismiss()
        }
    }

    /**
     * Method to show the dialog
     */
    open fun show() {
        // contentView needs to be initialized
        if (!this::contentView.isInitialized) {
            // Don't build if it is not initialized
            return
        }

        /*
         * Alert dialog builder
         */
        val builder = AlertDialog.Builder(context)

        // Build base view
        val baseView = LayoutInflater.from(context).inflate(R.layout.layout_alert_base, null)

        val animationView = baseView.findViewById<LottieAnimationView>(R.id.animationView)
        val contentViewParent = baseView.findViewById<LinearLayout>(R.id.contentView)

        if (animationResourceLayout != 0) {
            animationView.setAnimation(animationResourceLayout)
            animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        } else {
            animationView.visibility = View.GONE
        }

        contentViewParent.addView(contentView)

        builder.setView(baseView)
        builder.setCancelable(cancelable)

        dialog = builder.show()
    }
}