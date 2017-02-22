package com.zappos.hsiaoluw.zappos;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsiaoluw on 2017/2/20.
 */

public class cart {
    float total_value;

    public class inCartItem {
        float value;
        int   count;
        DataItem item;
        public inCartItem(DataItem item){
            this.item =item;
            String tobeParsed = item.getprice().trim();
            value =  Float.parseFloat(tobeParsed.substring(1, tobeParsed.length()-1));
            count=1;
        }

        public void setCount(int v){ this.count=v;}
    }
    Map<String,inCartItem> items;
    public cart(){
        items= new HashMap<>();
        total_value=0;
    }
    public void add_to_cart(DataItem d){
        String index = d.getstyleId()+d.getproductId();
        if(!items.containsKey(index)) {
            inCartItem inD = new inCartItem(d);
            items.put(index, inD);
            total_value+= inD.value;
        }
    }
    public void remove_from_cart(String index){
        if(items.containsKey(index)) {
            total_value-= items.get(index).value * items.get(index).count;
            items.remove(index);
        }
    }
    public void remove_from_cart(DataItem d){
        String index = d.getstyleId()+d.getproductId();
        if(items.containsKey(index)) {
            total_value-= items.get(index).value * items.get(index).count;
            items.remove(index);
        }
    }
    public void set_quantity (String index, int number){
        if(items.containsKey(index)) {
            total_value-=items.get(index).value * items.get(index).count;
            items.get(index).setCount(number);
            total_value+=items.get(index).value * items.get(index).count;
        }
    }

}

