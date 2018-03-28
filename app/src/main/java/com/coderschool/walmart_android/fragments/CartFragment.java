package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.activities.MainActivity;
import com.coderschool.walmart_android.adapters.CartAdapter;
import com.coderschool.walmart_android.adapters.CartListener;
import com.coderschool.walmart_android.models.Product;

import java.util.List;

/**
 * Created by tringo on 3/28/18.
 */

@SuppressLint("ValidFragment")
public class CartFragment extends Fragment implements CartListener {

    private MainActivity mainActivity;
    private List<Product> products;
    private CartAdapter adapter;

    public CartFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);
        setupRecyclerView();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checkout:
                Fragment checkoutFragment = new CheckoutFragment(mainActivity);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, checkoutFragment);
                ft.addToBackStack(CheckoutFragment.class.getName());
                ft.commit();
                return true;
            default:
                return false;
        }
    }

    private void setupRecyclerView() {
        products = Product.readJSONFile(mainActivity);

        RecyclerView rvCart = (RecyclerView) getView().findViewById(R.id.rvCart);
        adapter = new CartAdapter(mainActivity, products);
        adapter.setListener(this);
        rvCart.setAdapter(adapter);
        rvCart.setLayoutManager(new LinearLayoutManager(mainActivity));
        rvCart.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onQuantityIncreased(int index) {

    }

    @Override
    public void onQuantityDecreased(int index) {

    }

    @Override
    public void onCheckout(int index) {
        Fragment checkoutFragment = new CheckoutFragment(mainActivity);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, checkoutFragment);
        ft.addToBackStack(CheckoutFragment.class.getName());
        ft.commit();
    }
}
