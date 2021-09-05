package com.xianlv.business.util.datapick;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPickItem implements Parcelable {
    public String data;
    public boolean isClick;
    public boolean isSelete;
    public String day;


    public DataPickItem(String data, boolean isClick, boolean isSelete) {
        this.data = data;
        this.isClick = isClick;
        this.isSelete = isSelete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
        dest.writeByte(this.isClick ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelete ? (byte) 1 : (byte) 0);
        dest.writeString(this.day);
    }

    protected DataPickItem(Parcel in) {
        this.data = in.readString();
        this.isClick = in.readByte() != 0;
        this.isSelete = in.readByte() != 0;
        this.day = in.readString();
    }

    public static final Creator<DataPickItem> CREATOR = new Creator<DataPickItem>() {
        @Override
        public DataPickItem createFromParcel(Parcel source) {
            return new DataPickItem(source);
        }

        @Override
        public DataPickItem[] newArray(int size) {
            return new DataPickItem[size];
        }
    };
}
