package com.example.doodleblueapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doodleblueapp.repository.CartRepository


class CartViewModelFactory(val cartRepository: CartRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CartViewModel(cartRepository) as T
    }
}