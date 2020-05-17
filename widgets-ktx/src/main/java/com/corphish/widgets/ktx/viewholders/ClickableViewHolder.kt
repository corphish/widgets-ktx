package com.corphish.widgets.ktx.viewholders

import android.view.View

/**
 * Extension of BaseViewHolder with clickable capabilities
 */
class ClickableViewHolder @JvmOverloads constructor(view: View,
                          viewList: List<Int> = listOf(),
                          private val _onClickListenerJ: OnClickListener? = null, 
                          private val _onClickListenerK: (View, Int) -> Unit = { _, _ -> }): BasicViewHolder(view, viewList), View.OnClickListener {
    override fun onClick(v: View) {
        _onClickListenerJ?.onClick(v, adapterPosition)
        _onClickListenerK(v, adapterPosition)
    }

    init {
        view.setOnClickListener(this)
    }

    interface OnClickListener {
        fun onClick(v: View, adapterPosition: Int)
    }
}
