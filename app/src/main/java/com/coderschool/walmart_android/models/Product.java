package com.coderschool.walmart_android.models;

import android.content.Context;

import com.coderschool.walmart_android.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by tringo on 3/14/18.
 */

public class Product implements Serializable {

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

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("isSelected")
    private boolean isSelected;

    @SerializedName("totalItem")
    private int totalItem;

    @SerializedName("totalAmount")
    private double totalAmount;

    public Product(String id, String name, String photo, String ship, Double originPrice, Double currentPrice, Double rating, int quantity, boolean isSelected, int totalItem, double totalAmount) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.ship = ship;
        this.originPrice = originPrice;
        this.currentPrice = currentPrice;
        this.rating = rating;
        this.quantity = quantity;
        this.isSelected = isSelected;
        this.totalItem = totalItem;
        this.totalAmount = totalAmount;
    }

    public Product(int totalItem, double totalAmount) {
        this.totalItem = totalItem;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getShip() {
        return ship;
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Double getRating() {
        return rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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
