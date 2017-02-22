package com.zappos.hsiaoluw.zappos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.zappos.hsiaoluw.zappos.Network.NetworkManager;
import com.zappos.hsiaoluw.zappos.Services.MyService;
import com.zappos.hsiaoluw.zappos.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zappos.hsiaoluw.zappos.DataItemAdapter.REQUEST_CODE;

;


public class MainActivity  extends base  implements SearchView.OnQueryTextListener {

    private TextView output;
    String Zappo_base = "https://api.zappos.com/Search?term=";
    String Key        = "&key=b743e26728e16b81da139182bb2094357c31d331";
    public static int bought =0;
    public static final int BOUGHINCART = 102;
    public static final String POSITION = "postion";
    NetworkManager nm;
    static List<DataItem> products_data;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayout;
    DataItemAdapter mItemAdapter;
    RecyclerView.ItemDecoration dividerItemDecoration;
    Activity mn = (Activity) this;
    int i;


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataItem[] dataItems = (DataItem[]) intent
                    .getParcelableArrayExtra(MyService.MY_SERVICE_PAYLOAD);
            Toast.makeText(MainActivity.this,
                    "Received " + dataItems.length + " items from service",
                    Toast.LENGTH_SHORT).show();
            products_data = Arrays.asList(dataItems);
            displayDataItems();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvItems);
        mLayout = new GridLayoutManager(this,2){
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 0;
            }
        };
        mLayout.setInitialPrefetchItemCount(0);
        mRecyclerView.setLayoutManager(mLayout);
        products_data = new ArrayList<DataItem>();


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                if ( scrollState ==  RecyclerView.SCROLL_STATE_IDLE )
                {
                    mItemAdapter.notifyDataSetChanged();
                    recyclerView.invalidate();
                }

            }
        });
        mRecyclerView.setItemViewCacheSize(0);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(MyService.MY_SERVICE_MESSAGE));
      showData("nike");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mBroadcastReceiver);
    }
    private void displayDataItems() {
        if (products_data != null) {
            mItemAdapter = new DataItemAdapter(this, products_data);
            mRecyclerView.setAdapter(mItemAdapter);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
//        mDataSource.close();
    }



    public void showData(String query) {
        nm= new NetworkManager();
        if(nm.isOnline(this))  {
            Intent intent = new Intent(this, MyService.class);
            String search_query = Zappo_base+ query+ Key;
            intent.setData(Uri.parse(search_query));
            startService(intent);
        }
        else{
            Toast toast = Toast.makeText(this,"please connect to the internet", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast toast = Toast.makeText(this,query, Toast.LENGTH_SHORT);
        toast.show();
        showData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CODE&& resultCode==RESULT_OK){
            if(base.full==1)  mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.full_cart,null));
            else              mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.empty_cart,null));

        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mm!=null){
        if(base.full==1)   mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.full_cart,null));
        else              mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.empty_cart,null));
            invalidateOptionsMenu();
        }
    }


     /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                Integer previousTotal = 0;
                //synchronizew loading state when item count changes
                boolean loading = false;

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int begin = mLayout.findFirstVisibleItemPosition();
                    int end = mLayout.findLastVisibleItemPosition();

                    for (i = begin; i <= end; i++) {

                        View mView = mLayout.getChildAt(i);
                        mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(mn, DetailProductActivity.class);
                                intent.putExtra(MainActivity.POSITION, i);
                                intent.putExtra(MAP_KEY, (Serializable) mItemAdapter.mBitmaps);
                                startActivityForResult(intent, REQUEST_CODE);
                   /*Toast toast = Toast.makeText(mContext,"short click", Toast.LENGTH_SHORT);
                                  toast.show();*/
/*}
});
        mView.setOnLongClickListener(new View.OnLongClickListener() {
@Override
public boolean onLongClick(View v) {
        Intent intent = new Intent(mn, DetailProductActivity.class);
        intent.putExtra(MainActivity.POSITION, i);
        intent.putExtra(MAP_KEY, (Serializable) mItemAdapter.mBitmaps);
        startActivityForResult(intent, REQUEST_CODE);
                   /*Toast toast = Toast.makeText(mContext,"short click", Toast.LENGTH_SHORT);
                                  toast.show();*/
      /*  return true;
        }
        });
        }
        }
        });*/

}
