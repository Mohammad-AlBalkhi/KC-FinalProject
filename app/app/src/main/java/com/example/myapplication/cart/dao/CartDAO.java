package com.example.myapplication.cart.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.cart.cartProducts;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insertCartItem(cartProducts cartProducts);

    @Query("SELECT * FROM cart_table")
    LiveData<List<cartProducts>> getAllCartItems();

    @Delete
    void deleteCartItem(cartProducts cartProducts);

    @Query("UPDATE cart_table SET quantity= :quantity WHERE id= :id")
    void updateQuantity(int id, int quantity);

    @Query("UPDATE cart_table SET totalItemPrice= :totalItemPrice WHERE id= :id")
    void updatePrice(int id, double totalItemPrice);

    @Query("DELETE FROM cart_table")
    void deleteAllItems();

}