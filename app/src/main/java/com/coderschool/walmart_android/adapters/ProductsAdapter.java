package com.coderschool.walmart_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderschool.walmart_android.R;
import com.coderschool.walmart_android.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tringo on 3/14/18.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProducts;
        TextView tvCurrentPrice;
        TextView tvOriginPrice;
        TextView tvName;
        TextView tvShip;
        Button btnAddToCart;

        ViewHolder(View itemView) {
            super(itemView);

            ivProducts = (ImageView) itemView.findViewById(R.id.ivProduct);
            tvCurrentPrice = (TextView) itemView.findViewById(R.id.tvCurrentPrice);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tvOriginPrice);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvShip = (TextView) itemView.findViewById(R.id.tvShip);
            btnAddToCart = (Button) itemView.findViewById(R.id.btnAddToCart);
        }
    }

    private Context mContext;
    private List<Product> mProducts;

    public ProductsAdapter(Context mContext, List<Product> mProducts) {
        this.mContext = mContext;
        this.mProducts = mProducts;
    }

    public Context getContext() {
        return mContext;
    }


    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.ViewHolder holder, int position) {
        Product product = mProducts.get(position);

        if (product != null) {
            Picasso.get()
                    .load(product.getPhoto())
                    .into(holder.ivProducts);

            holder.tvCurrentPrice.setText(String.format("$%s", product.getCurrentPrice()));
            holder.tvOriginPrice.setText(String.format("was $%s", product.getOriginPrice()));
            holder.tvName.setText(product.getName());
            holder.tvShip.setText(product.getShip());
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
