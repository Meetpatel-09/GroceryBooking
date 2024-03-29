package com.meet.grocerybooking.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meet.grocerybooking.R;
import com.meet.grocerybooking.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private final Context context;
    private final List<ProductModel> list;

    public ProductAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ProductModel model = list.get(position);

        holder.product_title.setText(model.getName());
        holder.product_desc.setText(model.getBrand());
        holder.product_price.setText(model.getPrice());

        try {
            if (model.getImage() != null)
                Picasso.get().load(model.getImage()).placeholder(R.drawable.loading_shape).into(holder.product_thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView product_thumbnail;
        public TextView product_title;
        public TextView product_desc;
        public TextView product_price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            product_thumbnail = itemView.findViewById(R.id.row_thumbnail);
            product_title = itemView.findViewById(R.id.row_title);
            product_desc = itemView.findViewById(R.id.row_desc);
            product_price = itemView.findViewById(R.id.row_price);

        }
    }
}
