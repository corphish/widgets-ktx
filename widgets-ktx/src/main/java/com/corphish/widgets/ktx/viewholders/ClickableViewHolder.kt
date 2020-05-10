package com.corphish.widgets.ktx.viewholders

import android.view.View

/**
 * Extension of BaseViewHolder with clickable capabilities
 */
class ClickableViewHolder(view: View, viewList: List<Int>, private val _onClickListener: OnClickListener) : BasicViewHolder(view, viewList), View.OnClickListener {
    override fun onClick(v: View) {
        _onClickListener.onClick(v, adapterPosition)
    }

    interface OnClickListener {
        fun onClick(view: View?, adapterPosition: Int)
    }

    init {
        view.setOnClickListener(this)
    }
}
