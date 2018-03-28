package com.coderschool.walmart_android.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.coderschool.walmart_android.App;
import com.coderschool.walmart_android.models.Product;

import java.util.ArrayList;

/**
 * Created by tringo on 3/26/18.
 */

public class ProductManager {

    private static final String PREFERENCE_NAME = "com.coderschool.product_manager";
    private static ProductManager mInstance;
    private SharedPreferences mSharedPreferences;

    private ArrayList<Product> products;

    private ProductManager() {
        mSharedPreferences = App.self().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static ProductManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProductManager();
        }
        return mInstance;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void save() {
        this.put("products", products);
    }

    public void save(ArrayList<Product> newProducts) {
        this.put("products", newProducts);
    }

    public void load() {
        this.products = this.get("products", ArrayList.class);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, App.self().getGson().toJson(data));
        }
        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        } else {
            return (T) App.self().getGson().fromJson(mSharedPreferences.getString(key, ""), anonymousClass);
        }
    }
}
