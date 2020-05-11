package com.corphish.widgets.ktx.adapters

import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.corphish.widgets.ktx.R
import com.corphish.widgets.ktx.viewholders.BasicViewHolder

/**
 * Helper class to create adapters
 */
object Adapters {
    class AdapterProperties<T, V: RecyclerView.ViewHolder> {
        /**
         * Property for layout resource id
         */
        @LayoutRes
        var layoutResourceId: Int = R.layout.layout_item

        /**
         * Property for listItems
         */
        var listItems: List<T> = ArrayList<T>()

        /**
         * Property for view holder
         */
        var viewHolder: (View) -> V = { v -> BasicViewHolder(v, listOf(R.id.item)) as V }

        /**
         * Property for binding
         */
        var binding: (V, T) -> Unit = { vh, it ->
            (vh as BasicViewHolder).getViewById<TextView>(R.id.item)?.text = (it as String)
        }

        /**
         * Property for notifyDataSetChanged
         */
        var notifyDataSetChanged: Boolean = false
    }

    fun <T, V: RecyclerView.ViewHolder> newStaticAdapter(block: AdapterProperties<T, V>.() -> Unit): StaticAdapter<T, V> {
        val props = AdapterProperties<T, V>()
        
        props.block()

        return object: StaticAdaptable<T, V>() {
            override fun getLayoutResource() = props.layoutResourceId
            override fun getListItems() = props.listItems
            override fun getViewHolder(view: View) = props.viewHolder(view)
            override fun bind(viewHolder: V, item: T) { props.binding(viewHolder, item) }
        }.buildAdapter(props.notifyDataSetChanged)
    }

}