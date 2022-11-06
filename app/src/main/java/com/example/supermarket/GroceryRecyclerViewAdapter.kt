package com.example.supermarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryRecyclerViewAdapter(
    var list: List<GroceryItem>,
    private val groceryItemClicked: GroceryItemClicked,
) : RecyclerView.Adapter<GroceryRecyclerViewAdapter.GroceryViewHolder>() {


    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.item_name)
        val quantityTextView = itemView.findViewById<TextView>(R.id.item_quantity)
        val rateTextView = itemView.findViewById<TextView>(R.id.item_rate)
        val amountTextView = itemView.findViewById<TextView>(R.id.total_amount)
        val deleteImageView = itemView.findViewById<ImageView>(R.id.item_delete)
    }


    interface GroceryItemClicked {
        fun onItemClick(groceryItem: GroceryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_item, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTextView.text = list[position].itemName
        holder.quantityTextView.text = list[position].itemQuantity.toString()
        holder.rateTextView.text = "Rs. " + list[position].itemPrice.toString()
        val itemTotal: Int = list[position].itemPrice * list[position].itemQuantity
        holder.amountTextView.text = "Rs. " + itemTotal.toString()
        holder.deleteImageView.setOnClickListener {
            groceryItemClicked.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}