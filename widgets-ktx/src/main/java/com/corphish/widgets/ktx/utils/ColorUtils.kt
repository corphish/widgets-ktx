package com.corphish.widgets.ktx.utils

import android.graphics.Color

/**
 * Various utilities related to color.
 * Imported from CustomROMManager project.
 */
object ColorUtils {
    /**
     * Gets a darker shade of given color.
     *
     * @param color Color.
     * @return Darker shade of given color.
     */
    fun getDarkenedColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)

        hsv[2] *= 0.8f // value component

        return Color.HSVToColor(hsv)
    }

    /**
     * Gets a lighter shade of given color.
     *
     * @param color Color.
     * @param factor Lightness factor.
     * @return Lighter shade of given color.
     */
    fun getLightenedColor(color: Int, factor: Float): Int {
        val red = ((Color.red(color) * (1 - factor) / 255 + factor) * 255).toInt()
        val green = ((Color.green(color) * (1 - factor) / 255 + factor) * 255).toInt()
        val blue = ((Color.blue(color) * (1 - factor) / 255 + factor) * 255).toInt()

        return Color.argb(Color.alpha(color), red, green, blue)
    }

    /**
     * Gets a lighter shade of given color.
     *
     * @param color Color.
     * @return Lighter shade of given color.
     */
    fun getLightenedColor(color: Int): Int {
        return getLightenedColor(color, 0.8f)
    }

    /**
     * Returns a random dark color.
     * Dark color would look on a light background.
     *
     * @return Random dark color.
     */
    fun getRandomDarkColor(): Int {
        // RGB int array
        val rgb = IntArray(3)

        // Make one color possibly dominant
        val dominantIndex = (Math.random() * 3).toInt()
        rgb[dominantIndex] = 128 + (Math.random() * 128).toInt()

        // Fill out remaining colors
        for (i in 0..2) {
            if (rgb[i] == 0) {
                rgb[i] = 32 + (Math.random() * 96).toInt()
            }
        }
        return Color.rgb(rgb[0], rgb[1], rgb[2])
    }

    /**
     * Gets hex string representation of color int.
     *
     * @param color Color int value.
     * @return Hex value.
     */
    fun hexStringFromInt(color: Int): String? {
        return String.format("#%06X", 0xFFFFFF and color)
    }
}