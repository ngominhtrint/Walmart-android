package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.activities.MainActivity;
import com.coderschool.walmart_android.adapters.ProductListener;
import com.coderschool.walmart_android.adapters.ProductsAdapter;
import com.coderschool.walmart_android.manager.ProductManager;
import com.coderschool.walmart_android.models.Product;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements ProductListener {

    private ArrayList<Product> products;
    private ProductsAdapter adapter;
    private MainActivity mainActivity;

    public HomeFragment(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);
        setupRecyclerView();

        mainActivity.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setupRecyclerView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        super.onViewCreated(view, savedInstanceState);
    }


    private void setupRecyclerView() {
        if (ProductManager.getInstance().isReset()) {
            ProductManager.getInstance().setReset(false);
            products = Product.readJSONFile(mainActivity);
            ProductManager.getInstance().save(products);
        } else {
            products = ProductManager.getInstance().load();
        }

        RecyclerView rvProducts = (RecyclerView) getView().findViewById(R.id.rvProducts);
        adapter = new ProductsAdapter(mainActivity, products);
        adapter.setListener(this);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(mainActivity));
        rvProducts.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_product, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void addToCart(int index) {
        products.get(index).setSelected(!products.get(index).getSelected());
        products.get(index).setQuantity(1);
        adapter.notifyDataSetChanged();

        ProductManager.getInstance().save(products);
    }
}
