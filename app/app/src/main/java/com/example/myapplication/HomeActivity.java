package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.myapplication.accessories.Accessories;
import com.example.myapplication.boys.Boys;
import com.example.myapplication.cart.Cart;
import com.example.myapplication.girls.Girls;
import com.example.myapplication.men.Men;
import com.example.myapplication.shoes.Shoes;
import com.example.myapplication.women.Women;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();


    ViewFlipper viewFlipper;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layout = 7;

        viewPager2 = findViewById(R.id.viewPagerImageslider);
        List<Slideritem> slideritems = new ArrayList<>();
        slideritems.add(new Slideritem(R.drawable.imageshow1));
        slideritems.add(new Slideritem(R.drawable.imageshow2));
        slideritems.add(new Slideritem(R.drawable.imageshow3));
        slideritems.add(new Slideritem(R.drawable.imageshow4));
        slideritems.add(new Slideritem(R.drawable.imageshow5));

        viewPager2.setAdapter(new sliderAdapter(slideritems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,2000);

            }
        });

        int images[] = {R.drawable.best_prices,R.drawable.fast_delivery};
        viewFlipper = findViewById(R.id.flipper);
        for(int image:images){
            flipprImages(image);
        }

        Button GirlsButton = findViewById(R.id.GirlsButton);

        GirlsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_girls();

            }

            public void openActivity_girls() {

                Intent intent = new Intent(HomeActivity.this, Girls.class);
                startActivity(intent);
            }
        });

        Button WomenBtn = findViewById(R.id.WomenBtn);

        WomenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_Women();

            }

            public void openActivity_Women() {

                Intent intent = new Intent(HomeActivity.this, Women.class);
                startActivity(intent);


            }
        });

        Button BoysBtn = findViewById(R.id.BoysBtn);

        BoysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_Boys();

            }

            public void openActivity_Boys() {

                Intent intent = new Intent(HomeActivity.this, Boys.class);
                startActivity(intent);


            }
        });

        Button accBtn = findViewById(R.id.accBtn);

        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_acc();

            }

            public void openActivity_acc() {

                Intent intent = new Intent(HomeActivity.this, Accessories.class);
                startActivity(intent);


            }
        });

        Button menBtn = findViewById(R.id.menBtn);

        menBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_men();

            }

            public void openActivity_men() {

                Intent intent = new Intent(HomeActivity.this, Men.class);
                startActivity(intent);


            }
        });

        Button shoeBtn = findViewById(R.id.shoeBtn);
        shoeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_shoes();

            }

            public void openActivity_shoes() {

                Intent intent = new Intent(HomeActivity.this, Shoes.class);
                startActivity(intent);


            }
        });

        ImageView cartBtn = findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_cart();
            }
            public void openActivity_cart(){
                Intent intent = new Intent(HomeActivity.this, Cart.class);
                intent.putExtra("layout",layout);
                startActivity(intent);
            }
        });


        ImageView CallBtn = findViewById(R.id.CallBtn);
        CallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: 96598938919"));
                startActivity(callIntent);

            }
        });

        ImageView InstaBtn = findViewById(R.id.InstaBtn);
        InstaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.instagram.com/amal_fashion.kw");
                Intent instaIntent = new Intent(Intent.ACTION_VIEW);
                instaIntent.setPackage("com.instagram.android");

                try {
                    startActivity(instaIntent);
                }
                catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/amal_fashion.kw")));
                }

            }
        });

        
    }




    public void flipprImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left );
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+ 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,2000);
    }
}
