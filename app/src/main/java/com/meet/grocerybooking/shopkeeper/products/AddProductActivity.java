package com.meet.grocerybooking.shopkeeper.products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.shopkeeper.ShopkeeperMainActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

        addImage.setOnClickListener(v -> CropImage.activity().setCropShape(CropImageView.CropShape.RECTANGLE).start(AddProductActivity.this));

        uploadProduct.setOnClickListener(v -> validateData());

        cancel.setOnClickListener(v -> startActivity(new Intent(AddProductActivity.this, AddProductActivity.class)));
    }

    private void validateData() {

        sProductCategory = category.getSelectedItem().toString();

        sProductName = productName.getText().toString();
        sProductBrand = productBrand.getText().toString();
        sProductDescription = productDescription.getText().toString();
        sProductPrice = productPrice.getText().toString();

        if (sProductName.isEmpty()) {
            productName.setError("Required");
            productName.requestFocus();
        } else if (sProductBrand.isEmpty()) {
            productBrand.setError("Required");
            productBrand.requestFocus();
        } else if (sProductDescription.isEmpty()) {
            productDescription.setError("Required");
            productDescription.requestFocus();
        } else if (sProductPrice.isEmpty()) {
            productPrice.setError("Required");
            productPrice.requestFocus();
        } else if (sProductCategory.equals("Select Category")) {
            productPrice.setError("Select Category");
            category.requestFocus();
        } else if (mImageUri == null) {
            productPrice.setError("Select Image");
            productPrice.requestFocus();
        } else {
            uploadProductImage();
        }
    }

    private void uploadProductImage() {
        pd.setMessage("Uploading...");
        pd.show();
        String uniqueString = UUID.randomUUID().toString();

        final StorageReference referenceForProfile = storage.getReference().child("products").child(uniqueString+".jpeg");

        uploadTask = referenceForProfile.putFile(mImageUri);
        uploadTask.addOnCompleteListener(AddProductActivity.this, task -> {
            if (task.isSuccessful()) {
                uploadTask.addOnSuccessListener(taskSnapshot -> referenceForProfile.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl = String.valueOf(uri);
                    uploadData();
                }));
            } else {
                pd.dismiss();
                Toast.makeText(AddProductActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData() {
        DatabaseReference dbRef = reference.child("products");
        final String uniqueKey = dbRef.push().getKey();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", uniqueKey);
        map.put("shopkeeperID", Objects.requireNonNull(auth.getCurrentUser()).getUid());
        map.put("name", sProductName);
        map.put("brand", sProductBrand);
        map.put("description", sProductDescription);
        map.put("category", sProductCategory);
        map.put("price", sProductPrice);
        map.put("image", downloadUrl);

        assert uniqueKey != null;

        dbRef.child(uniqueKey).setValue(map).addOnSuccessListener(unused -> {
            pd.dismiss();
            Toast.makeText(AddProductActivity.this, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddProductActivity.this, ShopkeeperMainActivity.class));
            finish();
        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(AddProductActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddProductActivity.this, ShopkeeperMainActivity.class));
            finish();
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            assert result != null;

            mImageUri = result.getUri();
            productImage.setImageURI(mImageUri);
            productImage.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }
}