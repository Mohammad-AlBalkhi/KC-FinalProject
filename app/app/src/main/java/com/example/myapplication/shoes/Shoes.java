package com.example.myapplication.shoes;

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

public class Shoes extends AppCompatActivity implements ShoesRecyclerViewInterface{

    ArrayList<shoesProducts> shoesProductsArrayList = new ArrayList<>();
    private cartViewModel viewModel;
    private List<cartProducts> cartProductsList;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes);

        layout = 5;


        cartProductsList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(cartViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.shRecyclerView);

        shoesProducts item1=new shoesProducts("حذاء ولادي",3.95,R.drawable.shoes1);
        shoesProducts item2=new shoesProducts("حذاء نسائي",5.0,R.drawable.shoes2);
        shoesProducts item3=new shoesProducts("حذاء رجالي",4.95,R.drawable.shoes3);
        shoesProducts item4=new shoesProducts("حذاء بناتي",3.5,R.drawable.shoes4);

        shoesProductsArrayList.add(item1);
        shoesProductsArrayList.add(item2);
        shoesProductsArrayList.add(item3);
        shoesProductsArrayList.add(item4);

        viewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                cartProductsList.addAll(cartProducts);
            }
        });

        shoesAdapter shAdapter = new shoesAdapter(this,shoesProductsArrayList,this);
        recyclerView.setAdapter(shAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackHome();
            }

            public void BackHome(){
                Intent intent2 = new Intent(Shoes.this , HomeActivity.class);
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
                Intent intent = new Intent(Shoes.this, Cart.class);
                intent.putExtra("layout",layout);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(Shoes.this, Details.class);

        intent.putExtra("layout",layout);
        intent.putExtra("name",shoesProductsArrayList.get(position).getName());
        intent.putExtra("price",shoesProductsArrayList.get(position).getPrice());
        intent.putExtra("image",shoesProductsArrayList.get(position).getImg());

        startActivity(intent);
    }

    @Override
    public void onAddToCartClicked(shoesProducts shoesProducts) {
        cartProducts cartProducts = new cartProducts();
        cartProducts.setName(shoesProducts.getName());
        cartProducts.setPrice(shoesProducts.getPrice());
        cartProducts.setImg(shoesProducts.getImg());

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