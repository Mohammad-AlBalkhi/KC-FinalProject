package com.example.myapplication.cart.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.cart.cartProducts;
import com.example.myapplication.cart.dao.CartDAO;

@Database(entities = {cartProducts.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,CartDatabase.class
                    ,"productsDatabase").fallbackToDestructiveMigration().build();

        }
        return instance;
    }

}
