package com.example.myapplication.girls;

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

public class Girls extends AppCompatActivity implements GirlsRecyclerViewInerface{

    ArrayList<GirlsProducts> girlsProductsArrayList =new ArrayList<>();
    private cartViewModel viewModel;
    private List<cartProducts> cartProductsList;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls);

        layout = 3;

        cartProductsList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(cartViewModel.class);


        RecyclerView recyclerView = findViewById(R.id.gRecyclerView);

        GirlsProducts item1=new GirlsProducts("فستان وردي",4.95,R.drawable.girls1);
        GirlsProducts item2=new GirlsProducts("فستان بنقشة برج إيفل",2.5,R.drawable.girls2);
        GirlsProducts item3=new GirlsProducts("فستان بناتي",2.95,R.drawable.girls3);
        GirlsProducts item4=new GirlsProducts("فستان أحمر",3.95,R.drawable.girls4);
        GirlsProducts item5=new GirlsProducts("فستان مخطط",3.0,R.drawable.girls5);
        GirlsProducts item6=new GirlsProducts("فستان مخطط كحلي",3.0,R.drawable.girls6);
        GirlsProducts item7=new GirlsProducts("فستان أصفر",4.95,R.drawable.girls7);
        GirlsProducts item8=new GirlsProducts("فستان أزرق",5.5,R.drawable.girls8);

        girlsProductsArrayList.add(item1);
        girlsProductsArrayList.add(item2);
        girlsProductsArrayList.add(item3);
        girlsProductsArrayList.add(item4);
        girlsProductsArrayList.add(item5);
        girlsProductsArrayList.add(item6);
        girlsProductsArrayList.add(item7);
        girlsProductsArrayList.add(item8);

        viewModel.getAllCartItems().observe(this, new Observer<List<cartProducts>>() {
            @Override
            public void onChanged(List<cartProducts> cartProducts) {
                cartProductsList.addAll(cartProducts);
            }
        });


        girlsAdapter gAdapter = new girlsAdapter(this,girlsProductsArrayList,this);
        recyclerView.setAdapter(gAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackHome();
            }

            public void BackHome(){
                Intent intent2 = new Intent(Girls.this , HomeActivity.class);
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
                Intent intent = new Intent(Girls.this, Cart.class);
                intent.putExtra("layout",layout);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(Girls.this, Details.class);

        intent.putExtra("layout",layout);
        intent.putExtra("name",girlsProductsArrayList.get(position).getName());
        intent.putExtra("price",girlsProductsArrayList.get(position).getPrice());
        intent.putExtra("image",girlsProductsArrayList.get(position).getImg());

        startActivity(intent);
    }


    @Override
    public void onAddToCartClicked(GirlsProducts girlsProducts) {
        cartProducts cartProducts = new cartProducts();
        cartProducts.setName(girlsProducts.getName());
        cartProducts.setPrice(girlsProducts.getPrice());
        cartProducts.setImg(girlsProducts.getImg());

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