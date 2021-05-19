package com.xianlv.business.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopInfo implements Parcelable {


    public String shopId;
    public String shopName;
    public String provinceCode;
    public String provinceName;
    public String address;
    public String shopPhone;
    public int merchantId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopId);
        dest.writeString(this.shopName);
        dest.writeString(this.provinceCode);
        dest.writeString(this.provinceName);
        dest.writeString(this.address);
        dest.writeString(this.shopPhone);
        dest.writeInt(this.merchantId);
    }

    public void readFromParcel(Parcel source) {
        this.shopId = source.readString();
        this.shopName = source.readString();
        this.provinceCode = source.readString();
        this.provinceName = source.readString();
        this.address = source.readString();
        this.shopPhone = source.readString();
        this.merchantId = source.readInt();
    }

    public ShopInfo() {
    }

    protected ShopInfo(Parcel in) {
        this.shopId = in.readString();
        this.shopName = in.readString();
        this.provinceCode = in.readString();
        this.provinceName = in.readString();
        this.address = in.readString();
        this.shopPhone = in.readString();
        this.merchantId = in.readInt();
    }

    public static final Parcelable.Creator<ShopInfo> CREATOR = new Parcelable.Creator<ShopInfo>() {
        @Override
        public ShopInfo createFromParcel(Parcel source) {
            return new ShopInfo(source);
        }

        @Override
        public ShopInfo[] newArray(int size) {
            return new ShopInfo[size];
        }
    };
}
