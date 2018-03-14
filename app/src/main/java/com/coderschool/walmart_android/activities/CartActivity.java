package com.coderschool.walmart_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.adapters.CartAdapter;
import com.coderschool.walmart_android.adapters.CartListener;
import com.coderschool.walmart_android.adapters.ProductsAdapter;
import com.coderschool.walmart_android.models.Product;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener {

    private TextView tvAmount;
    private TextView tvTotalItem;
    private List<Product> products;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setupToolbar();
        setupRecyclerView();
        setupBottomView();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        products = (List<Product>) getIntent().getSerializableExtra("ProductInCart");

        RecyclerView rvCart = (RecyclerView) findViewById(R.id.rvCart);
        adapter = new CartAdapter(this, products);
        adapter.setListener(this);
        rvCart.setAdapter(adapter);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void setupBottomView() {
        tvAmount = (TextView) findViewById(R.id.tvTotalAmount);
        tvTotalItem = (TextView) findViewById(R.id.tvTotalItem);
        updateAmount();
        updateQuantity();
    }

    private void updateAmount() {
        double totalAmount = 0;
        for (Product product: products) {
            totalAmount += product.getQuantity().doubleValue() * product.getCurrentPrice();
        }

        tvAmount.setText(String.format("Total amount: $%.2f", totalAmount));
    }

    private void updateQuantity() {
        int totalItem = 0;
        for (Product product: products) {
            totalItem += product.getQuantity();
        }
        tvTotalItem.setText(String.format("%d items", totalItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checkout:
                Intent intent = new Intent(this, CheckoutActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onQuantityIncreased(int index) {
        products.get(index).setQuantity(products.get(index).getQuantity() + 1);
        adapter.notifyDataSetChanged();
        updateQuantity();
        updateAmount();
    }

    @Override
    public void onQuantityDecreased(int index) {
        if (products.get(index).getQuantity() > 1) {
            products.get(index).setQuantity(products.get(index).getQuantity() - 1);
            adapter.notifyDataSetChanged();
            updateQuantity();
            updateAmount();
        } else {
            Intent output = new Intent();
            output.putExtra("RemoveProduct", products.get(index));
            setResult(ProductActivity.RESULT_CODE, output);

            products.remove(index);
            adapter.notifyDataSetChanged();
            updateQuantity();
            updateAmount();
        }
    }
}
