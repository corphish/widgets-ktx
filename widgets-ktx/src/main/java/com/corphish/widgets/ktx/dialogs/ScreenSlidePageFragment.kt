package com.corphish.widgets.ktx.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.corphish.widgets.ktx.R

/**
 * Slide fragment.
 */
class ScreenSlidePageFragment : Fragment() {
    // Properties
    private var titleResId = 0
    private var titleString = ""
    private var useDialogTitle = false
    private var messageRes = 0
    private var messageString = ""
    private var animation = 0
    private var dialogTitleRes = 0
    private var dialogTitleString = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.layout_on_boarding_item, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        titleResId = arguments?.getInt(TITLE_RES) ?: 0
        titleString = arguments?.getString(TITLE_STRING) ?: ""
        useDialogTitle = arguments?.getBoolean(USE_DIALOG_TITLE) ?: false
        messageRes = arguments?.getInt(MESSAGE_RES) ?: 0
        messageString = arguments?.getString(MESSAGE_STRING) ?: ""
        animation = arguments?.getInt(ANIMATION) ?: 0
        dialogTitleRes = arguments?.getInt(DIALOG_TITLE_RES) ?: 0
        dialogTitleString = arguments?.getString(DIALOG_TITLE_STRING) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.alertTitle)
        val message = view.findViewById<TextView>(R.id.alertMessage)
        val animationView = view.findViewById<LottieAnimationView>(R.id.animationView)

        val slide = OnBoardingDialog.Slide(titleString, titleResId, useDialogTitle, messageString, messageRes, animation)

        if (slide.useDialogTitle) {
            if (dialogTitleRes != 0) {
                title.setText(dialogTitleRes)
            } else {
                title.text = dialogTitleString
            }
        } else {
            if (slide.titleResId != 0) {
                title.setText(slide.titleResId)
            } else {
                title.text = slide.titleString
            }
        }

        if (slide.messageResId != 0) {
            message.setText(slide.messageResId)
        } else {
            message.text = slide.messageString
        }

        if (slide.animation != 0) {
            animationView.setAnimation(slide.animation)
            animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        } else {
            animationView.visibility = View.GONE
        }

        view.forceLayout()
    }

    companion object {
        const val TITLE_RES = "title_res"
        const val TITLE_STRING = "title_string"
        const val USE_DIALOG_TITLE = "use_dialog_title"
        const val MESSAGE_RES = "message_res"
        const val MESSAGE_STRING = "message_string"
        const val ANIMATION = "animation"
        const val DIALOG_TITLE_RES = "dialog_title_res"
        const val DIALOG_TITLE_STRING = "dialog_title_string"
    }
}