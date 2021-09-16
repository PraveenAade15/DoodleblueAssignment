package com.example.doodleblueapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItemIntoCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cartTable")
    fun getAllCart(): LiveData<List<CartEntity>>

    @Query("SELECT SUM (productQuantity) FROM cartTable")
    fun getCartCount(): LiveData<Int>

    @Query("SELECT SUM (productPrice) FROM cartTable")
    fun getTotalCartAmount(): LiveData<Long>

    @Query("UPDATE cartTable SET productQuantity=:quantity,productPrice=:price WHERE cId=:id")
    fun updateQuantityPrice(quantity: Int, price: Long, id: Int)

}
