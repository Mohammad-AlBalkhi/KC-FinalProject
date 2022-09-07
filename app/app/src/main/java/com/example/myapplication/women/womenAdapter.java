package com.example.myapplication.women;

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


public class womenAdapter extends RecyclerView.Adapter<womenAdapter.MyViewHolder> {

    private final womenRecyclerViewInterface womenRecyclerViewInterface;
    Context context;
    ArrayList<womenProducts> womenProductsArrayList;


    public womenAdapter(Context context, ArrayList<womenProducts> womenProductsArrayList,
                        womenRecyclerViewInterface womenRecyclerViewInterface) {
        this.context = context;
        this.womenProductsArrayList = womenProductsArrayList;
        this.womenRecyclerViewInterface = womenRecyclerViewInterface;

    }

    @NonNull
    @Override
    public womenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row,parent,false);

        return new womenAdapter.MyViewHolder(view,womenRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull womenAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        womenProducts product = womenProductsArrayList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
        holder.imageView.setImageResource(product.getImg());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim));

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                womenRecyclerViewInterface.onAddToCartClicked(product);

                Toast.makeText(context,womenProductsArrayList.get(position).getName()+" added to cart",Toast.LENGTH_SHORT).show();


            }
        });



    }

    @Override
    public int getItemCount() {
        return womenProductsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView price;
        ImageView addBtn;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, womenRecyclerViewInterface womenRecyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cartImg);
            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            addBtn= itemView.findViewById(R.id.addBtn);
            cardView=itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(womenRecyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            womenRecyclerViewInterface.onItemClicked(pos);
                        }

                    }
                }
            });


        }

    }

}
