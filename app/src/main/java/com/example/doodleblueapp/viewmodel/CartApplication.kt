package com.example.doodleblueapp.viewmodel

import android.app.Application
import com.example.doodleblueapp.data.CartDatabase
import com.example.doodleblueapp.repository.CartRepository


class CartApplication : Application() {

    val cartDao by lazy {
        val roomDatabase = CartDatabase.getDataBaseInstance(this)
        roomDatabase.getCartDao()
    }

    val cartRepository by lazy {
        CartRepository(cartDao)
    }
}