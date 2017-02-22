package com.zappos.hsiaoluw.zappos;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class base extends AppCompatActivity  implements SearchView.OnQueryTextListener {

    static Menu mm;
    static int full =0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        this.mm =menu;

        if(mm!=null){
            if(base.full==1)   mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.full_cart,null));
            else              mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.empty_cart,null));}

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_search) {;}
        else if (id == R.id.app_bar_cart) {
            Intent intent =new Intent(this,InCArt.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if(base.full==1)  mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.full_cart,null));
        else            mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.empty_cart,null));
        return true ;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(mm!=null){
            if(base.full==1)   mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.full_cart,null));
            else              mm.getItem(1).setIcon(getResources().getDrawable(R.drawable.empty_cart,null));}
    }
}
