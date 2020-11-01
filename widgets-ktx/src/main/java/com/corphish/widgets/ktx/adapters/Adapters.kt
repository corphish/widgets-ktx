package com.corphish.widgets.ktx.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corphish.widgets.ktx.R
import com.corphish.widgets.ktx.viewholders.BasicViewHolder
import com.corphish.widgets.ktx.viewholders.ClickableViewHolder

/**
 * Helper class to create adapters.
 */
object Adapters {
    /**
     * This class holds the properties of adapter.
     *
     * @param T Type of item.
     * @param V Type of ViewHolder.
     */
    class AdapterProperties<T, V : RecyclerView.ViewHolder> {
        /**
         * Property for layout resource id.
         * Now that we can have different layout resource ids for
         * different type, we need to have a generator for this.
         *
         * For demo purposes, we utilise the view type parameter but we
         * return same layout for every type.
         */
        var layoutResourceId: (Int) -> Int = {
            when (it) {
                0 -> R.layout.layout_item
                else -> R.layout.layout_item
            }
        }

        /**
         * Property for listItems.
         */
        var listItems: List<T> = ArrayList()

        /**
         * Property for view holder.
         *
         * For demo purposes, we utilise the type parameter.
         */
        var viewHolder: (View, Int) -> V = { v, type ->
            when (type) {
                0 -> BasicViewHolder(v, listOf(R.id.item)) as V
                else -> ClickableViewHolder(v, listOf(R.id.item)) as V
            }
        }

        /**
         * Property for binding.
         */
        var binding: (V, Int) -> Unit = { vh, pos ->
            val type = viewTypeGenerator(pos)
            val viewHolder = if (type == 0) vh as BasicViewHolder else vh as ClickableViewHolder
            viewHolder.getViewById<TextView>(R.id.item)?.text = listItems[pos] as String
        }

        /**
         * ViewType property.
         * Returns the view type for given item position.
         */
        var viewTypeGenerator: (Int) -> Int = { 0 }

        /**
         * Property for notifyDataSetChanged.
         */
        var notifyDataSetChanged: Boolean = false
    }

    /**
     * Lets you create a static adapter
     * @param T Item type
     * @param V ViewHolder type
     * @param block In this block you can define the properties of ViewHolder
     * @return Static Adapter created based on the set properties
     */
    fun <T, V : RecyclerView.ViewHolder> newImmutableListAdapter(block: AdapterProperties<T, V>.() -> Unit): ImmutableListAdapter<T, V> {
        val props = AdapterProperties<T, V>()

        props.block()

        return object : ListAdaptable<T, V>() {
            override fun getLayoutResource(viewType: Int) = props.layoutResourceId(viewType)
            override fun getListItems() = props.listItems
            override fun getViewHolder(view: View, viewType: Int) = props.viewHolder(view, viewType)
            override fun bind(viewHolder: V, position: Int) {
                props.binding(viewHolder, position)
            }
        }.buildImmutableListAdapter(props.notifyDataSetChanged)
    }

}