package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class sliderAdapter extends RecyclerView.Adapter<sliderAdapter.sliderViewHolder> {

    private List<Slideritem> slideritems;
    private ViewPager2 viewPager2;


     sliderAdapter(List<Slideritem> slideritems, ViewPager2 viewPager2) {
        this.slideritems = slideritems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public sliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new sliderViewHolder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.slide_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull sliderViewHolder holder, int position) {
        holder.setImageView(slideritems.get(position));
        if(position == slideritems.size() - 2){
            viewPager2.post(runnable);

        }

    }

    @Override
    public int getItemCount() {
        return slideritems.size();
    }

    class sliderViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView;

        sliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageslider);
        }

        void setImageView(Slideritem slideritem) {
            imageView.setImageResource(slideritem.getImage());
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideritems.addAll(slideritems);
            notifyDataSetChanged();
        }
    };


}
