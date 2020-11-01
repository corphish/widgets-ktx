package com.corphish.widgets.ktx.intro.models

import android.content.Context
import androidx.preference.PreferenceManager

data class BooleanPreferenceLogic(
        /**
         * Preference key.
         * This key must store a boolean value, otherwise
         * class cast exceptions will happen.
         */
        val preferenceKey: String,

        /**
         * By default, if the app intro needs to be shown,
         * the value returned by the preference key must be true.
         * It should also return true in case if it is not set.
         * Once the intro is shown, we update the preference key
         * with false value.
         * If for some reason this is not a preferred behavior, this
         * can be inverted by setting the value of this variable to
         * true.
         */
        val invertValueForPersistence: Boolean = false,
) : PreferenceLogic {

    override fun shouldShowAppIntro(context: Context): Boolean {
        val ret = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(preferenceKey, !invertValueForPersistence)

        return if (invertValueForPersistence) ret.not() else ret
    }

    /**
     * Notifies that intro is shown. Necessary persistence
     * operations needs to be performed in this method.
     *
     * In this case we set the preference key value to be false.
     * However, depending on the inverted value, it can be set to true.
     *
     * @param context Context.
     */
    override fun notifyIntroShown(context: Context) {
        TODO("Not yet implemented")
    }

}