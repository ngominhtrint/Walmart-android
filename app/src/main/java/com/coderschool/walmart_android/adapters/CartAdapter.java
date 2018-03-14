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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProducts;
        TextView tvCurrentPrice;
        TextView tvOriginPrice;
        TextView tvName;
        TextView tvShip;
        TextView tvQuantity;
        Button btnAdd;
        Button btnSubstract;

        ViewHolder(View itemView) {
            super(itemView);

            ivProducts = (ImageView) itemView.findViewById(R.id.ivProduct);
            tvCurrentPrice = (TextView) itemView.findViewById(R.id.tvCurrentPrice);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tvOriginPrice);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvShip = (TextView) itemView.findViewById(R.id.tvShip);
            tvQuantity = (TextView) itemView.findViewById(R.id.tvQuantity);
            btnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            btnSubstract = (Button) itemView.findViewById(R.id.btnSubstract);
        }
    }

    private Context mContext;
    private CartListener mListener;
    private List<Product> mProducts;

    public CartAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.mProducts = products;
    }

    public Context getContext() {
        return mContext;
    }

    public void setListener(CartListener listener) {
        this.mListener = listener;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(productView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Product product = mProducts.get(position);

        if (product != null) {
            Picasso.get()
                    .load(product.getPhoto())
                    .into(holder.ivProducts);

            holder.tvCurrentPrice.setText(String.format("$%s", product.getCurrentPrice()));
            holder.tvOriginPrice.setText(String.format("was $%s", product.getOriginPrice()));
            holder.tvName.setText(product.getName());
            holder.tvShip.setText(product.getShip());
            holder.tvQuantity.setText(product.getQuantity().toString());

            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onQuantityIncreased(position);
                }
            });

            holder.btnSubstract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onQuantityDecreased(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}

