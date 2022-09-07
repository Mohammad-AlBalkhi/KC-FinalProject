package com.example.myapplication.boys;

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
import java.util.Calendar;

public class boysAdapter extends RecyclerView.Adapter<boysAdapter.MyViewHolder> {
    Context context;
    ArrayList<BoysProducts> boysProductsArrayList;
    BoysRecyclerViewInterface boysRecyclerViewInterface;

    public boysAdapter(Context context, ArrayList<BoysProducts> boysProductsArrayList,
                       BoysRecyclerViewInterface boysRecyclerViewInterface) {
        this.context = context;
        this.boysProductsArrayList = boysProductsArrayList;
        this.boysRecyclerViewInterface = boysRecyclerViewInterface;

    }

    @NonNull
    @Override
    public boysAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row,parent,false);

        return new boysAdapter.MyViewHolder(view,boysRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull boysAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BoysProducts product = boysProductsArrayList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
        holder.imageView.setImageResource(product.getImg());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim));


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boysRecyclerViewInterface.onAddToCartClicked(product);

                Toast.makeText(context,boysProductsArrayList.get(position).getName()+" added to cart",Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return boysProductsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        TextView price;
        ImageView addBtn;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, BoysRecyclerViewInterface boysRecyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cartImg);
            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            addBtn = itemView.findViewById(R.id.addBtn);
            cardView = itemView.findViewById(R.id.cardView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(boysRecyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            boysRecyclerViewInterface.onItemClicked(pos);
                        }

                    }
                }
            });
        }
    }
}
