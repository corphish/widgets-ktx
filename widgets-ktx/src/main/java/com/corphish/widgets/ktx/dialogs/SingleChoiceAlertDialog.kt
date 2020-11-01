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
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corphish.widgets.ktx.R
import com.corphish.widgets.ktx.adapters.Adapters
import com.corphish.widgets.ktx.dialogs.properties.IconProperties
import com.corphish.widgets.ktx.extensions.views.applyIconProperties
import com.corphish.widgets.ktx.viewholders.ClickableViewHolder
import com.google.android.material.button.MaterialButton

/**
 * This alert dialog will show single choice
 * item selection UI.
 */
class SingleChoiceAlertDialog(context: Context) : BaseAlertDialog(context) {
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
     * Choice list.
     */
    var choiceList: List<ChoiceItem> = ArrayList()

    /**
     * Choice selection behavior
     */
    var dismissOnChoiceSelection = false

    /**
     * Icon properties.
     */
    var iconProperties = IconProperties()

    override fun show() {
        // Inflate specific view
        val view = LayoutInflater.from(context).inflate(R.layout.layout_alert_single_choice, null)

        // Hook views
        val title = view.findViewById<TextView>(R.id.alertTitle)
        val message = view.findViewById<TextView>(R.id.alertMessage)
        val recyclerView = view.findViewById<RecyclerView>(R.id.options)

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

        // Hide empty textviews
        if (title.text.isEmpty()) {
            title.visibility = View.GONE
        }

        if (message.text.isEmpty()) {
            message.visibility = View.GONE
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = Adapters.newImmutableListAdapter<ChoiceItem, ClickableViewHolder> {
            layoutResourceId = { R.layout.layout_single_choice_item }
            viewHolder = { it, _ -> ClickableViewHolder(it, listOf(R.id.choiceIcon, R.id.choiceTitle)) }
            listItems = choiceList
            binding = { viewHolder, position ->
                val item = listItems[position]

                if (item.titleResId == 0) {
                    viewHolder.getViewById<TextView>(R.id.choiceTitle)?.text = item.titleString
                } else {
                    viewHolder.getViewById<TextView>(R.id.choiceTitle)?.setText(item.titleResId)
                }

                val icon = viewHolder.getViewById<MaterialButton>(R.id.choiceIcon)
                icon?.setIconResource(item.iconResId)
                icon?.applyIconProperties(iconProperties)

                viewHolder.getView<View>()?.setOnClickListener {
                    item.action()
                    if (dismissOnChoiceSelection) {
                        dismissDialog()
                    }
                }
            }
            notifyDataSetChanged = true
        }

        super.contentView = view

        super.show()
    }

    /**
     * This class defines an item choice.
     */
    data class ChoiceItem(
            /**
             * Title resource id.
             * Preferred over titleString.
             */
            @StringRes val titleResId: Int = 0,

            /**
             * Title string.
             */
            val titleString: String = "",

            /**
             * Icon of the action.
             */
            @DrawableRes val iconResId: Int = 0,

            /**
             * Action to perform.
             */
            val action: () -> Unit = {}
    )
}