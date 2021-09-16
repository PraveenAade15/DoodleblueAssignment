package com.example.doodleblueapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ccom.example.doodleblueapp.itemclicklistener.OnItemClickListener
import com.example.doodleblueapp.data.CartEntity

import kotlinx.android.synthetic.main.item_layout.view.*

class ProductAdapter(
        val productList: List<CartEntity>,
        val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<ProductItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ProductItemHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {
        val productItem = productList[position];
        holder.setData(productItem, position)

        holder.addItemQuantity.setOnClickListener {
            onItemClickListener.incrementCount(productList[position],position)
        }

        holder.removeItemQuantity.setOnClickListener {
            onItemClickListener.decrementCount(productList[position],position)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

}

class ProductItemHolder(val view: View, val onItemClickListener: OnItemClickListener) :
    RecyclerView.ViewHolder(view) {

    val addItemQuantity = view.findViewById<AppCompatTextView>(R.id.tvAddItemQuantity)
    val removeItemQuantity = view.findViewById<AppCompatTextView>(R.id.tvRemoveItemQuantity)

    fun setData(cartEntity: CartEntity, position: Int) {
        view.apply {

            tvItemTitle.text = cartEntity.productTitle
            tvItemDesc.text = cartEntity.productDesc
            tvItemPrice.text = "€${7}"
            tvItemQuantity.text = cartEntity.productQuantity.toString()

        }
    }
}