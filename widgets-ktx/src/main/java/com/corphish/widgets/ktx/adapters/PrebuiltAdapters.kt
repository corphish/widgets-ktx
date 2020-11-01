package com.corphish.widgets.ktx.adapters

import android.view.View
import android.widget.TextView
import com.corphish.widgets.ktx.R
import com.corphish.widgets.ktx.viewholders.BasicViewHolder

/**
 * Provides ready-made adapters for use.
 * Intended for demo but you can use it nevertheless.
 */
object PrebuiltAdapters {
    /**
     * Returns a recyclerView adapter which can display single line items.
     *
     * @param items Items to display.
     * @return Adapter.
     */
    fun singleItemAdapterWith(items: List<String>) =
            object: ImmutableListAdaptable<String, BasicViewHolder>() {
                override fun getLayoutResource(viewType: Int) = R.layout.layout_item
                override fun getListItems() = items
                override fun getViewHolder(view: View, viewType: Int) =
                        BasicViewHolder(view, listOf(R.id.item))

                override fun bind(viewHolder: BasicViewHolder, position: Int) {
                    viewHolder.getViewById<TextView>(R.id.item)?.text = getListItems()[position]
                }
            }.buildAdapter(true)

    /**
     * Returns a recyclerView adapter which can display a pair of items.
     * Its layout is same as KeyValueView.
     *
     * @param items Items to display.
     * @return Adapter.
     */
    fun keyValueItemAdapterWith(items: List<Pair<String, String>>) =
            object: ImmutableListAdaptable<Pair<String, String>, BasicViewHolder>() {
                override fun getLayoutResource(viewType: Int) = R.layout.layout_key_value
                override fun getListItems() = items
                override fun getViewHolder(view: View, viewType: Int) =
                        BasicViewHolder(view, listOf(R.id.key, R.id.value))

                override fun bind(viewHolder: BasicViewHolder, position: Int) {
                    val item = getListItems()[position]
                    viewHolder.getViewById<TextView>(R.id.key)?.text = item.first
                    viewHolder.getViewById<TextView>(R.id.value)?.text = item.second
                }
            }.buildAdapter(true)
}