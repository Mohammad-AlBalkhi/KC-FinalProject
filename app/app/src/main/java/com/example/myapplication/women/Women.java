package com.example.myapplication.women;

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

public class Women extends AppCompatActivity implements womenRecyclerViewInterface {



    ArrayList<womenProducts> womenProductsArrayList =new ArrayList<>();
    private cartViewModel viewModel;
    private List<cartProducts> cartProductsList;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);

        layout = 6;

        cartProductsList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(cartViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.wRecyclerView);

        womenProducts item1=new womenProducts("دراعة بأكمام قصيرة",2.95,R.drawable.women1);
        womenProducts item2=new womenProducts("دراعة بنقشة ورود",2.5,R.drawable.women2);
        womenProducts item3=new womenProducts("دراعة صفراء",4.95,R.drawable.women3);
        womenProducts item4=new womenProducts("دراعة بيضاء",3.25,R.drawable.women4);
        womenProducts item5=new womenProducts("دراعة صيفية", 4.5,R.drawable.women5);


        womenProductsArrayList.add(item1);
        womenProductsArrayList.add(item2);
        womenProductsArrayList.add(item3);
        womenProductsArrayList.add(item4);
        womenProductsArrayList.add(item5);

        viewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                cartProductsList.addAll(cartProducts);
            }
        });

        womenAdapter wAdapter = new womenAdapter(this,womenProductsArrayList,this);
        recyclerView.setAdapter(wAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackHome();
            }

            public void BackHome(){
                Intent intent2 = new Intent(Women.this , HomeActivity.class);
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
                Intent intent = new Intent(Women.this, Cart.class);
                intent.putExtra("layout",layout);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(Women.this, Details.class);

        intent.putExtra("layout",layout);
        intent.putExtra("name",womenProductsArrayList.get(position).getName());
        intent.putExtra("price",womenProductsArrayList.get(position).getPrice());
        intent.putExtra("image",womenProductsArrayList.get(position).getImg());

        startActivity(intent);

    }

    @Override
    public void onAddToCartClicked(womenProducts womenProducts) {
        cartProducts cartProducts = new cartProducts();
        cartProducts.setName(womenProducts.getName());
        cartProducts.setPrice(womenProducts.getPrice());
        cartProducts.setImg(womenProducts.getImg());

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