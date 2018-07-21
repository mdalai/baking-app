package com.example.minga.bakingapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by minga on 7/11/2018.
 */
@Entity(tableName = "recipes")
public class Recipe implements Parcelable {
    @PrimaryKey @NonNull
    Integer id;
    String name;
    Integer servings;
    String image;
    ArrayList<Ingredient> ingredients;
    ArrayList<Step> steps;


    public Recipe(Integer id, String name, Integer servings, String image, ArrayList<Ingredient> ingredients, ArrayList<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings =servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }


    public Integer getId(){ return id; }
    public void setId(Integer id){ this.id = id; }
    public String getName(){ return name; }
    public void setName(String name) {this.name = name; }
    public Integer getServings(){ return servings; }
    public void setServings(Integer servings){ this.servings = servings; }
    public String getImage(){ return image; }
    public void setImage(String image) {this.image = image; }

    public ArrayList<Ingredient> getIngredients() { return  ingredients; }
    public void setIngredients(ArrayList<Ingredient> ingredients) {this.ingredients = ingredients; }
    public ArrayList<Step> getSteps() { return  steps; }
    public void setSteps(ArrayList<Step> steps) {this.steps = steps; }

    // Parcelable -----------
    protected Recipe(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        servings = in.readByte() == 0x00 ? null : in.readInt();
        image = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<Step>();
            in.readList(steps, Step.class.getClassLoader());
        } else {
            steps = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (servings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(servings);
        }
        dest.writeString(image);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", servings='" + servings + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
