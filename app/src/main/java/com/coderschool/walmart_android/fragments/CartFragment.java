package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.activities.MainActivity;
import com.coderschool.walmart_android.adapters.CartAdapter;
import com.coderschool.walmart_android.adapters.CartListener;
import com.coderschool.walmart_android.manager.ProductManager;
import com.coderschool.walmart_android.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tringo on 3/28/18.
 */

@SuppressLint("ValidFragment")
public class CartFragment extends Fragment implements CartListener {

    private MainActivity mainActivity;
    private ArrayList<Product> products;
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

        mainActivity.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    setupRecyclerView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
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
        products = ProductManager.getInstance().loadCartItems();

        RecyclerView rvCart = (RecyclerView) getView().findViewById(R.id.rvCart);
        adapter = new CartAdapter(mainActivity, products);
        adapter.setListener(this);
        rvCart.setAdapter(adapter);
        rvCart.setLayoutManager(new LinearLayoutManager(mainActivity));
        rvCart.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onCheckout(int index) {
        Fragment checkoutFragment = new CheckoutFragment(mainActivity);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, checkoutFragment);
        ft.addToBackStack(CheckoutFragment.class.getName());
        ft.commit();
    }

    @Override
    public void onRemoveItem(int index) {
        Product cartItem = products.get(index);
        ArrayList<Product> homeProducts = ProductManager.getInstance().load();
        for (int i = 0; i < homeProducts.size(); i++) {
            if (cartItem.getId().equals(homeProducts.get(i).getId())) {
                homeProducts.get(i).setSelected(false);
                homeProducts.get(i).setQuantity(0);
            }
        }
        ProductManager.getInstance().save(homeProducts);

        products.remove(index);

        if (products.size() < 3) {
            products.remove(products.size() - 1);
            products.remove(0);
        } else {
            products.get(0).setTotalItem(ProductManager.getInstance().calculateQuantity(products));
            products.get(0).setTotalAmount(ProductManager.getInstance().calculateAmount(products));
            products.get(products.size() - 1).setTotalItem(ProductManager.getInstance().calculateQuantity(products));
            products.get(products.size() - 1).setTotalAmount(ProductManager.getInstance().calculateAmount(products));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPickQuantity(int index) {
        openDialog(index);
    }

    private void openDialog(final int index) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mainActivity);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Pick item quantity");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_selectable_list_item);
        for (int i = 1; i <= 12; i++) {
            arrayAdapter.add(String.valueOf(i));
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strNumber = arrayAdapter.getItem(which);
                products.get(index).setQuantity(Integer.valueOf(strNumber));

                ProductManager.getInstance().save(products);
                products.get(0).setTotalItem(ProductManager.getInstance().calculateQuantity(products));
                products.get(0).setTotalAmount(ProductManager.getInstance().calculateAmount(products));
                products.get(products.size() - 1).setTotalItem(ProductManager.getInstance().calculateQuantity(products));
                products.get(products.size() - 1).setTotalAmount(ProductManager.getInstance().calculateAmount(products));

                adapter.notifyDataSetChanged();
            }
        });
        builderSingle.show();
    }
}
