package com.corphish.keyvalueviewtest

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.corphish.widgets.ktx.adapters.ImmutableListAdaptable
import com.corphish.widgets.ktx.viewholders.BasicViewHolder
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity for demonstration
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleList = listOf(11, 5, 3, 8, 1, 9, 6, 2, 9, 16, 78, 14)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : ImmutableListAdaptable<Int, BasicViewHolder>() {
            override fun getLayoutResource(viewType: Int) = when(viewType) {
                0 -> R.layout.layout_item_type_0
                else -> R.layout.layout_item_type_1
            }

            override fun getListItems() = sampleList

            override fun getViewHolder(view: View, viewType: Int): BasicViewHolder {
                return when(viewType) {
                    0 -> BasicViewHolder(view, listOf(R.id.text0))
                    else -> BasicViewHolder(view, listOf(R.id.text1))
                }
            }

            override fun bind(viewHolder: BasicViewHolder, position: Int) {
                val textViewId = intArrayOf(R.id.text0, R.id.text1)[getViewType(position)]
                viewHolder.getViewById<TextView>(textViewId)?.text = "${getListItems()[position]}"
            }

            override fun getViewType(position: Int) = position % 2
        }.buildAdapter(true)
    }

    val text: String
        get() = "Success"
}