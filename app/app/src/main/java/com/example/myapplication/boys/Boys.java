package com.example.myapplication.boys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.cart.Cart;
import com.example.myapplication.Details;
import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;
import com.example.myapplication.cart.cartProducts;
import com.example.myapplication.cart.viewmodel.cartViewModel;

import java.util.ArrayList;
import java.util.List;

public class Boys extends AppCompatActivity implements BoysRecyclerViewInterface{

    ArrayList<BoysProducts> boysProductsArrayList =new ArrayList<>();
    private cartViewModel viewModel;
    private List<cartProducts> cartProductsList;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boys);
        ImageView back = findViewById(R.id.back);

        layout = 2;

        cartProductsList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(cartViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.bRcyclerView);

        BoysProducts item1=new BoysProducts("ملابس ولادي أنيق",3.5,R.drawable.boy1);
        BoysProducts item2=new BoysProducts("ملابس ولادي كحلي",4.0,R.drawable.boy2);
        BoysProducts item3=new BoysProducts("ملابس ولادي رمادي",4.95,R.drawable.boy3);
        BoysProducts item4=new BoysProducts("ملابس ولادي طقم",5.5,R.drawable.boy4);

        boysProductsArrayList.add(item1);
        boysProductsArrayList.add(item2);
        boysProductsArrayList.add(item3);
        boysProductsArrayList.add(item4);

        viewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                cartProductsList.addAll(cartProducts);
            }
        });

        boysAdapter bAdapter = new boysAdapter(this,boysProductsArrayList,this);
        recyclerView.setAdapter(bAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackHome();
            }

            public void BackHome(){
                Intent intent2 = new Intent(Boys.this , HomeActivity.class);
                startActivity(intent2);

            }
        });

        ImageView cartBtn = findViewById(R.id.cart);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_cart();
            }
            public void openActivity_cart(){
                Intent intent = new Intent(Boys.this, Cart.class);
                intent.putExtra("layout",layout);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(Boys.this, Details.class);

        intent.putExtra("layout",layout);
        intent.putExtra("name",boysProductsArrayList.get(position).getName());
        intent.putExtra("price",boysProductsArrayList.get(position).getPrice());
        intent.putExtra("image",boysProductsArrayList.get(position).getImg());

        startActivity(intent);
    }


    @Override
    public void onAddToCartClicked(BoysProducts boysProducts) {
        cartProducts cartProducts = new cartProducts();
        cartProducts.setName(boysProducts.getName());
        cartProducts.setPrice(boysProducts.getPrice());
        cartProducts.setImg(boysProducts.getImg());

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