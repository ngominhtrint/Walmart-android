package com.coderschool.walmart_android.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

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
    public void onCheckout(int index) {
        Fragment checkoutFragment = new CheckoutFragment(mainActivity);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, checkoutFragment);
        ft.addToBackStack(CheckoutFragment.class.getName());
        ft.commit();
    }

    @Override
    public void onRemoveItem(int index) {

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
                adapter.notifyDataSetChanged();

                Toast.makeText(mainActivity, strNumber + " items", Toast.LENGTH_SHORT).show();
            }
        });
        builderSingle.show();
    }
}
