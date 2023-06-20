package com.meet.grocerybooking.shopkeeper.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.meet.grocerybooking.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private ImageView productImage;

    private Spinner category;

    private EditText productName;
    private EditText productBrand;
    private EditText productDescription;
    private EditText productPrice;

    private String sProductName;
    private String sProductBrand;
    private String sProductDescription;
    private String sProductPrice;
    private String sProductCategory;

    private FirebaseAuth auth;

    private StorageTask<UploadTask.TaskSnapshot> uploadTask;

    private DatabaseReference reference;
    private FirebaseStorage storage;

    private Uri mImageUri;
    String downloadUrl = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();

        pd = new ProgressDialog(this);

        MaterialCardView addImage = findViewById(R.id.add_image);
        productImage = findViewById(R.id.product_img);

        category = findViewById(R.id.product_category);

        productName = findViewById(R.id.product_name);
        productBrand = findViewById(R.id.product_brand);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);

        setSpinner();

        Button uploadProduct = findViewById(R.id.upload_product);
        Button cancel = findViewById(R.id.cancel_upload);

        addImage.setOnClickListener(v -> CropImage.activity().setCropShape(CropImageView.CropShape.RECTANGLE).start(UploadActivity.this));

//        uploadProduct.setOnClickListener(v -> validateData());

        cancel.setOnClickListener(v -> startActivity(new Intent(AddProductActivity.this, AddProductActivity.class)));
    }

    private void setSpinner() {
        final List<String> catItems = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    catItems.add(dataSnapshot.getKey());
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, catItems);
                category.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}