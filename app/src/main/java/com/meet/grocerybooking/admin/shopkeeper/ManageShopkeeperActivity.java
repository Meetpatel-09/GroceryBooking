package com.meet.grocerybooking.admin.shopkeeper;

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
import com.meet.grocerybooking.adpters.ShopkeeperAdapter;
import com.meet.grocerybooking.models.ShopkeeperModel;

import java.util.ArrayList;

public class ManageShopkeeperActivity extends AppCompatActivity {

    private RecyclerView manageShopkeeperRecyclerview;
    private ProgressBar progressBar;
    private ArrayList<ShopkeeperModel> list;
    private ShopkeeperAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shopkeeper);

        manageShopkeeperRecyclerview = findViewById(R.id.manage_shopkeeper_recyclerview);
        progressBar = findViewById(R.id.progress_bar_u);

        reference = FirebaseDatabase.getInstance().getReference().child("shopkeeper");

        manageShopkeeperRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        manageShopkeeperRecyclerview.setHasFixedSize(true);

        getData();

    }

    private void getData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ShopkeeperModel data = dataSnapshot.getValue(ShopkeeperModel.class);
                    list.add(data);
                }

                adapter = new ShopkeeperAdapter(ManageShopkeeperActivity.this, list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                manageShopkeeperRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ManageShopkeeperActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}