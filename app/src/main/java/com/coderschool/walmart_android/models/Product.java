package com.coderschool.walmart_android.models;

import android.content.Context;
import android.util.JsonReader;

import com.coderschool.walmart_android.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by tringo on 3/14/18.
 */

public class Product {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("photo")
    private String photo;

    @SerializedName("ship")
    private String ship;

    @SerializedName("originPrice")
    private Double originPrice;

    @SerializedName("currentPrice")
    private Double currentPrice;

    @SerializedName("rating")
    private Double rating;

    public Product(String id, String name, String photo, String ship, Double originPrice, Double currentPrice, Double rating) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.ship = ship;
        this.originPrice = originPrice;
        this.currentPrice = currentPrice;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public static ArrayList<Product> readJSONFile(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.products);
        String jsonString = readJsonFile(inputStream);

        Type productType = new TypeToken<ArrayList<Product>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<Product> products = gson.fromJson(jsonString, productType);
        return products;
    }

    private static String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
