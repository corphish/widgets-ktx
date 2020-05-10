package com.corphish.widgets.ktx.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic interface that allows definition of key RecyclerView adapter methods
 * This is done so that creating multiple adapters can be avoided
 * This is intended to be used with non-changing data only
 * While it is possible to work with changing data sets with variation
 * @param T Indicates the type of data, list of which shall be presented
 * @param V ViewHolder type
 *
 * This does not support multiple data sets so please combine your multiple data items into one single class
 */
interface StaticAdaptable<T, V: RecyclerView.ViewHolder> {
    /**
     * Layout resource for adapter view
     */
    fun getLayoutResource(): Int

    /**
     * List that is to be adapted
     */
    fun getListItems(): List<T>

    /**
     * ViewHolder for RV
     */
    fun getViewHolder(view: View, items: List<T>): V

    /**
     * onBind adapter method interface
     */
    fun bind(viewHolder: V, item: T)

    /**
     * Finally builds the adapter
     */
    fun buildAdapter(notifyDataSetChange: Boolean = true): StaticAdapter<T, V> {
        val adapter = StaticAdapter(this)
        if (notifyDataSetChange) adapter.notifyDataSetChanged()

        return adapter
    }
}