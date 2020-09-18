package com.corphish.widgets.ktx.extensions.views

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import com.corphish.widgets.ktx.dialogs.properties.IconProperties
import com.google.android.material.button.MaterialButton

// Internal class to have extensions for MaterialButtons

fun MaterialButton.applyIconProperties(iconProperties: IconProperties) {
    // Set background drawable first
    if (iconProperties.backgroundDrawable != null) {
        this.background = iconProperties.backgroundDrawable
    } else {
        this.backgroundTintList = ColorStateList.valueOf(iconProperties.backgroundColor)
    }

    // Apply icon color
    this.iconTint = ColorStateList.valueOf(iconProperties.iconColor)
}