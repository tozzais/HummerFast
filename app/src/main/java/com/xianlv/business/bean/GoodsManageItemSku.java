package com.xianlv.business.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsManageItemSku implements Parcelable {


    public String skuId;
    public String property;
    public String newPrice;
    public String total;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.skuId);
        dest.writeString(this.property);
        dest.writeString(this.newPrice);
        dest.writeString(this.total);
    }

    public void readFromParcel(Parcel source) {
        this.skuId = source.readString();
        this.property = source.readString();
        this.newPrice = source.readString();
        this.total = source.readString();
    }

    public GoodsManageItemSku() {
    }

    protected GoodsManageItemSku(Parcel in) {
        this.skuId = in.readString();
        this.property = in.readString();
        this.newPrice = in.readString();
        this.total = in.readString();
    }

    public static final Parcelable.Creator<GoodsManageItemSku> CREATOR = new Parcelable.Creator<GoodsManageItemSku>() {
        @Override
        public GoodsManageItemSku createFromParcel(Parcel source) {
            return new GoodsManageItemSku(source);
        }

        @Override
        public GoodsManageItemSku[] newArray(int size) {
            return new GoodsManageItemSku[size];
        }
    };
}
