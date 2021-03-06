package com.example.minga.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by minga on 7/12/2018.
 */

public class Ingredient implements Parcelable {
    double quantity;
    String measure;
    String ingredient;

    public Ingredient(double quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure= measure;
        this.ingredient = ingredient;
    }

    public double getQuantity(){ return quantity; }
    public void setQuantity(double quantity){ this.quantity = quantity; }
    public String getMeasure() { return measure; }
    public void setMeasure(String measure){ this.measure = measure; }
    public String getIngredient(){ return ingredient; }
    public void setIngredient(String ingredient) { this.ingredient = ingredient; }


    protected Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
