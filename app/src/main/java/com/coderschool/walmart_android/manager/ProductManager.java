package com.coderschool.walmart_android.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.coderschool.walmart_android.App;
import com.coderschool.walmart_android.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tringo on 3/26/18.
 */

public class ProductManager {

    private static final String PREFERENCE_NAME = "com.coderschool.product_manager";
    private static ProductManager mInstance;
    private SharedPreferences mSharedPreferences;

    private ArrayList<Product> products = new ArrayList<>();

    private ProductManager() {
        mSharedPreferences = App.self().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static ProductManager getInstance() {
        if (mInstance == null) {
            mInstance = new ProductManager();
        }
        return mInstance;
    }

    public boolean isReset() {
        return get("isReset", Boolean.class);
    }

    public void setReset(boolean isReset) {
        put("isReset", isReset);
    }

    public ArrayList<Product> getProducts() {
        return load();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        this.save(products);
    }

    public void save() {
        this.put("products", products);
    }

    public void save(ArrayList<Product> newProducts) {
        this.put("products", newProducts);
    }

    public ArrayList<Product> load() {
        Type listType = new TypeToken<ArrayList<Product>>(){}.getType();
        products = this.get("products", listType);

        return products;
    }

    public ArrayList<Product> loadCartItems() {
        ArrayList<Product> filterProducts = new ArrayList<>();

        for (Product item: load()) {
            if (item.getSelected()) {
                filterProducts.add(item);
            }
        }

        if (filterProducts.size() > 0) {
            int totalItems = calculateQuantity(filterProducts);
            double totalAmount = calculateAmount(filterProducts);
            Product headerItem = new Product(totalItems, totalAmount);
            Product footerItem = new Product(totalItems, totalAmount);
            filterProducts.add(0, headerItem);
            filterProducts.add(filterProducts.size(), footerItem);
        }

        return filterProducts;
    }

    public double calculateAmount(ArrayList<Product> sources) {
        double totalAmount = 0;
        for (Product product: sources) {
            if (product.getCurrentPrice() == null) {
                continue;
            }
            totalAmount += (double) product.getQuantity() * product.getCurrentPrice();
        }
        return totalAmount;
    }

    public int calculateQuantity(ArrayList<Product> sources) {
        int totalItem = 0;
        for (Product product: sources) {
            totalItem += product.getQuantity();
        }
        return totalItem;
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
    public <T> T get(String key, Type anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, true));
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
