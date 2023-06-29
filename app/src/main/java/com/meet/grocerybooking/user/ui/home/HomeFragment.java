package com.meet.grocerybooking.user.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.admin.category.RemoveCategoryActivity;
import com.meet.grocerybooking.authentication.UserLogInActivity;
import com.meet.grocerybooking.models.ProductModel;
import com.meet.grocerybooking.shopkeeper.products.ShopkeeperManageProductsActivity;
import com.meet.grocerybooking.user.UserMainActivity;
import com.meet.grocerybooking.user.ui.category.CategoryProductFragment;
import com.meet.grocerybooking.user.ui.products.ProductAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private ProductAdapter adapter;
    private com.meet.grocerybooking.user.ui.home.CategoryAdapter categoryAdapter;
    private ArrayList<ProductModel> list;


    private DatabaseReference reference;

    private GridView gridview;
    private RecyclerView recyclerView;

    private RecyclerView allProductsRecyclerView;
    private com.meet.grocerybooking.adpters.ProductAdapter adapter2;
    private ArrayList<ProductModel> list2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView btnLogOut = view.findViewById(R.id.log_out);

        reference = FirebaseDatabase.getInstance().getReference().child("products");
        gridview = view.findViewById(R.id.grid_view_one);
        recyclerView = view.findViewById(R.id.recycler_view_category);

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/grocery-booking-da290.appspot.com/o/banners%2Fb1.webp?alt=media&token=0b7a7181-e626-415c-aa86-b76bb4dc62d9", null));
        images.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/grocery-booking-da290.appspot.com/o/banners%2Fb2.webp?alt=media&token=b53123f5-a9b3-437d-8fbd-a3ba8c5e0e43", null));
        images.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/grocery-booking-da290.appspot.com/o/banners%2Fb3.webp?alt=media&token=77c82331-f2ad-44b5-a905-c227e5077818", null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_INSIDE);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(MyLayoutManager);

        allProductsRecyclerView = view.findViewById(R.id.recycler_view_home_products);

        allProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allProductsRecyclerView.setHasFixedSize(true);

        getCategories();
        getProducts();

        btnLogOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(), UserLogInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        });

        return  view;
    }

    private void getProducts() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                list2 = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel model = dataSnapshot.getValue(ProductModel.class);
                    if (list.size() < 4) {
                        list.add(model);
                    }
                    list2.add(model);
                }

                System.out.println(list.size());
                Collections.reverse(list);
                adapter = new ProductAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                gridview.setAdapter(adapter);

                adapter2 = new com.meet.grocerybooking.adpters.ProductAdapter(getContext(), list2);
                adapter2.notifyDataSetChanged();
                allProductsRecyclerView.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCategories() {
        final List<String> catItems = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("category").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    catItems.add(dataSnapshot.getKey());
                }

                categoryAdapter = new CategoryAdapter(getContext(), catItems);
                categoryAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}