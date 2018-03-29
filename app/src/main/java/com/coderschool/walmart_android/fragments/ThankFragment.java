package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.activities.MainActivity;
import com.coderschool.walmart_android.manager.ProductManager;
import com.coderschool.walmart_android.models.Product;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ThankFragment extends Fragment {

    private MainActivity mainActivity;

    public ThankFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);

        TextView tvThankSubtotalItem = (TextView) getView().findViewById(R.id.tvThankSubtotalItem);
        TextView tvThankSubtotalAmount = (TextView) getView().findViewById(R.id.tvThankSubtotalAmount);
        TextView tvThankTotalAmount = (TextView) getView().findViewById(R.id.tvThankTotalAmount);

        ArrayList<Product> products = ProductManager.getInstance().loadCartItems();
        if (products.size() > 0) {
            tvThankSubtotalItem.setText(String.format("Subtotal (%d items)", products.get(0).getTotalItem()));
            tvThankSubtotalAmount.setText(String.format("$%.2f", products.get(0).getTotalAmount()));
            tvThankTotalAmount.setText(String.format("$%.2f", products.get(0).getTotalAmount()));
        }

        Button btnContinueShopping = (Button) getView().findViewById(R.id.btnContinueShopping);
        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductManager.getInstance().setReset(true);
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                mainActivity.viewPager.setCurrentItem(0);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
