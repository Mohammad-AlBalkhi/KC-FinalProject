package com.example.myapplication.shoes;

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

public class shoesAdapter extends RecyclerView.Adapter<shoesAdapter.MyViewHolder> {
    Context context;
    ArrayList<shoesProducts> shoesProductsArrayList;
    private final ShoesRecyclerViewInterface shoesRecyclerViewInterface;

    public shoesAdapter(Context context, ArrayList<shoesProducts> shoesProductsArrayList, ShoesRecyclerViewInterface shoesRecyclerViewInterface) {
        this.context = context;
        this.shoesProductsArrayList = shoesProductsArrayList;
        this.shoesRecyclerViewInterface = shoesRecyclerViewInterface;
    }

    @NonNull
    @Override
    public shoesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row,parent,false);

        return new shoesAdapter.MyViewHolder(view,shoesRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull shoesAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        shoesProducts product = shoesProductsArrayList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice()+"");
        holder.imageView.setImageResource(product.getImg());

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim));

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shoesRecyclerViewInterface.onAddToCartClicked(product);

                Toast.makeText(context,shoesProductsArrayList.get(position).getName()+" added to cart",Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return shoesProductsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        TextView price;
        ImageView addBtn;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, ShoesRecyclerViewInterface shoesRecyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cartImg);
            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
            addBtn = itemView.findViewById(R.id.addBtn);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shoesRecyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            shoesRecyclerViewInterface.onItemClicked(pos);
                        }

                    }
                }
            });
        }
    }
}
