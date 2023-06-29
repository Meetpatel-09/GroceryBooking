package com.meet.grocerybooking.user.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.meet.grocerybooking.R;
import com.meet.grocerybooking.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductModel> {

    public ProductAdapter(@NonNull Context context, ArrayList<ProductModel> productModelArrayList) {
        super(context, 0, productModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.product_card_item, parent, false);
        }

        ProductModel productModel = getItem(position);

        ImageView product_thumbnail = listItemView.findViewById(R.id.card_product_image);
        TextView product_title = listItemView.findViewById(R.id.card_product_title);
        TextView product_price =  listItemView.findViewById(R.id.card_product_price);

        product_title.setText(productModel.getName());
        product_price.setText(String.format("₹%s", productModel.getPrice()));

        try {
            if (productModel.getImage() != null)
                Picasso.get().load(productModel.getImage()).placeholder(R.drawable.loading_shape).into(product_thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listItemView;
    }

}
