package com.example.myapplication.accessories;

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

public class Accessories extends AppCompatActivity implements AccRecyclerViewInterface {

    ArrayList<AccessoriesProducts> accessoriesProductsArrayList =new ArrayList<>();
    private cartViewModel viewModel;
    private List<cartProducts> cartProductsList;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);
        ImageView back = findViewById(R.id.back);

        layout = 1;

        cartProductsList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(cartViewModel.class);

        RecyclerView accrecyclerView = findViewById(R.id.accRecyclerView);

        AccessoriesProducts item1=new AccessoriesProducts("ساعة نسائية",40.0,R.drawable.accessories1);
        AccessoriesProducts item2=new AccessoriesProducts("اكسسوار للشعر",1.25,R.drawable.accessories2);
        AccessoriesProducts item3=new AccessoriesProducts("طقم مجوهرات",3.750,R.drawable.accessories3);
        AccessoriesProducts item4=new AccessoriesProducts("ساعة رجالية",49.950,R.drawable.accessories4);

        accessoriesProductsArrayList.add(item1);
        accessoriesProductsArrayList.add(item2);
        accessoriesProductsArrayList.add(item3);
        accessoriesProductsArrayList.add(item4);

        viewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                cartProductsList.addAll(cartProducts);
            }
        });


        AccessoriesAdapter accAdapter = new AccessoriesAdapter(accessoriesProductsArrayList,this,this);
        accrecyclerView.setAdapter(accAdapter);
        accrecyclerView.setLayoutManager(new LinearLayoutManager(this));



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackHome();
            }

            public void BackHome(){
                Intent intent2 = new Intent(Accessories.this , HomeActivity.class);
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
                Intent intent = new Intent(Accessories.this, Cart.class);
                intent.putExtra("layout",layout);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(Accessories.this, Details.class);

        intent.putExtra("layout",layout);
        intent.putExtra("name",accessoriesProductsArrayList.get(position).getName());
        intent.putExtra("price",accessoriesProductsArrayList.get(position).getPrice());
        intent.putExtra("image",accessoriesProductsArrayList.get(position).getImg());

        startActivity(intent);
    }

    @Override
    public void onAddToCartClicked(AccessoriesProducts accessoriesProducts) {
        cartProducts cartProducts = new cartProducts();
        cartProducts.setName(accessoriesProducts.getName());
        cartProducts.setPrice(accessoriesProducts.getPrice());
        cartProducts.setImg(accessoriesProducts.getImg());

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