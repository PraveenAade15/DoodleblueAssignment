package com.example.doodleblueapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cartTable")
data class CartEntity(
    @ColumnInfo(name = "productTitle") var productTitle: String,
    @ColumnInfo(name = "productDesc") var productDesc: String,
    @ColumnInfo(name = "productPrice") var productPrice: Long,
    @ColumnInfo(name = "productQuantity") var productQuantity: Int,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cId")
    var cId: Int? = null
}