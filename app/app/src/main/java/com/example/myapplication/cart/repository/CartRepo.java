package com.example.myapplication.cart.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.cart.cartProducts;
import com.example.myapplication.cart.dao.CartDAO;
import com.example.myapplication.cart.database.CartDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {

    private CartDAO cartDAO;
    private LiveData<List<cartProducts>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<cartProducts>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public CartRepo(Application application){
        cartDAO = CartDatabase.getInstance(application).cartDAO();
        allCartItemsLiveData = cartDAO.getAllCartItems();

    }

    public void insertCartItem(cartProducts cartProducts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.insertCartItem(cartProducts);
            }
        });
    }

    public void deleteCartItem(cartProducts cartProducts){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteCartItem(cartProducts);
            }
        });
    }

    public void updateQuantity(int id, int quantity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updateQuantity(id, quantity);
            }
        });
    }

    public void updatePrice(int id, double price){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.updatePrice(id, price);
            }
        });
    }

    public void deleteAllCartItem(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDAO.deleteAllItems();
            }
        });
    }

}
