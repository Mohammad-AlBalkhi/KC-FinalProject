package com.example.myapplication.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.accessories.Accessories;
import com.example.myapplication.boys.Boys;
import com.example.myapplication.girls.Girls;
import com.example.myapplication.men.Men;
import com.example.myapplication.shoes.Shoes;
import com.example.myapplication.cart.viewmodel.cartViewModel;
import com.example.myapplication.women.Women;

import java.text.DecimalFormat;
import java.util.List;

public class Cart extends AppCompatActivity implements CartAdapter.CartClickedListeners {

    private RecyclerView recyclerView;
    private cartViewModel cartViewModel;
    private TextView totalPriceTv, checkoutBtn,totaltxt,mycartTxt;
    private CardView cardView;
    private CartAdapter cartAdapter;
    private double checkoutPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        cartAdapter = new CartAdapter(this);

        cardView = findViewById(R.id.cartActivityCardView);
        totalPriceTv = findViewById(R.id.totalPrice);
        totaltxt = findViewById(R.id.totaltxt);
        mycartTxt = findViewById(R.id.mycartTxt);
        checkoutBtn = findViewById(R.id.checkOutBtn);
        cartViewModel = new ViewModelProvider(this).get(cartViewModel.class);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(cartAdapter);


        cartViewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                double price = 0;
                cartAdapter.setCartProductsList(cartProducts);

                for(int i=0;i<cartProducts.size();i++){
                    price = price + cartProducts.get(i).getTotalItemPrice();
                }
                checkoutPrice = price;
                DecimalFormat df = new DecimalFormat("#.###");


                totalPriceTv.setText(df.format(price)+" KD");
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkoutPrice>0) {

                    cartViewModel.deleteAllCartItem();
                    totalPriceTv.setVisibility(View.INVISIBLE);
                    checkoutBtn.setVisibility(View.INVISIBLE);
                    totaltxt.setVisibility(View.INVISIBLE);
                    mycartTxt.setVisibility(View.INVISIBLE);

                    cardView.setVisibility(View.VISIBLE);
                }
            }
        });

        int recivedlayout = getIntent().getIntExtra("layout",0);
        ImageView backBtn2 = findViewById(R.id.backBtn2);
        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recivedlayout == 1){
                    Intent accIntent = new Intent(Cart.this, Accessories.class);
                    startActivity(accIntent);
                }
                else if(recivedlayout == 2){
                    Intent bIntent = new Intent(Cart.this, Boys.class);
                    startActivity(bIntent);

                }
                else if(recivedlayout == 3){
                    Intent gIntent = new Intent(Cart.this, Girls.class);
                    startActivity(gIntent);
                }
                else if(recivedlayout ==4){
                    Intent mIntent = new Intent(Cart.this, Men.class);
                    startActivity(mIntent);
                }
                else if(recivedlayout ==5){
                    Intent shIntent = new Intent(Cart.this, Shoes.class);
                    startActivity(shIntent);
                }
                else if(recivedlayout == 6){
                    Intent wIntent = new Intent(Cart.this, Women.class);
                    startActivity(wIntent);
                }
                else{
                    Intent homeIntent = new Intent(Cart.this, HomeActivity.class);
                    startActivity(homeIntent);
                }


            }
        });


    }

    @Override
    public void onDeleteClicked(cartProducts cartProducts) {
        cartViewModel.deleteCartItem(cartProducts);

    }

    @Override
    public void onPlusClicked(cartProducts cartProducts) {
        int quantity = cartProducts.getQuantity() + 1;
        cartViewModel.updateQuantity(cartProducts.getId(), quantity);
        cartViewModel.updatePrice(cartProducts.getId(), quantity * cartProducts.getPrice());

        cartAdapter.notifyDataSetChanged();


    }

    @Override
    public void onMinusClicked(cartProducts cartProducts) {
        int quantity = cartProducts.getQuantity() - 1;
        if (quantity != 0){
            cartViewModel.updateQuantity(cartProducts.getId() , quantity);
            cartViewModel.updatePrice(cartProducts.getId() , quantity*cartProducts.getPrice());
            cartAdapter.notifyDataSetChanged();
        }else{
            cartViewModel.deleteCartItem(cartProducts);
        }

    }


}