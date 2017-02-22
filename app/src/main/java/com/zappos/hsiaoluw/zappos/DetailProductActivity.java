package com.zappos.hsiaoluw.zappos;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zappos.hsiaoluw.zappos.databinding.ActivityDetailProductBinding;


public class DetailProductActivity extends base {


    private TextView tvName, tvBrand, tvPrice, tvPercent, tvOriPrice;

    private Button btnProduct;
    private ImageView itemImage;
    private FloatingActionButton fabCart;


    public class MyAnimationListener implements Animation.AnimationListener {
        View v;
        Animation next;
        FloatingActionButton fab;
        String full;
        public void setV(View view) {
            this.v = view;
        }
        public void setAni(Animation ani){ next=ani;}
        public void setFab(FloatingActionButton fb){ this.fab=fb;}
        public void setFull(String f){this.full=f;}
        public void onAnimationEnd(Animation animation) {
            if(full.equals("0"))
            fab.setImageDrawable(getResources().getDrawable(R.drawable.full_cart,null));
            else{fab.setImageDrawable(getResources().getDrawable(R.drawable.empty_cart,null));}
            v.startAnimation(next);
        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       ActivityDetailProductBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_product);

        final DataItem item = MainActivity.products_data.get(getIntent().getExtras().getInt(MainActivity.POSITION));

        //Map<String, Bitmap> mBitmaps = (Map<String, Bitmap>) getIntent().getExtras().getSerializable(DataItemAdapter.MAP_KEY);

        if (item == null) {
            throw new AssertionError("Null data item received!");
        }
        binding.setItem(item);

        tvName = (TextView) findViewById(R.id.tvItemName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvBrand = (TextView) findViewById(R.id.tv_brand);
        tvOriPrice =(TextView) findViewById(R.id.tv_ori);
        fabCart = (FloatingActionButton) findViewById(R.id.fab_cart);
        itemImage = (ImageView) findViewById(R.id.itemImage);

        tvName   .setTextSize(20);
        tvBrand.setTextColor(Color.BLACK);
        tvOriPrice.setTextColor(Color.rgb(255,0,0));
        tvOriPrice.setPaintFlags(tvOriPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        if(item.getprice().equals(item.getoriginalPrice()) ){
            tvOriPrice.setVisibility(View.INVISIBLE);
        }

        btnProduct =(Button) findViewById(R.id.btn_producturl);
        btnProduct.setPaintFlags(btnProduct.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        btnProduct.setTextColor(Color.rgb(0,0,255));
        btnProduct.setTextSize(9);
        final Animation ani_cart= AnimationUtils.loadAnimation(this, R.anim.flipcart);
        final Animation ani_cart_inverse = AnimationUtils.loadAnimation(this, R.anim.inverse_flipcart);
        final String index= item.getproductId()+item.getstyleId();
        if(DataItemAdapter.mBitmaps.containsKey(index)){
            Bitmap mp = DataItemAdapter.mBitmaps.get(item.getproductId()+item.getstyleId());
            itemImage.setImageBitmap(mp);
        }
        final MyAnimationListener listener = new MyAnimationListener();


        Toast.makeText(DetailProductActivity.this
                ,item.getprice(),
                Toast.LENGTH_SHORT).show();
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getproductUrl()));
                startActivity(myIntent);
            }
        });

        if(item.InCart.equals("0"))  fabCart.setImageDrawable(getResources().getDrawable(R.drawable.empty_cart,null));
        else                        fabCart.setImageDrawable(getResources().getDrawable(R.drawable.full_cart,null));



        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.setV(v);
                listener.setFull(item.InCart);
                if(item.InCart.equals("0"))
                {
                    mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.full_cart,null));
                    listener.setAni(ani_cart_inverse);
                    listener.setFab(fabCart);
                    v.startAnimation(ani_cart);
                    item.InCart = "1";
                    CartAdapter.Cart.add_to_cart(item);
                    full=1;
                }

                else{
                    mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.empty_cart,null));
                    listener.setAni(ani_cart_inverse);
                    listener.setFab(fabCart);
                    v.startAnimation(ani_cart);
                    CartAdapter.Cart.remove_from_cart(item);
                    item.InCart = "0";
                    full=0;
                }
            }
        });
        ani_cart.setAnimationListener(listener);
        setResult(RESULT_OK);
    }

}