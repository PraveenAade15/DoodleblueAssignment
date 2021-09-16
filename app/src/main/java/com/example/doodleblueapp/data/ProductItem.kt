package com.example.doodleblueapp.data

data class ProductItem(
    var productId: Int = 0,
    var productName: String = "",
    var productDesc: String = "",
    var productPrice: Long = 9999,
    var count: Int = 0,
)