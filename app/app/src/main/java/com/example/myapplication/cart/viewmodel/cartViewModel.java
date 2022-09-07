package com.example.myapplication.cart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.cart.cartProducts;
import com.example.myapplication.cart.repository.CartRepo;

import java.util.List;

public class cartViewModel extends AndroidViewModel {

    private CartRepo cartRepo;

    public cartViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }


    public LiveData<List<cartProducts>> getAllCartItems(){
        return cartRepo.getAllCartItemsLiveData();
    }

    public void insertCartItem(cartProducts cartProducts){
        cartRepo.insertCartItem(cartProducts);
    }
    public void updateQuantity(int id, int quantity){
        cartRepo.updateQuantity(id, quantity);
    }

    public void updatePrice(int id, double price){
        cartRepo.updatePrice(id, price);
    }

    public void deleteCartItem(cartProducts cartProducts){
        cartRepo.deleteCartItem(cartProducts);
    }

    public void deleteAllCartItem(){
        cartRepo.deleteAllCartItem();
    }
}
