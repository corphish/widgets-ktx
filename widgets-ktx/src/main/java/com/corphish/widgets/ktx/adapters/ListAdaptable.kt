package com.corphish.widgets.ktx.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil
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
abstract class ListAdaptable<T, V : RecyclerView.ViewHolder> {
    /**
     * Layout resource for adapter view.
     *
     * @param viewType Optional parameter to determine different
     *                 layouts based on view type.
     * @return Layout resource id of the list item.
     */
    abstract fun getLayoutResource(viewType: Int = 0): Int

    /**
     * List that is to be adapted.
     *
     * @return List items to be shown in the recyclerview.
     */
    abstract fun getListItems(): List<T>

    /**
     * Generates the ViewHolder for the RecyclerView.
     *
     * @param view A view needs to be passed to generate a ViewHolder.
     * @param viewType Based on the viewType, different ViewHolders can
     *                 be generated.
     * @return ViewHolder. Please adjust the ViewHolder type parameter of the
     *         class accordingly if you are planning to use multiple ViewHolder.
     */
    abstract fun getViewHolder(view: View, viewType: Int = 0): V

    /**
     * Gets view type for given position.
     *
     * @param Position of item whose type needs to be determined.
     * @return Type of the view. This result can be now used in thh
     *         [getLayoutResource] and [getViewHolder] methods.
     */
    open fun getViewType(position: Int): Int = 0

    /**
     * onBind adapter method interface. This is where the binding logic needs
     * to be supplied.
     *
     * @param viewHolder ViewHolder.
     * @param position Position of the item to populate. Position is supplied so that
     *                 proper ViewHolder and item type can be determined. You can
     *                 retrieve the item by calling the [getListItems()[position]] method.
     */
    abstract fun bind(viewHolder: V, position: Int)

    /**
     * Builds an immutable list adapter, which can be supplied to the RecyclerView.
     *
     * @param notifyDataSetChanged Optional parameter indicating whether the adapter
     *                            method `notifyDataSetChanged()` needs to be called
     *                            immediately after adapter creation.
     * @return Adapter.
     */
    fun buildImmutableListAdapter(notifyDataSetChanged: Boolean = true): ImmutableListAdapter<T, V> {
        val adapter = ImmutableListAdapter(this)

        if (notifyDataSetChanged) {
            adapter.notifyDataSetChanged()
        }

        return adapter
    }

    /**
     * Builds a mutable list adapter, which can be supplied to the RecyclerView.
     *
     * @param notifyDataSetChanged Optional parameter indicating whether the adapter
     *                            method `notifyDataSetChanged()` needs to be called
     *                            immediately after adapter creation.
     * @return Adapter.
     */
    fun buildMutableListAdapter(diffUtilCallback: DiffUtil.ItemCallback<T>, notifyDataSetChanged: Boolean = true): MutableListAdapter<T, V> {
        val adapter = MutableListAdapter(this, diffUtilCallback)

        if (notifyDataSetChanged) {
            adapter.notifyDataSetChanged()
        }

        return adapter
    }
}