package com.example.supermarket

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryRecyclerViewAdapter.GroceryItemClicked {
    lateinit var itemsRecyclerView: RecyclerView
    lateinit var addActionButton: FloatingActionButton
    lateinit var list: List<GroceryItem>
    lateinit var adapter: GroceryRecyclerViewAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRecyclerView = findViewById(R.id.grocery_recycler_view)
        addActionButton = findViewById(R.id.fab_button)

        list = ArrayList<GroceryItem>()
        adapter = GroceryRecyclerViewAdapter(list, this)

        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsRecyclerView.adapter = adapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]

        groceryViewModel.getAllItems().observe(this, Observer {
            adapter.list = it
            adapter.notifyDataSetChanged()
        })

        addActionButton.setOnClickListener {
            openDialog()
        }
    }

    fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.btn_cancel)
        val addBtn = dialog.findViewById<Button>(R.id.btn_add)
        val itemNameEdtxt = dialog.findViewById<EditText>(R.id.edit_item_name)
        val itemQuantityEdtxt = dialog.findViewById<EditText>(R.id.edit_item_quantity)
        val itemPriceEdtxt = dialog.findViewById<EditText>(R.id.edit_item_price)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName: String = itemNameEdtxt.text.toString()
            val itemQuantity: String = itemQuantityEdtxt.text.toString()
            val itemPrice: String = itemPriceEdtxt.text.toString()
            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val qnty: Int = itemQuantity.toInt()
                val price: Float = itemPrice.toFloat()
                val groceryItem = GroceryItem(itemName, qnty, price)
                groceryViewModel.insert(groceryItem)
                Toast.makeText(applicationContext, "Item inserted!", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(applicationContext, "Please fill all the boxes", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    override fun onItemClick(groceryItem: GroceryItem) {
        groceryViewModel.delete(groceryItem)
        adapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item deleted!", Toast.LENGTH_SHORT).show()
    }
}