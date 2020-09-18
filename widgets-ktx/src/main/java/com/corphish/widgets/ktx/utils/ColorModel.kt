package com.corphish.widgets.ktx.utils

import androidx.annotation.ColorInt

/**
 * Wrapper class for storing color
 */
data class ColorModel(
        /**
         * Color int
         */
        @ColorInt val colorInt: Int
) {
        /**
         * Gets a dark version of this color.
         *
         * @param times Number of iterations to darken the color.
         * @return Darkened color.
         */
        fun getDarkerShade(times: Int = 1): Int {
                var darkerColor = colorInt

                for (i in 1..times) {
                        darkerColor = ColorUtils.getDarkenedColor(darkerColor)
                }

                return darkerColor
        }
        /**
         * Gets a lighter version of this color.
         *
         * @param times Number of iterations to lighten the color.
         * @return Lightened color.
         */
        fun getLighterShade(times: Int = 1): Int {
                var lighterColor = colorInt

                for (i in 1..times) {
                        lighterColor = ColorUtils.getLightenedColor(lighterColor)
                }

                return lighterColor
        }

}