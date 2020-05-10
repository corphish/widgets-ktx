package com.corphish.widgets.ktx.adapters

import android.view.View
import android.widget.TextView
import com.corphish.widgets.ktx.R
import com.corphish.widgets.ktx.viewholders.BasicViewHolder

/**
 * Provides ready-made adapters for use
 * Intended for demo but you can use it nevertheless
 */
object Adapters {
    /**
     * Returns a recyclerView adapter which can display single line items
     * @param items Items to display
     * @return Adapter
     */
    fun singleItemAdapterWith(items: List<String>) =
            object: StaticAdaptable<String, BasicViewHolder> {
                override fun getLayoutResource() = R.layout.layout_item
                override fun getListItems() = items
                override fun getViewHolder(view: View, items: List<String>) =
                        BasicViewHolder(view, listOf(R.id.item))

                override fun bind(viewHolder: BasicViewHolder, item: String) {
                    viewHolder.getViewById<TextView>(R.id.item)?.text = item
                }
            }.buildAdapter(true)

    /**
     * Returns a recyclerView adapter which can display a pair of items
     * Its layout is same as KeyValueView
     * @param items Items to display
     * @return Adapter
     */
    fun keyValueItemAdapterWith(items: List<Pair<String, String>>) =
            object: StaticAdaptable<Pair<String, String>, BasicViewHolder> {
                override fun getLayoutResource() = R.layout.layout_item
                override fun getListItems() = items
                override fun getViewHolder(view: View, items: List<Pair<String, String>>) =
                        BasicViewHolder(view, listOf(R.id.key, R.id.value))

                override fun bind(viewHolder: BasicViewHolder, item: Pair<String, String>) {
                    viewHolder.getViewById<TextView>(R.id.key)?.text = item.first
                    viewHolder.getViewById<TextView>(R.id.value)?.text = item.second
                }
            }.buildAdapter(true)
}