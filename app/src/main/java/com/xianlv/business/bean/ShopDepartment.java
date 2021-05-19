package com.xianlv.business.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopDepartment implements Parcelable {

    public int departmentId;
    public String department;
    public int status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.departmentId);
        dest.writeString(this.department);
        dest.writeInt(this.status);
    }

    public void readFromParcel(Parcel source) {
        this.departmentId = source.readInt();
        this.department = source.readString();
        this.status = source.readInt();
    }

    public ShopDepartment() {
    }

    protected ShopDepartment(Parcel in) {
        this.departmentId = in.readInt();
        this.department = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<ShopDepartment> CREATOR = new Parcelable.Creator<ShopDepartment>() {
        @Override
        public ShopDepartment createFromParcel(Parcel source) {
            return new ShopDepartment(source);
        }

        @Override
        public ShopDepartment[] newArray(int size) {
            return new ShopDepartment[size];
        }
    };
}
