package com.example.myapplication.girls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class girlsAdapter extends RecyclerView.Adapter<girlsAdapter.MyViewHolder> {
    Context context;
    ArrayList<GirlsProducts> girlsProductsArrayList;
    private final GirlsRecyclerViewInerface girlsRecyclerViewInerface;

    public girlsAdapter(Context context, ArrayList<GirlsProducts> girlsProductsArrayList,
                        GirlsRecyclerViewInerface girlsRecyclerViewInerface) {
        this.context = context;
        this.girlsProductsArrayList = girlsProductsArrayList;
        this.girlsRecyclerViewInerface = girlsRecyclerViewInerface;

    }

    @NonNull
    @Override
    public girlsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recyclerview_row,parent,false);

        return new girlsAdapter.MyViewHolder(v,girlsRecyclerViewInerface);
    }

    @Override
    public void onBindViewHolder(@NonNull girlsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GirlsProducts product = girlsProductsArrayList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
        holder.imageView.setImageResource(product.getImg());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim));


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                girlsRecyclerViewInerface.onAddToCartClicked(product);

                Toast.makeText(context,girlsProductsArrayList.get(position).getName()+" added to cart",Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return girlsProductsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView price;
        ImageView addBtn;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView, GirlsRecyclerViewInerface girlsRecyclerViewInerface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartImg);
            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            addBtn = itemView.findViewById(R.id.addBtn);
            cardView = itemView.findViewById(R.id.cardView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(girlsRecyclerViewInerface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            girlsRecyclerViewInerface.onItemClicked(pos);
                        }

                    }
                }
            });
        }
    }
}