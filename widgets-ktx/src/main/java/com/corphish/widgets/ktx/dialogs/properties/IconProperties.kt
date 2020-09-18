package com.corphish.widgets.ktx.dialogs.properties

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 * This class is used define properties of icon.
 * Exact image to be displayed is not a focus of this class,
 * but rather the looks of the icon, like the icon color, background
 * color etc.
 */
data class IconProperties(
        /**
         * Color of icon.
         * This will be applied to the foreground of the icon.
         */
        @ColorInt val iconColor: Int = Color.WHITE,

        /**
         * Background color of the icon.
         * This color will be applied in the background.
         * By default, platform default is applied.
         */
        @ColorInt val backgroundColor: Int = Int.MIN_VALUE,

        /**
         * Background drawable of the icon.
         * Use this property to supply custom shapes.
         * It is highly recommended that if you are using this property,
         * you should define the color in the drawable itself, as the
         * backgroundColor property is ignored if this is set.
         */
        val backgroundDrawable: Drawable? = null
)