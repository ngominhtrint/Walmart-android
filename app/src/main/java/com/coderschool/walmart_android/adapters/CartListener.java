package com.coderschool.walmart_android.adapters;

/**
 * Created by tringo on 3/14/18.
 */

public interface CartListener {
    public void onQuantityIncreased(int index);
    public void onQuantityDecreased(int index);
}
