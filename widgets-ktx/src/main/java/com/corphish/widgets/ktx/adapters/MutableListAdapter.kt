package com.corphish.widgets.ktx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Provides an adapter that can be used in recyclerview.
 * It builds on an adaptable.
 * The adapter returned can be used to work with mutable data sets.
 *
 * @param T Type of data item
 * @param V RecyclerView ViewHolder
 * @property adaptable Adaptable from which the adapter will be built
 */
class MutableListAdapter<T, V : RecyclerView.ViewHolder>(private val adaptable: MutableListAdaptable<T, V>,
                                                         diffUtil: DiffUtil.ItemCallback<T>) :
        ListAdapter<T, V>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            adaptable.getViewHolder(
                    LayoutInflater.from(parent.context).inflate(adaptable.getLayoutResource(viewType), parent, false),
                    viewType
            )

    override fun onBindViewHolder(holder: V, position: Int) {
        adaptable.bind(holder, getItem(position), position)
    }

    override fun getItemViewType(position: Int) = adaptable.getViewType(position)

    /**
     * Updates the list to be displayed.
     * Due to the way it is coded, in order for [DiffUtil.ItemCallback] to trigger,
     * the new list passed to the [ListAdapter.submitList] method must be a new list.
     * Which is why this method exists, so that it can supply a new updated list to
     * the submitList method.
     *
     * In order to generate the new list, it is copied into a new ArrayList. If it
     * is not a recommended approach for your use case, consider using the submitList
     * method itself, but make sure to supply to a new list otherwise changes will not
     * be reflected.
     *
     * For any other use cases, where the list supplied is a new list, you can either
     * set the copy parameter as false or use  the submitList itself.
     *
     * @param list New list to update.
     * @param copy Boolean indicating whether or not to copy the supplied list to a
     *             new list by default or not.
     */
    fun updateList(list: List<T>, copy: Boolean = true) {
        // Make a new list if copy is enabled.
        val newList = if (copy) ArrayList(list) else list

        // Submit the new list.
        submitList(newList)
    }
}