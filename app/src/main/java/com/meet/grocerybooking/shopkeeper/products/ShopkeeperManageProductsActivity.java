package com.meet.grocerybooking.shopkeeper.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.adpters.ProductAdapter;
import com.meet.grocerybooking.models.ProductModel;

import java.util.ArrayList;

public class ShopkeeperManageProductsActivity extends AppCompatActivity {

    private RecyclerView manageProductsRecyclerView;
    private ProgressBar progressBar;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> list;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_manage_products);

        manageProductsRecyclerView = findViewById(R.id.s_manage_products_recyclerview);
        progressBar = findViewById(R.id.progress_bar);

        reference = FirebaseDatabase.getInstance().getReference().child("products");

        manageProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        manageProductsRecyclerView.setHasFixedSize(true);

        getData();
    }

    private void getData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel data = dataSnapshot.getValue(ProductModel.class);
                    list.add(data);
                }

                adapter = new ProductAdapter(ShopkeeperManageProductsActivity.this, list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                manageProductsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ShopkeeperManageProductsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}