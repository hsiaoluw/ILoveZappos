package com.zappos.hsiaoluw.zappos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by hsiaoluw on 2017/2/10.
 */

public class DataItemAdapter  extends RecyclerView.Adapter<DataItemAdapter.ViewHolder>{
    public static final String ITEM_KEY = "item_key";
    public static final String MAP_KEY = "map_key";
    public static final int REQUEST_CODE = 300;
    private List<DataItem> mItems;
    static public Map<String, Bitmap> mBitmaps= new HashMap<>();
    private Context mContext;
    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId =  R.layout.product_layout;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutId, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.holder_con = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final DataItemAdapter.ViewHolder holder, final int position) {
        final DataItem item = mItems.get(position);

        try {
            holder.tvName.setText(item.getproductName());
            holder.tvBrand.setText(item.getbrandName());
            if(mBitmaps.containsKey(item.getproductId()+item.getstyleId())){
                Bitmap mp = mBitmaps.get(item.getproductId()+item.getstyleId());
                holder.imageView.setImageBitmap(mp);}
            else{
                ImageDownloadTask task = new ImageDownloadTask();
                task.setViewHolder(holder);
                task.execute(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //itemView.setEnabled(false);
        if(!holder.mView.hasOnClickListeners()) {
           holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, DetailProductActivity.class);
                    intent.putExtra(MainActivity.POSITION, position);
                 //   intent.putExtra(MAP_KEY, (Serializable) mBitmaps);
                    ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_CODE);
                   /*Toast toast = Toast.makeText(mContext,"short click", Toast.LENGTH_SHORT);
                                  toast.show();*/
                }
            });
            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(mContext, DetailProductActivity.class);
                    intent.putExtra(MainActivity.POSITION, position);
                 //   intent.putExtra(MAP_KEY, (Serializable) mBitmaps);
                    ((Activity) v.getContext()).startActivityForResult(intent, REQUEST_CODE);
                   /*Toast toast = Toast.makeText(mContext,"long click", Toast.LENGTH_SHORT);
                   toast.show();*/
                    return false;
                }
            });
        }

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

        public TextView tvName,tvBrand;
        public ImageView imageView;
        public View mView;
        public Context holder_con;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tvBrand  = (TextView) itemView.findViewById(R.id.itemBrandText);
            mView = itemView;
        }
    }

    private class ImageDownloadTask extends AsyncTask<DataItem, Void, Bitmap> {
        private DataItem mDataItem;
        private ViewHolder mHolder;
        public void setViewHolder(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        protected Bitmap doInBackground(DataItem... dataItems) {

            mDataItem = dataItems[0];
            InputStream in = null;

            try {
                in = (InputStream) mDataItem.getthumbnailImageUrl().getContent();
                return BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mHolder.imageView.setImageBitmap(bitmap);
            mBitmaps.put(mDataItem.getproductId()+mDataItem.getstyleId(),bitmap);
        }
    }
}