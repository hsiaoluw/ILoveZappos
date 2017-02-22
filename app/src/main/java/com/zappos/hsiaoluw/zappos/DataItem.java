package com.zappos.hsiaoluw.zappos;

import android.databinding.BaseObservable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import com.google.gson.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;

public  class DataItem extends BaseObservable implements Parcelable {

    public String brandName;
    public URL thumbnailImageUrl;
    public String productId;
    public String price;
    public String originalPrice;

    public String styleId;
    public String colorId;
    public String percentOff;
    public String productUrl;
    public String productName;
    public String InCart;


    public String getbrandName() {return brandName;}
    public void setbrandName(String branName) {   this.brandName = branName;}

    public URL getthumbnailImageUrl() {return thumbnailImageUrl;}
    public void setbrandName(URL thumbnailImageUrl) {   this.thumbnailImageUrl = thumbnailImageUrl;}

    public String getproductId() {return productId;}
    public void setproductId(String productId) {   this.productId = productId;}

    public String getprice() {return price;}
    public void setprice(String price) {   this.price = price;}

    public String getoriginalPrice() {return originalPrice;}
    public void setoriginalPrice(String originalPrice) {   this.originalPrice = originalPrice;}

    public String getstyleId() {return styleId;}
    public void setstyleId(String styleId) {   this.styleId = styleId;}

    public String getpercentOff() {return percentOff;}
    public void setpercentOff(String colorId) {   this.percentOff= percentOff;}

    public String getproductUrl() {return productUrl;}
    public void setproductUrl(String productUrl) {   this.productUrl = productUrl;}

    public String getproductName() {return productName;}
    public void setproductName(String productName) {   this.productName= productName;}

    public String getInCart() {return InCart;}
    public void setInCart(String  InCart) {   this. InCart=  InCart;}

    @Override
    public String toString() {
        return "DataItem{" +
                "brandName='" + brandName + '\'' +
                ", thumbnailImageUrl='" +  thumbnailImageUrl.toString()+ '\'' +
                ", productId='" + productId + '\'' +
                ", price='" + price + '\'' +
                ", originalPrice=" + originalPrice +
                ", colorId=" + colorId +
                ", percentOff='" + percentOff + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", productName='" +productName + '\'' +
                ",InCart="+InCart+
                '}';
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brandName);
        dest.writeString(this.thumbnailImageUrl.toString());
        dest.writeString(this.productId);
        dest.writeString(this.originalPrice);
        dest.writeString(this.styleId);
        dest.writeString(this.colorId);
        dest.writeString(this.price);
        dest.writeString(this.percentOff);
        dest.writeString(this.productUrl);
        dest.writeString(this.productName);
        dest.writeString(this.InCart);

    }

    public DataItem() {
    }

    public DataItem(Parcel in) {
        this.brandName = in.readString();
        try {
            this.thumbnailImageUrl = new URL(in.readString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.productId   = in.readString();
        this.originalPrice= in.readString();
        this.styleId     = in.readString();
        this.colorId     = in.readString();
        this.price       = in.readString();
        this.percentOff  = in.readString();
        this.productUrl  = in.readString();
        this.productName=in.readString();
        this.InCart     = "0";

    }


    public DataItem(JsonObject j) {

        this.brandName =j.get("brandName").getAsString();
        try{
            this.thumbnailImageUrl = new URL(j.get("thumbnailImageUrl").getAsString());}
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        this.productId=  j.get("productId").getAsString();
        this.originalPrice= j.get("originalPrice").getAsString();
        this.styleId= j.get("styleId").getAsString();
        this.colorId= j.get("colorId").getAsString();
        this.price  = j.get("price").getAsString();
        this.percentOff = j.get("percentOff").getAsString();
        this.productUrl = j.get("productUrl").getAsString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.productName= Html.fromHtml(j.get("productName").getAsString(),Html.FROM_HTML_MODE_COMPACT).toString();
        }
        else{
            this.productName=Html.fromHtml(j.get("productName").getAsString()).toString();
        }
        this.InCart="0";

    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}

