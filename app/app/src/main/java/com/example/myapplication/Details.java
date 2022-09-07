package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.accessories.Accessories;
import com.example.myapplication.boys.Boys;
import com.example.myapplication.cart.Cart;
import com.example.myapplication.cart.cartProducts;
import com.example.myapplication.girls.Girls;
import com.example.myapplication.men.Men;
import com.example.myapplication.shoes.Shoes;
import com.example.myapplication.cart.viewmodel.cartViewModel;
import com.example.myapplication.women.Women;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    private cartViewModel viewModel;
    private List<cartProducts> cartProductsList;

    String name;
    double price;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int recivedlayout = getIntent().getIntExtra("layout",0);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price",0);
        image = getIntent().getIntExtra("image",0);

        TextView Name = findViewById(R.id.nametxt);
        TextView Price = findViewById(R.id.pricetxt);
        ImageView Image = findViewById(R.id.imageViewD);

        Name.setText(name);
        Price.setText("Price: "+price +"KD");
        Image.setImageResource(image);

        viewModel = new ViewModelProvider(this).get(cartViewModel.class);
        cartProductsList = new ArrayList<>();
        TextView addBtn = findViewById(R.id.addCartBtn);
        viewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                cartProductsList.addAll(cartProducts);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        insertToRoom();
                        Intent intent = new Intent(Details.this, Cart.class);
                        intent.putExtra("layout",recivedlayout);
                        startActivity(intent);
                    }
                });
            }
        });


        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recivedlayout == 1){
                    Intent accIntent = new Intent(Details.this, Accessories.class);
                    startActivity(accIntent);
                }
                else if(recivedlayout ==2){
                    Intent bIntent = new Intent(Details.this, Boys.class);
                    startActivity(bIntent);
                }
                else if(recivedlayout ==3){
                    Intent gIntent = new Intent(Details.this, Girls.class);
                    startActivity(gIntent);
                }
                else if(recivedlayout == 4){
                    Intent menIntent = new Intent(Details.this, Men.class);
                    startActivity(menIntent);
                }
                else if(recivedlayout == 5){
                    Intent shIntent = new Intent(Details.this, Shoes.class);
                    startActivity(shIntent);
                }
                else if(recivedlayout == 6){
                    Intent womenIntent = new Intent(Details.this, Women.class);
                    startActivity(womenIntent);
                }
            }
        });


    }

    private void insertToRoom() {
        cartProducts cartProducts = new cartProducts();
        cartProducts.setName(name);
        cartProducts.setPrice(price);
        cartProducts.setImg(image);

        final int[] quantity = {1};
        final int[] id = new int[1];

        if(!cartProductsList.isEmpty()){
            for(int i = 0; i<cartProductsList.size(); i++){
                if(cartProducts.getName().equals(cartProductsList.get(i).getName())){
                    quantity[0] = cartProductsList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = cartProductsList.get(i).getId();
                }
            }
        }

        if (quantity[0] == 1){
            cartProducts.setQuantity(quantity[0]);
            cartProducts.setTotalItemPrice(quantity[0]*cartProducts.getPrice());
            viewModel.insertCartItem(cartProducts);
        }else {
            viewModel.updateQuantity(id[0],quantity[0]);
            viewModel.updatePrice(id[0], quantity[0]*cartProducts.getPrice());
        }
    }
}