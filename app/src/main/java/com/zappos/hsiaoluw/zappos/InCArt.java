package com.zappos.hsiaoluw.zappos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

public class InCArt extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayout;
    RecyclerView.Adapter mItemAdapter;
    TextView tvTotal;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_cart);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvItems_cart);
        mLayout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayout);
        mItemAdapter = new CartAdapter(this);
        mRecyclerView.setAdapter(mItemAdapter);
        tvTotal = (TextView) findViewById(R.id.total_cart);
        tvTotal.setText(String.valueOf(CartAdapter.Cart.total_value));
        imageView = (ImageView) findViewById(R.id.CartItemView);
    }
}
