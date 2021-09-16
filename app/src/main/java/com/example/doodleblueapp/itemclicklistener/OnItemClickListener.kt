package ccom.example.doodleblueapp.itemclicklistener

import com.example.doodleblueapp.data.CartEntity



interface OnItemClickListener {
    fun incrementCount(cartEntity : CartEntity, position : Int)
    fun decrementCount(cartEntity : CartEntity,position : Int)
}