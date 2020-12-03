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
import android.content.res.Resources
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.*
import com.airbnb.lottie.LottieAnimationView

/**
 * Placeholder view to show in case there is nothing to show in certain situations
 * For example, use this to show empty search results or failure screen
 */
class PlaceholderView @JvmOverloads constructor(context: Context,
                                                attributeSet: AttributeSet? = null,
                                                defStyle: Int = 0) : RelativeLayout(context, attributeSet, defStyle) {
    /**
     * Make the views used user accessible for advanced stuff
     * @return Description textview
     */
    val descriptionTextView: TextView

    /**
     * Make the views used user accessible for advanced stuff
     * @return Title textview
     */
    val titleTextView: TextView

    /**
     * Make the views used user accessible for advanced stuff
     * @return imageview
     */
    val animationView: LottieAnimationView

    // To handle sizes
    private val dpi: Float

    private fun processProperties(context: Context, attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PlaceholderView)
        val count = typedArray.indexCount
        for (i in 0 until count) {
            val property = typedArray.getIndex(i)
            if (property == R.styleable.PlaceholderView_titleText) {
                title = typedArray.getText(property).toString()
                continue
            }
            if (property == R.styleable.PlaceholderView_descriptionText) {
                description = typedArray.getText(property).toString()
                continue
            }
            if (property == R.styleable.PlaceholderView_titleSize) {
                setTitleSize(typedArray.getDimension(property, 18 * dpi))
                continue
            }
            if (property == R.styleable.PlaceholderView_descriptionSize) {
                setDescriptionSize(typedArray.getDimension(property, 12 * dpi))
                continue
            }
            if (property == R.styleable.PlaceholderView_animation) {
                val drawable = typedArray.getResourceId(property, 0)
                animationResourceId = drawable
                continue
            }
            if (property == R.styleable.PlaceholderView_titleStyle) {
                setTitleTypeface(titleTypeface, typedArray.getInt(property, -1))
                continue
            }
            if (property == R.styleable.PlaceholderView_descriptionStyle) {
                setDescriptionTypeface(descriptionTypeface, typedArray.getInt(property, -1))
            }
        }
        typedArray.recycle()
    }

    /**
     * Sets description of this view
     * @param description Description res id
     */
    fun setDescription(@StringRes description: Int) {
        descriptionTextView.setText(description)
    }

    /**
     * Sets title of this view
     * @param title Title res id
     */
    fun setTitle(@StringRes title: Int) {
        titleTextView.setText(title)
    }

    /**
     * Description property
     */
    var description: String
        get() = descriptionTextView.text.toString()
        set(value) {
            descriptionTextView.text = value
        }

    /**
     * Title property
     */
    var title: String
        get() = titleTextView.text.toString()
        set(value) {
            titleTextView.text = value
        }

    @RawRes
    var animationResourceId: Int = 0
        set(value) {
            if (value != 0) {
                animationView.setAnimation(value)
            } else {
                animationView.visibility = View.GONE
            }
        }

    /**
     * Sets Title size
     * @param size Size
     */
    fun setTitleSize(size: Float) {
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size / dpi)
    }

    /**
     * Sets Description size
     * @param size Size
     */
    fun setDescriptionSize(size: Float) {
        descriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size / dpi)
    }

    /**
     * Sets title typeface
     * @param typeface Typeface (pass null to clear current typeface, or use getTitleTypeface())
     * @param style Style to set
     */
    fun setTitleTypeface(typeface: Typeface?, style: Int) {
        titleTextView.setTypeface(typeface, style)
    }

    /**
     * Sets description typeface
     * @param typeface Typeface (pass null to clear current typeface, or use getDescriptionTypeface())
     * @param style  Style to set
     */
    fun setDescriptionTypeface(typeface: Typeface?, style: Int) {
        descriptionTextView.setTypeface(typeface, style)
    }

    /**
     * Returns title typeface
     * @return Typeface
     */
    val titleTypeface: Typeface
        get() = titleTextView.typeface

    /**
     * Return description typeface
     * @return Typeface
     */
    val descriptionTypeface: Typeface
        get() = descriptionTextView.typeface


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_placeholder_view, this)
        descriptionTextView = findViewById(R.id.placeholder_desc_tv)
        titleTextView = findViewById(R.id.placeholder_title_tv)
        animationView = findViewById(R.id.animationView)
        dpi = Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()
        processProperties(context, attributeSet)

        // Default scale type for animation
        animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }
}