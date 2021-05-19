package com.xianlv.business.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ShopResult implements Parcelable {

    public List<ShopDepartment> apiDepartmentList;
    public ShopInfo appShop;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.apiDepartmentList);
        dest.writeParcelable(this.appShop, flags);
    }

    public void readFromParcel(Parcel source) {
        this.apiDepartmentList = new ArrayList<ShopDepartment>();
        source.readList(this.apiDepartmentList, ShopDepartment.class.getClassLoader());
        this.appShop = source.readParcelable(ShopInfo.class.getClassLoader());
    }

    public ShopResult() {
    }

    protected ShopResult(Parcel in) {
        this.apiDepartmentList = new ArrayList<ShopDepartment>();
        in.readList(this.apiDepartmentList, ShopDepartment.class.getClassLoader());
        this.appShop = in.readParcelable(ShopInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<ShopResult> CREATOR = new Parcelable.Creator<ShopResult>() {
        @Override
        public ShopResult createFromParcel(Parcel source) {
            return new ShopResult(source);
        }

        @Override
        public ShopResult[] newArray(int size) {
            return new ShopResult[size];
        }
    };
}
