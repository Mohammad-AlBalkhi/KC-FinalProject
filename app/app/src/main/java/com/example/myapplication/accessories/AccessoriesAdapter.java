package com.example.myapplication.accessories;

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

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.ViewHolder> {

    ArrayList<AccessoriesProducts> AccArray;
    Context context;
    AccRecyclerViewInterface accRecyclerViewInterface;

    public AccessoriesAdapter(ArrayList<AccessoriesProducts> AccArray,Context context,
                              AccRecyclerViewInterface accRecyclerViewInterface){
        this.AccArray = AccArray;
        this.context = context;
        this.accRecyclerViewInterface = accRecyclerViewInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row,parent,false);

        return new AccessoriesAdapter.ViewHolder(view,accRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AccessoriesProducts product = AccArray.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
        holder.Img.setImageResource(product.getImg());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim));


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                accRecyclerViewInterface.onAddToCartClicked(product);

                Toast.makeText(context,AccArray.get(position).getName()+" added to cart",Toast.LENGTH_SHORT).show();


            }
        });

    }


    @Override
    public int getItemCount() {
        return AccArray.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Img;
        TextView name;
        TextView price;
        ImageView addBtn;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, AccRecyclerViewInterface accRecyclerViewInterface) {
            super(itemView);
            Img =itemView.findViewById(R.id.cartImg);
            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            addBtn = itemView.findViewById(R.id.addBtn);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(accRecyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            accRecyclerViewInterface.onItemClicked(pos);
                        }

                    }
                }
            });

        }
    }
}
