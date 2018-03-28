package com.coderschool.walmart_android.adapters;

/**
 * Created by tringo on 3/14/18.
 */

public interface CartListener {
    public void onCheckout(int index);
    public void onRemoveItem(int index);
    public void onPickQuantity(int index);
}
