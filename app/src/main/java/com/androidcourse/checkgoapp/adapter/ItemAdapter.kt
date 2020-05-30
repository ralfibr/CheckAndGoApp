package com.androidcourse.checkgoapp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidcourse.checkgoapp.R
import com.androidcourse.checkgoapp.model.Item
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_layout.view.*


class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) {
            itemView.tvItem.setOnClickListener{
                Snackbar.make(itemView, "hi", Snackbar.LENGTH_SHORT).show()
            }
            itemView.tvItem.text = item.name

        }
    }


}