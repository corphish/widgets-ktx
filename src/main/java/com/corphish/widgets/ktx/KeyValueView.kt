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

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

/**
 * Key Value view
 * A container having 2 text views
 * One showing the heading or key and the other one showing its value
 * Written as separate library cause it is used it widely
 */
class KeyValueView(context: Context,
                   attributeSet: AttributeSet? = null,
                   defStyle: Int = 0) : LinearLayout(context, attributeSet, defStyle) {
    /**
     * Just in case user wants to use the full capabilities of a TextView or do advanced stuff
     * @return Key
     */
    /**
     * 2 text views
     * We will work with these 2
     */
    private var keyTextView: TextView
    /**
     * Just in case user wants to use the full capabilities of a TextView or do advanced stuff
     * @return Value
     */
    private var valueTextView: TextView

    // To handle sizes
    private var dpi: Float

    // Prefer method set value over property set
    private var methodSetValue = false

    private fun processProperties(context: Context, attributeSet: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.KeyValueView)
        val count = typedArray.indexCount

        for (i in 0 until count) {
            val property = typedArray.getIndex(i)
            if (property == R.styleable.KeyValueView_keyText) {
                keyText = typedArray.getText(property).toString()
                continue
            }
            if (property == R.styleable.KeyValueView_valueText) {
                if (!methodSetValue) valueText = typedArray.getText(property).toString()
                continue
            }
            if (property == R.styleable.KeyValueView_keySize) {
                setKeySize(typedArray.getDimension(property, keyTextView.textSize))
                continue
            }
            if (property == R.styleable.KeyValueView_valueSize) {
                setValueSize(typedArray.getDimension(property, valueTextView.textSize))
                continue
            }
            if (property == R.styleable.KeyValueView_keyEnabled) {
                setEnabled(typedArray.getBoolean(property, true), valueTextView.isEnabled)
                continue
            }
            if (property == R.styleable.KeyValueView_valueEnabled) {
                setEnabled(keyTextView.isEnabled, typedArray.getBoolean(property, true))
                continue
            }
            if (property == R.styleable.KeyValueView_keyStyle) {
                setKeyTypeface(keyTypeface, typedArray.getInt(property, -1))
                continue
            }
            if (property == R.styleable.KeyValueView_valueStyle) {
                setValueTypeface(valueTypeface, typedArray.getInt(property, -1))
                continue
            }
            if (property == R.styleable.KeyValueView_absoluteSpacing) {
                setAbsoluteSpacing(typedArray.getDimensionPixelSize(property, 1))
                continue
            }
            if (property == R.styleable.KeyValueView_absoluteSpacingTop) {
                setAbsoluteSpacing(typedArray.getDimensionPixelSize(property, 1), valueTextView.paddingTop)
                continue
            }
            if (property == R.styleable.KeyValueView_absoluteSpacingBottom) {
                setAbsoluteSpacing(keyTextView.paddingBottom, typedArray.getDimensionPixelSize(property, 1))
                continue
            }
            if (property == R.styleable.KeyValueView_relativeSpacing) {
                setRelativeSpacing(typedArray.getDimensionPixelSize(property, 1))
                continue
            }
            if (property == R.styleable.KeyValueView_relativeSpacingTop) {
                setRelativeSpacing(typedArray.getDimensionPixelSize(property, 1), valueTextView.paddingTop)
                continue
            }
            if (property == R.styleable.KeyValueView_relativeSpacingBottom) {
                setRelativeSpacing(keyTextView.paddingBottom, typedArray.getDimensionPixelSize(property, 1))
                continue
            }
            if (property == R.styleable.KeyValueView_keyTextColor) {
                keyTextColor = typedArray.getColor(property, keyTextView.currentTextColor)
                continue
            }
            if (property == R.styleable.KeyValueView_valueTextColor) {
                valueTextColor = typedArray.getColor(property, valueTextView.currentTextColor)
                continue
            }
            if (property == R.styleable.KeyValueView_keyBackgroundColor) {
                var color = Color.TRANSPARENT
                if (keyTextView.background is ColorDrawable) {
                    color = (keyTextView.background as ColorDrawable).color
                }
                keyBackgroundColor = typedArray.getColor(property, color)
                continue
            }
            if (property == R.styleable.KeyValueView_valueBackgroundColor) {
                var color = Color.TRANSPARENT
                if (valueTextView.background is ColorDrawable) {
                    color = (valueTextView.background as ColorDrawable).color
                }
                valueBackgroundColor = typedArray.getColor(property, color)
                continue
            }
            if (property == R.styleable.KeyValueView_methodForValue) {
                check(!context.isRestricted) { "The app:methodForValue cannot be used in a restricted context" }
                val methodName = typedArray.getString(property)
                methodName?.let { processMethod(it) }
            }
            if (property == R.styleable.KeyValueView_keyAppearance) {
                setKeyTextAppearance(typedArray.getResourceId(property, android.R.attr.textAppearance))
            }
            if (property == R.styleable.KeyValueView_valueAppearance) {
                setValueTextAppearance(typedArray.getResourceId(property, android.R.attr.textAppearanceSmall))
            }
        }
        typedArray.recycle()
    }

    private fun processMethod(methodName: String) {
        // This method expects the method in form of domainname.orgname.projectname.className.subClassName.methodName
        // Either way, the last part must be the method, the rest must be class
        val className: String
        val context = context
        val methodExtracted = methodName.substring(methodName.lastIndexOf(".") + 1)
        className = if (methodName.contains(".")) methodName.substring(0, methodName.lastIndexOf(".")) else {
            //throw new IllegalArgumentException("The method name supplied must be absolute");
            context.javaClass.name
        }
        try {
            val clazz = Class.forName(className)
            val method = clazz.getDeclaredMethod(methodExtracted)
            val value = method.invoke(context) as String
            valueText = value
            methodSetValue = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Sets text into the **Key** field (or TextView in this case)
     * @param resId String resource id
     */
    fun setKeyText(@StringRes resId: Int) {
        keyTextView.setText(resId)
    }

    /**
     * Sets text into the **Value** field (or TextView in this case)
     * @param resId String resource id
     */
    fun setValueText(@StringRes resId: Int) {
        valueTextView.setText(resId)
    }

    /**
     * Property access for key text
     */
    var keyText: String
        get() = keyTextView.text.toString()
        set(value) { keyTextView.text = value }

    /**
     * Property access for value text
     */
    var valueText: String
        get() = valueTextView.text.toString()
        set(value) { valueTextView.text = value }

    /**
     * Enables or disables each view as needed
     * @param keyEnabled Whether or not to enable Key
     * @param valueEnabled Whether or not to enable Value
     */
    fun setEnabled(keyEnabled: Boolean, valueEnabled: Boolean) {
        // Set the key and value enabled as required
        keyTextView.isEnabled = keyEnabled
        valueTextView.isEnabled = valueEnabled

        // Disable the entire view if all of them are false
        // Enable if any one of them is true
        super.setEnabled(keyEnabled or valueEnabled)
    }

    /**
     * Enables or disables the entire view
     * @param enabled Whether or not to enable this view
     */
    override fun setEnabled(enabled: Boolean) {
        this.setEnabled(enabled, enabled)
    }

    /**
     * Sets Key size
     * @param size Size
     */
    fun setKeySize(size: Float) {
        keyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size / dpi)
    }

    /**
     * Sets Value size
     * @param size Size
     */
    fun setValueSize(size: Float) {
        valueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size / dpi)
    }

    /**
     * Sets the padding of the 2 text views
     * @param dp Padding in dp
     */
    fun setPadding(dp: Int) {
        keyTextView.setPadding(dp, dp, dp, dp)
        valueTextView.setPadding(dp, dp, dp, dp)
    }

    /**
     * Sets absolute vertical spacing in between the Key and Value
     * The passed parameters are applied directly
     * @param spacingBelowKey Spacing below key in dp
     * @param spacingAboveValue Spacing above value in dp
     */
    fun setAbsoluteSpacing(spacingBelowKey: Int, spacingAboveValue: Int) {
        keyTextView.setPadding(keyTextView.paddingLeft, keyTextView.paddingTop, keyTextView.paddingRight, spacingBelowKey)
        valueTextView.setPadding(valueTextView.paddingLeft, spacingAboveValue, valueTextView.paddingRight, valueTextView.paddingBottom)
    }

    /**
     * Sets absolute vertical spacing in between the Key and Value
     * @param spacing Spacing in dp
     */
    fun setAbsoluteSpacing(spacing: Int) {
        setAbsoluteSpacing(spacing, spacing)
    }

    /**
     * Sets relative spacing in between the Key and Value
     * Use this to increase or decrease the spacing
     * A positive value means spacing will be increased, a negative value
     * @param spacingBelowKey Spacing in dp
     * @param spacingAboveValue Spacing in dp
     */
    fun setRelativeSpacing(spacingBelowKey: Int, spacingAboveValue: Int) {
        setAbsoluteSpacing(keyTextView.paddingBottom + spacingBelowKey, valueTextView.paddingTop + spacingAboveValue)
    }

    /**
     * Sets relative spacing in between the Key and Value
     * Use this to increase or decrease the spacing
     * A positive value means spacing will be increased, a negative value
     * @param spacing Spacing in dp
     */
    fun setRelativeSpacing(spacing: Int) {
        setRelativeSpacing(spacing, spacing)
    }

    /**
     * Sets typeface for Key
     * @param typeface Typeface
     * @param style Text Style
     */
    fun setKeyTypeface(typeface: Typeface?, style: Int) {
        keyTextView.setTypeface(typeface, style)
    }

    /**
     * Sets typeface for Value
     * @param typeface Typeface
     * @param style Text Style
     */
    fun setValueTypeface(typeface: Typeface?, style: Int) {
        valueTextView.setTypeface(typeface, style)
    }

    /**
     * Gets typeface of key
     * @return Key typeface
     */
    val keyTypeface: Typeface
        get() = keyTextView.typeface

    /**
     * Gets value typeface
     * @return Value typeface
     */
    val valueTypeface: Typeface
        get() = valueTextView.typeface

    /**
     * Key text color property
     */
    var keyTextColor: Int
        get() = keyTextView.currentTextColor
        set(value) {
            keyTextView.setTextColor(value)
        }

    /**
     * Value text color property
     */
    var valueTextColor: Int
        get() = valueTextView.currentTextColor
        set(value) {
            valueTextView.setTextColor(value)
        }

    /**
     * Value background color property
     */
    var keyBackgroundColor: Int
        get() = (keyTextView.background as ColorDrawable).color
        set(value) {
            valueTextView.setBackgroundColor(value)
        }

    /**
     * Value background color property
     */
    var valueBackgroundColor: Int
        get() = (valueTextView.background as ColorDrawable).color
        set(value) {
            valueTextView.setBackgroundColor(value)
        }

    /**
     * Set drawables for key
     * @param left Left drawable
     * @param top Top drawable
     * @param right Right drawable
     * @param bottom Bottom drawable
     */
    fun setKeyDrawables(@DrawableRes left: Int, @DrawableRes top: Int, @DrawableRes right: Int, @DrawableRes bottom: Int) {
        keyTextView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    /**
     * Set drawables for value
     * @param left Left drawable
     * @param top Top drawable
     * @param right Right drawable
     * @param bottom Bottom drawable
     */
    fun setValueDrawables(@DrawableRes left: Int, @DrawableRes top: Int, @DrawableRes right: Int, @DrawableRes bottom: Int) {
        valueTextView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    /**
     * Set relative drawables for key
     * @param start Start drawable
     * @param top Top drawable
     * @param end End drawable
     * @param bottom Bottom drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setKeyRelativeDrawables(@DrawableRes start: Int, @DrawableRes top: Int, @DrawableRes end: Int, @DrawableRes bottom: Int) {
        keyTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    }

    /**
     * Set relative drawables for value
     * @param start Start drawable
     * @param top Top drawable
     * @param end End drawable
     * @param bottom Bottom drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setValueRelativeDrawables(@DrawableRes start: Int, @DrawableRes top: Int, @DrawableRes end: Int, @DrawableRes bottom: Int) {
        valueTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
    }

    /**
     * Sets text appearance of key
     * @param appearance Appearance
     */
    fun setKeyTextAppearance(@StyleRes appearance: Int) {
        TextViewCompat.setTextAppearance(keyTextView, appearance)
    }

    /**
     * Sets text appearance of value
     * @param appearance Appearance
     */
    fun setValueTextAppearance(@StyleRes appearance: Int) {
        TextViewCompat.setTextAppearance(valueTextView, appearance)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_key_value, this)
        keyTextView = findViewById(R.id.key)
        valueTextView = findViewById(R.id.value)
        dpi = Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()
        processProperties(context, attributeSet)
    }
}