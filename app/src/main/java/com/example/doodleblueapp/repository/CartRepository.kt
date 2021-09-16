package com.example.doodleblueapp.repository

import androidx.lifecycle.LiveData
import com.example.doodleblueapp.data.CartDao
import com.example.doodleblueapp.data.CartEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartRepository(val cartDao: CartDao) {

    fun addProductRepo(cartEntity: CartEntity){
        CoroutineScope(Dispatchers.IO).launch {
             cartDao.addItemIntoCart(cartEntity)
        }
    }

    fun getAllItem(): LiveData<List<CartEntity>> {
        return cartDao.getAllCart()
    }

    fun getTotalCartAmountRepo(): LiveData<Long> {
        return cartDao.getTotalCartAmount()
    }

    fun updateProductQuantityRepo(quantity: Int, price: Long, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.updateQuantityPrice(quantity, price, id)
        }
    }
    fun getCartCountRepo(): LiveData<Int> {
        return cartDao.getCartCount()
    }


}