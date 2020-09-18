package com.corphish.widgets.ktx.extensions

import android.graphics.Color
import com.corphish.widgets.ktx.utils.ColorModel

// This file contains various string extensions.

/**
 * Returns a color model for given string.
 * Color framework is used to translate the color.
 */
fun String.asColor() = ColorModel(Color.parseColor(this))