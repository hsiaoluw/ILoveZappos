package com.zappos.hsiaoluw.zappos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsiaoluw on 2017/2/20.
 */

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    static cart Cart= new cart();
    private Context mContext;
    private List<cart.inCartItem>  mItems;
    public CartAdapter(Context context) {
        this.mContext = context;
        this.mItems = new ArrayList<>(Cart.items.values());
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId =  R.layout.cart_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.holder_con = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {
        final cart.inCartItem item = mItems.get(position);
        holder.tvName.setText(item.item.getproductName());
        holder.tvBrand.setTextColor(Color.BLACK);
        holder.tvBrand.setText(item.item.getbrandName());
        holder.tvPrice.setText(item.item.getprice());

        final String index= item.item.getproductId()+item.item.getstyleId();
        if(DataItemAdapter.mBitmaps.containsKey(index)){
            Bitmap mp = DataItemAdapter.mBitmaps.get(index);
            holder.imageView.setImageBitmap(mp);
        }
        holder.spCount.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                String selectedItem = holder.spCount.getSelectedItem().toString();
                String index = item.item.getstyleId()+item.item.getproductId();
                CartAdapter.Cart.set_quantity(index, Integer.valueOf(selectedItem));
                ((InCArt)mContext).tvTotal.setText(String.valueOf(CartAdapter.Cart.total_value));
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                holder.spCount.setSelection(item.count-1);
            }

        });
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName,tvBrand,tvPrice;
        ImageView imageView;
        final public Spinner  spCount;
        public View mView;
        public Context holder_con;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.product_cart);
            tvBrand  = (TextView) itemView.findViewById(R.id.brand_cart);
            tvPrice= (TextView) itemView.findViewById(R.id.price_cart);
            spCount = (Spinner) itemView.findViewById(R.id.spinner_cart);
            imageView = (ImageView) itemView.findViewById(R.id.CartItemView);
            mView = itemView;
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                    itemView.getContext(),R.array.total,android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCount.setAdapter(adapter1);
        }
    }
}