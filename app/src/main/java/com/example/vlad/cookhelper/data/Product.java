package com.example.vlad.cookhelper.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Product implements Parcelable {
    private String name;
    private int glass;
    private int faceted_glass;
    private int tablespoon;
    private int teaspoon;

    public Product() {

    }

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, int glass, int faceted_glass, int tablespoon, int teaspoon) {
        this.name = name;
        this.faceted_glass = faceted_glass;
        this.glass = glass;
        this.teaspoon = teaspoon;
        this.tablespoon = tablespoon;
    }

    private Product(Parcel in) {
        name = in.readString();
        int[] values = new int[4];
        in.readIntArray(values);

        glass = values[0];
        faceted_glass = values[1];
        tablespoon = values[2];
        teaspoon = values[3];
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in)
        {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeIntArray(new int[] {glass, faceted_glass, tablespoon, teaspoon});
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGlass() {
        return glass;
    }

    public void setGlass(int glass) {
        this.glass = glass;
    }

    public int getFaceted_glass() {
        return faceted_glass;
    }

    public void setFaceted_glass(int faceted_glass) {
        this.faceted_glass = faceted_glass;
    }

    public int getTablespoon() {
        return tablespoon;
    }

    public void setTablespoon(int tablespoon) {
        this.tablespoon = tablespoon;
    }

    public int getTeaspoon() {
        return teaspoon;
    }

    public void setTeaspoon(int teaspoon) {
        this.teaspoon = teaspoon;
    }

}
