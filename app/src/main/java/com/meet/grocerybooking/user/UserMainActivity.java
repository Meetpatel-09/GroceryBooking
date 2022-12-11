package com.meet.grocerybooking.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

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
import com.meet.grocerybooking.adpters.ProductAdapter;
import com.meet.grocerybooking.authentication.UserLogInActivity;
import com.meet.grocerybooking.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private ArrayList<ProductModel> list;

    private DatabaseReference reference;

    private GridView gridview;

    static final String[] numbers = new String[] {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        ImageView btnLogOut = findViewById(R.id.log_out);

        reference = FirebaseDatabase.getInstance().getReference().child("products");
//        gridview = findViewById(R.id.grid_view1);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, numbers);

//        gridview.setAdapter(adapter);

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/grocery-booking-da290.appspot.com/o/banners%2Fb1.webp?alt=media&token=0b7a7181-e626-415c-aa86-b76bb4dc62d9", null));
        images.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/grocery-booking-da290.appspot.com/o/banners%2Fb2.webp?alt=media&token=b53123f5-a9b3-437d-8fbd-a3ba8c5e0e43", null));
        images.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/grocery-booking-da290.appspot.com/o/banners%2Fb3.webp?alt=media&token=77c82331-f2ad-44b5-a905-c227e5077818", null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_INSIDE);

//        getProducts();

        ImageView image1 = findViewById(R.id.id1);
        ImageView image2 = findViewById(R.id.id2);
        ImageView image3 = findViewById(R.id.id3);
        ImageView image4 = findViewById(R.id.id4);

        String img1 = "https://www.jiomart.com/images/product/600x600/490001387/amul-butter-100-g-carton-product-images-o490001387-p490001387-0-202203170403.jpg";
        String img2 = "https://www.jiomart.com/images/product/600x600/491186625/good-life-almonds-500-g-product-images-o491186625-p491186625-0-202205180139.jpg";
        String img3 = "https://www.jiomart.com/images/product/600x600/490009614/lotte-creamfilled-choco-pie-28-g-pack-of-12-product-images-o490009614-p490009614-0-202203170648.jpg";
        String img4 = "https://www.jiomart.com/images/product/600x600/490001795/maaza-mango-drink-1-2-l-bottle-product-images-o490001795-p490001795-0-202203171010.jpg";

        Picasso.get().load(img1).placeholder(R.drawable.loading_shape).into(image1);
        Picasso.get().load(img2).placeholder(R.drawable.loading_shape).into(image2);
        Picasso.get().load(img3).placeholder(R.drawable.loading_shape).into(image3);
        Picasso.get().load(img4).placeholder(R.drawable.loading_shape).into(image4);

        btnLogOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, UserLogInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        });

    }

    private void getProducts() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel model = dataSnapshot.getValue(ProductModel.class);
                    list.add(model);
                }

                adapter = new ProductAdapter(UserMainActivity.this, list);
//                adapter.notifyDataSetChanged();
                gridview.setAdapter((ListAdapter) new ProductAdapter(UserMainActivity.this, list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}