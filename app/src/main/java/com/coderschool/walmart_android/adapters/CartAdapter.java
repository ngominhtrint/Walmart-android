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

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProducts;
        TextView tvCurrentPrice;
        TextView tvName;
        TextView tvShip;
        Button btnPickQuantity;
        Button btnRemove;

        ViewHolder(View itemView) {
            super(itemView);

            ivProducts = (ImageView) itemView.findViewById(R.id.ivProduct);
            tvCurrentPrice = (TextView) itemView.findViewById(R.id.tvCurrentPrice);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvShip = (TextView) itemView.findViewById(R.id.tvShip);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove);
            btnPickQuantity = (Button) itemView.findViewById(R.id.btnPickQuantity);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        Button btnHeaderCheckout;

        HeaderViewHolder(View itemView) {
            super(itemView);
            btnHeaderCheckout = (Button) itemView.findViewById(R.id.btnHeaderCheckout);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        Button btnFooterCheckout;
        FooterViewHolder(View itemView) {
            super(itemView);
            btnFooterCheckout = (Button) itemView.findViewById(R.id.btnFooterCheckout);
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
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == mProducts.size() - 1) {
            return -1;
        } else {
            return position;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productView = inflater.inflate(R.layout.item_cart, parent, false);
        View headerView = inflater.inflate(R.layout.item_header, parent, false);
        View footerView = inflater.inflate(R.layout.item_footer, parent, false);

        switch (viewType) {
            case 0:
                return new HeaderViewHolder(headerView);
            case -1:
                return new FooterViewHolder(footerView);
            default:
                return new ViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Product product = mProducts.get(position);

        switch (holder.getItemViewType()) {
            case 0:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.btnHeaderCheckout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onCheckout(position);
                    }
                });
                break;
            case -1:
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                footerViewHolder.btnFooterCheckout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onCheckout(position);
                    }
                });
                break;
                default:
                    ViewHolder viewHolder = (ViewHolder) holder;
                    if (product != null) {
                        Picasso.get()
                                .load(product.getPhoto())
                                .into(viewHolder.ivProducts);

                        viewHolder.tvCurrentPrice.setText(String.format("$%s", product.getCurrentPrice()));
                        viewHolder.tvName.setText(product.getName());
                        viewHolder.tvShip.setText(product.getShip());
                        viewHolder.btnPickQuantity.setText(product.getQuantity() == 0 ? "1" : product.getQuantity().toString());

                        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mListener.onRemoveItem(position);
                            }
                        });

                        viewHolder.btnPickQuantity.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mListener.onPickQuantity(position);
                            }
                        });
                    }
                    break;
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}

