package com.corphish.widgets.ktx.viewholders

import android.view.View

/**
 * Extension of BaseViewHolder with clickable capabilities
 */
class ClickableViewHolder(view: View, viewList: List<Int> = listOf(), private val _onClickListener: (View, Int) -> Unit) : BasicViewHolder(view, viewList), View.OnClickListener {
    override fun onClick(v: View) {
        _onClickListener(v, adapterPosition)
    }

    init {
        view.setOnClickListener(this)
    }
}
