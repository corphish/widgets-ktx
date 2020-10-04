package com.corphish.widgets.ktx.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.corphish.widgets.ktx.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IllegalArgumentException

/**
 * Shows a dialog with list of slides which can show
 * some information to user. Can be used for on boarding if you will.
 */
class OnBoardingDialog(val context: Context) {
    /**
     * Title of this dialog.
     */
    var titleString = ""

    @StringRes
    var titleResId = 0

    /**
     * Slide list
     */
    var slides: List<Slide> = ArrayList()

    /**
     * Data class to hold each slide information.
     */
    data class Slide(
            /**
             * Title of this slide.
             */
            val titleString: String = "",

            /**
             * Title res id.
             * Preferred more over titleString
             */
            @StringRes val titleResId: Int = 0,

            /**
             * Boolean indicating whether the dialog title
             * is used or not. If this is true, the titles set
             * in this slide will be ignored.
             */
            val useDialogTitle: Boolean = false,

            /**
             * Message of this slide.
             * For best results, use one line message per
             * slide
             */
            val messageString: String = "",

            /**
             * Message res id.
             * Preferred more over messageString
             */
            @StringRes val messageResId: Int = 0,

            /**
             * Animation for this slide.
             * If this is not set, the default animation is used.
             */
            @RawRes val animation: Int = 0
    )

    fun show() {
        // Warn if the passed context is not a fragment activity
        if (context !is FragmentActivity) {
            throw IllegalArgumentException("Must pass a FragmentActivity")
        }

        /*
         * Alert dialog builder
         */
        val builder = AlertDialog.Builder(context)

        // Inflate specific view
        val view = LayoutInflater.from(context).inflate(R.layout.layout_on_boarding_dialog, null)

        // Hook views
        val viewPager = view.findViewById<ViewPager2>(R.id.pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        val pagerAdapter = ScreenSlidePagerAdapter(context)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
            view.requestLayout()
        }.attach()

        builder.setView(view)
        builder.setCancelable(true)
        builder.show()
    }

    /**
     * A pager adapter to control the slides.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = slides.size

        override fun createFragment(position: Int): Fragment {
            val slidePageFragment = ScreenSlidePageFragment()
            val bundle = Bundle()

            bundle.putInt(ScreenSlidePageFragment.TITLE_RES, slides[position].titleResId)
            bundle.putInt(ScreenSlidePageFragment.MESSAGE_RES, slides[position].messageResId)
            bundle.putInt(ScreenSlidePageFragment.ANIMATION, slides[position].animation)
            bundle.putInt(ScreenSlidePageFragment.DIALOG_TITLE_RES, titleResId)
            bundle.putString(ScreenSlidePageFragment.TITLE_STRING, slides[position].titleString)
            bundle.putString(ScreenSlidePageFragment.MESSAGE_STRING, slides[position].messageString)
            bundle.putString(ScreenSlidePageFragment.DIALOG_TITLE_STRING, titleString)
            bundle.putBoolean(ScreenSlidePageFragment.USE_DIALOG_TITLE, slides[position].useDialogTitle)

            slidePageFragment.arguments =  bundle

            return slidePageFragment
        }
    }
}

