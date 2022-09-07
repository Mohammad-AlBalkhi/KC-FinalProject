package com.example.myapplication.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<cartProducts> cartProductsList;
    private CartClickedListeners cartClickedListeners;

    public CartAdapter(CartClickedListeners cartClickedListeners){
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setCartProductsList(List<cartProducts> cartProductsList) {
        this.cartProductsList = cartProductsList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        cartProducts product = cartProductsList.get(position);
        holder.Image.setImageResource(product.getImg());
        holder.Name.setText(product.getName());
        holder.Quantity.setText(product.getQuantity()+"");
        holder.Price.setText(product.getPrice()+"");

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(product);
            }
        });

        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(product);
            }
        });

        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(product);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (cartProductsList == null) {
            return 0;
        } else {
            return cartProductsList.size();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView Name, Price, Quantity;
        private ImageView Image;
        private ImageView deleteBtn;
        private ImageButton addQuantityBtn, minusQuantityBtn;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.eachCartItemName);
            Price = itemView.findViewById(R.id.eachCartItemPriceTv);
            Quantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            Image = itemView.findViewById(R.id.eachCartItemIV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
            deleteBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);


        }
    }

    public interface CartClickedListeners{
        void onDeleteClicked(cartProducts cartProducts);
        void onPlusClicked(cartProducts cartProducts);
        void onMinusClicked(cartProducts cartProducts);


    }

}
