package com.example.doodleblueapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.doodleblueapp.data.CartEntity
import com.example.doodleblueapp.repository.CartRepository


class CartViewModel(val cartRepository: CartRepository) : ViewModel() {

    fun addProductModel(cartEntity: CartEntity){
        return cartRepository.addProductRepo(cartEntity)
    }

    fun getAllItem(): LiveData<List<CartEntity>> {
        return cartRepository.getAllItem()
    }

    fun updateProductQuantityModel(quantity: Int, price: Long, id: Int) {
        cartRepository.updateProductQuantityRepo(quantity, price, id)
    }
    fun getTotalCartAmountModel(): LiveData<Long> {
        return cartRepository.getTotalCartAmountRepo()
    }

    fun getCartCountModel(): LiveData<Int> {
        return cartRepository.getCartCountRepo()
    }

}