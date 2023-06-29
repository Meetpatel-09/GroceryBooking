package com.meet.grocerybooking.user.ui.category;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.adpters.ProductAdapter;
import com.meet.grocerybooking.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_product, container, false);

        String data = requireContext().getSharedPreferences("CATEGORY", Context.MODE_PRIVATE).getString("categoryName", null);

        TextView categoryName = view.findViewById(R.id.category_name);
        categoryName.setText(data);

        recyclerView = view.findViewById(R.id.recycler_view_category_product);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        getProducts(data);

        return view;
    }

    private void getProducts(String data) {

        FirebaseDatabase.getInstance().getReference().child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel model = dataSnapshot.getValue(ProductModel.class);

                    if (model.getCategory().equals(data))
                        list.add(model);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}