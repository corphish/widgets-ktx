package com.corphish.widgets.ktx.intro.models

import android.content.Context

/**
 * This interface will be used to implement preference
 * logic which will be used to determine whether app intro
 * must be shown to the user or not.
 */
interface PreferenceLogic {
    /**
     * Method which determines whether app intro should
     * be shown to the user or not.
     *
     * @param context Context.
     * @return True if app intro should be shown, false
     *         otherwise.
     */
    fun shouldShowAppIntro(context: Context): Boolean

    /**
     * Notifies that intro is shown. Necessary persistence
     * operations needs to be performed in this method.
     *
     * @param context Context.
     */
    fun notifyIntroShown(context: Context)
}