package com.corphish.widgets.ktx.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic interface that allows definition of key RecyclerView adapter methods.
 * This is done so that creating multiple adapters can be avoided.
 * This is intended to be used with non-changing data only.
 * Additionally this supports mixed views by supporting multiple view types and view holders.
 *
 * @param T Indicates the type of data, list of which shall be presented.
 * @param V Indicates the base type of ViewHolder to be used.
 *
 * This does not support multiple data sets so please combine your multiple data items into one single class
 */
abstract class StaticMixedAdaptable<T, V: RecyclerView.ViewHolder> {
    /**
     * Layout resource for adapter view.
     */
    abstract fun getLayoutResource(viewType: Int): Int

    /**
     * List that is to be adapted.
     */
    abstract fun getListItems(): List<T>

    /**
     * ViewHolder for RV.
     */
    abstract fun getViewHolder(view: View, viewType: Int): V

    /**
     * Gets view type for given position.
     */
    abstract fun getViewType(position: Int): Int

    /**
     * onBind adapter method interface.
     */
    abstract fun bind(viewHolder: V, item: T)

    /**
     * Finally builds the adapter.
     */
    fun buildAdapter(notifyDataSetChange: Boolean = true): StaticMixedAdapter<T, V> {
        val adapter = StaticMixedAdapter(this)
        if (notifyDataSetChange) adapter.notifyDataSetChanged()

        return adapter
    }
}