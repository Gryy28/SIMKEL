package com.gerry.simkel;

import android.os.Parcel;
import android.os.Parcelable;

public class Tampil implements Parcelable {
    private String id;
    private String namaBarang;
    private int harga;
    private String desc;
    private String user_id;

    private String username;

    protected Tampil(Parcel in) {
        id = in.readString();
        namaBarang = in.readString();
        harga = in.readInt();
        desc = in.readString();
        user_id = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(namaBarang);
        dest.writeInt(harga);
        dest.writeString(desc);
        dest.writeString(user_id);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tampil> CREATOR = new Creator<Tampil>() {
        @Override
        public Tampil createFromParcel(Parcel in) {
            return new Tampil(in);
        }

        @Override
        public Tampil[] newArray(int size) {
            return new Tampil[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
