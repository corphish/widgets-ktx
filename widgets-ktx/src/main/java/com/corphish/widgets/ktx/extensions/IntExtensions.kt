package com.corphish.widgets.ktx.extensions

import com.corphish.widgets.ktx.utils.ColorModel

// This file contains various int extensions.

/**
 * Wraps up a int value as color.
 * TODO: Add checks to validate color.
 */
fun Int.asColor() = ColorModel(this)