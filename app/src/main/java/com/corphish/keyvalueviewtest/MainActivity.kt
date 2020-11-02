package com.corphish.keyvalueviewtest

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.corphish.widgets.ktx.adapters.MutableListAdaptable
import com.corphish.widgets.ktx.viewholders.BasicViewHolder
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity for demonstration
 */
class MainActivity : AppCompatActivity() {
    private val sampleList = mutableListOf(11, 5, 3, 8, 1/*, 9, 6, 2, 9, 16, 78, 14*/)
    private lateinit var adapter: ListAdapter<Int, BasicViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = object : MutableListAdaptable<Int, BasicViewHolder>() {
            override fun getLayoutResource(viewType: Int) = when(viewType) {
                0 -> R.layout.layout_item_type_0
                else -> R.layout.layout_item_type_1
            }

            override fun getViewHolder(view: View, viewType: Int): BasicViewHolder {
                return when(viewType) {
                    0 -> BasicViewHolder(view, listOf(R.id.text0))
                    else -> BasicViewHolder(view, listOf(R.id.text1))
                }
            }

            override fun bind(viewHolder: BasicViewHolder, item: Int, position: Int) {
                val textViewId = intArrayOf(R.id.text0, R.id.text1)[getViewType(position)]
                viewHolder.getViewById<TextView>(textViewId)?.text = "$item"
            }

            override fun getViewType(position: Int) = position % 2

            override fun getDiffUtilItemCallback() = object: DiffUtil.ItemCallback<Int>() {
                override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
                override fun areContentsTheSame(oldItem: Int, newItem: Int) = false
            }
        }.buildAdapter()

        recyclerView.adapter = adapter
        adapter.submitList(sampleList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.itemId

        return when (id) {
            R.id.shuffle -> {
                adapter.submitList(sampleList.shuffled())
                true
            }
            R.id.add -> {
                val size = sampleList.size
                val r1 = (Math.random() * size).toInt()
                val r2 = (Math.random() * 47).toInt()

                sampleList.add(r1, r2)
                adapter.submitList(ArrayList(sampleList))

                true
            }
            R.id.delete -> {
                val size = sampleList.size
                val r1 = (Math.random() * size).toInt()

                sampleList.removeAt(r1)
                adapter.submitList(sampleList.toList())

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}