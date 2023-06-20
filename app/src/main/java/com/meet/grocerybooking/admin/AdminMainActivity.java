package com.meet.grocerybooking.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.meet.grocerybooking.R;
import com.meet.grocerybooking.StartActivity;
import com.meet.grocerybooking.admin.category.AddCategoryActivity;
import com.meet.grocerybooking.admin.category.RemoveCategoryActivity;
import com.meet.grocerybooking.admin.products.ManageProductsActivity;
import com.meet.grocerybooking.admin.shopkeeper.ManageShopkeeperActivity;
import com.meet.grocerybooking.authentication.AdminLogInActivity;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("false")) {
            startActivity(new Intent(AdminMainActivity.this, AdminLogInActivity.class));
            finish();
        }

        MaterialCardView addCategory = findViewById(R.id.add_category);
        MaterialCardView removeCategory = findViewById(R.id.remove_category);
        MaterialCardView manageShopkeeper = findViewById(R.id.manage_shopkeeper);
//        MaterialCardView manageProducts = findViewById(R.id.manage_products);
        Button logOut = findViewById(R.id.btn_logout);

        addCategory.setOnClickListener(this);
        removeCategory.setOnClickListener(this);
        manageShopkeeper.setOnClickListener(this);
//        manageProducts.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_category:
                intent = new Intent(AdminMainActivity.this, AddCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.remove_category:
                intent = new Intent(AdminMainActivity.this, RemoveCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_shopkeeper:
                intent = new Intent(AdminMainActivity.this, ManageShopkeeperActivity.class);
                startActivity(intent);
                break;
//            case R.id.manage_products:
//                intent = new Intent(AdminMainActivity.this, ManageProductsActivity.class);
//                startActivity(intent);
//                break;
            case R.id.btn_logout:
                editor.putString("isLogin", "false");
                editor.commit();
                intent = new Intent(AdminMainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}